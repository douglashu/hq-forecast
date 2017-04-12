package com.hq.peaches.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.peaches.dao.generate.MemberMapper;
import com.hq.peaches.entity.generate.Member;
import com.hq.peaches.entity.generate.MemberExample;
import com.hq.peaches.service.MemberService;
import com.hq.peaches.service.entity.request.MemberReq;
import com.hq.peaches.service.entity.response.MemberRsp;
import com.hq.scrati.common.exception.BusinessException;
import com.hq.scrati.common.exception.ParamValueException;
import com.hq.scrati.common.log.Logger;
import com.hq.scrati.common.util.CollectionUtils;
import com.hq.scrati.common.util.StringUtils;
import com.hq.scrati.common.util.ValidateUtils;
import com.hq.scrati.framework.IDGenerator;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @包名称：com.hq.peaches.service.impl
 * @创建人：yyx
 * @创建时间：16/12/1 下午9:55
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private IDGenerator idGenerator;

    private Logger logger = Logger.getLogger();

    @Override
    @Transactional
    public Boolean addMemberTransactional(String userId, MemberReq req) {
        validateMemberReq(req);
        vailidateMemberUnique(req);

        Member member = new Member();
        BeanUtils.copyProperties(req, member);
        //        member.setId(UUIDGenerator.generate());
        try {
            member.setId(idGenerator.generateMemberNo(Long.parseLong(req.getMchId())));
        } catch (InterruptedException e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("会员ID生成异常");
        }
        member.setCreate_user(userId);
        member.setCreate_time(Calendar.getInstance().getTime());
        member.setStatus("1");
        try {
            memberMapper.insert(member);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean updateMemberTransactional(String userId, String memberId, MemberReq req) {
        if (ValidateUtils.isStrEmpty(req.getId())) {
            throw new ParamValueException("传入参数错误");
        }
        validateMemberReq(req);
        vailidateMemberUnique(req);
        Member member = validateMember(req.getId());

        BeanUtils.copyProperties(req, member, new String[] { "id", "mchId", "status" });
        member.setUpdate_time(Calendar.getInstance().getTime());
        member.setUpdate_user(userId);
        try {
            memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean delMemberTransactional(String userId, String memberId) {
        Member member = validateMember(memberId);
        member.setStatus("2");
        member.setUpdate_time(Calendar.getInstance().getTime());
        member.setUpdate_user(userId);
        try {
            memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean uniqueMemberInfo(String mchId, String phone) {
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        criteria.andMchIdEqualTo(mchId);
        long count;
        try {
            count = memberMapper.countByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (count > 0) {
            throw new BusinessException("商户会员信息已经存在");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean disabledMember(String userId, String memberId) {
        Member member = validateMember(memberId);
        member.setStatus("0");
        member.setUpdate_time(Calendar.getInstance().getTime());
        member.setUpdate_user(userId);
        try {
            memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean enabledMember(String userId, String memberId) {
        Member member = validateMember(memberId);
        member.setStatus("1");
        member.setUpdate_time(Calendar.getInstance().getTime());
        member.setUpdate_user(userId);
        try {
            memberMapper.updateByPrimaryKeySelective(member);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        return Boolean.TRUE;
    }

    @Override
    public PageInfo<MemberRsp> findMemberByCondition(String mchId, String name, String phone, Integer page, Integer pageSize) {
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        if (!ValidateUtils.isStrEmpty(mchId)) {
            criteria.andMchIdLike("%" + mchId + "%");
        }
        if (!ValidateUtils.isStrEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!ValidateUtils.isStrEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }
        RowBounds rowBounds = new RowBounds(page, pageSize);
        List<MemberRsp> rsps = new ArrayList<>();
        List<Member> records = memberMapper.selectByExampleWithRowbounds(memberExample, rowBounds);
        CollectionUtils.copyCollections(records, rsps, MemberRsp.class);
        PageInfo<MemberRsp> pageInfo = new com.github.pagehelper.PageInfo<>(rsps);
        return pageInfo;
    }

    @Override
    public MemberRsp findMemberById(String memberId) {
        Member member = validateMember(memberId);
        MemberRsp rsp = new MemberRsp();
        BeanUtils.copyProperties(member, rsp);
        return rsp;
    }

    @Override
    public MemberRsp findMemberByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new ParamValueException("支付宝或者微信的openId不能为空");
        }
        MemberExample memberExample = new MemberExample();
        MemberExample.Criteria criteria = memberExample.createCriteria();
        criteria.andAli_openidEqualTo(openId);
        MemberExample.Criteria criteria2 = memberExample.createCriteria();
        criteria2.andMmsg_openidEqualTo(openId);
        memberExample.or(criteria2);

        // 平台openId
        MemberExample.Criteria criteria3 = memberExample.createCriteria();
        criteria3.andPali_openidEqualTo(openId);
        memberExample.or(criteria3);

        MemberExample.Criteria criteria4 = memberExample.createCriteria();
        criteria4.andPmmsg_openidEqualTo(openId);
        memberExample.or(criteria4);

        long count = memberMapper.countByExample(memberExample);
        if (count <= 0) {
            return null;
        } else {
            List<Member> listMember = memberMapper.selectByExample(memberExample);
            Member member = listMember.get(0);
            MemberRsp rsp = new MemberRsp();
            BeanUtils.copyProperties(member, rsp);
            return rsp;
        }
    }

    public void validateMemberReq(MemberReq req) {
        if( null == req ){
            throw new ParamValueException("请求参数为空");
        }
        if( StringUtils.isBlank(req.getName() )){
            throw new ParamValueException("参数【name】为空");
        }
        if( StringUtils.isBlank(req.getPhone() )){
            throw new ParamValueException("参数【phone】为空");
        }
        if( StringUtils.isBlank(req.getMchId() )){
            throw new ParamValueException("参数【mchId】为空");
        }
        if( StringUtils.isBlank(req.getSex() )){
            throw new ParamValueException("参数【sex】为空");
        }

    }

    public Member validateMember(String memberId) {
        Member member = null;
        try {
            member = memberMapper.selectByPrimaryKey(memberId);
        } catch (Exception e) {
            Logger.getLogger().error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (null == member) {
            throw new ParamValueException("没有对应的会员");
        }
        return member;
    }

    /**
     * 验证商户会员是否已经存在
     *
     * @param req
     */
    private void vailidateMemberUnique(MemberReq req) {
        MemberExample example = new MemberExample();
        MemberExample.Criteria criteria = example.createCriteria();
        //        criteria.andNameEqualTo(req.getName());
        criteria.andPhoneEqualTo(req.getPhone());
        criteria.andMchIdEqualTo(req.getMchId());
        if (!ValidateUtils.isStrEmpty(req.getId())) {
            criteria.andIdNotEqualTo(req.getId());
        }
        long count;
        try {
            count = memberMapper.countByExample(example);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
            throw new BusinessException("数据库异常");
        }
        if (count > 0) {
            throw new BusinessException("商户会员信息已经存在");
        }
    }

}
