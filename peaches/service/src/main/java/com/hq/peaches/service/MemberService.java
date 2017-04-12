package com.hq.peaches.service;

import com.github.pagehelper.PageInfo;
import com.hq.peaches.service.entity.request.MemberReq;
import com.hq.peaches.service.entity.response.MemberRsp;

/**
 * @包名称：com.hq.peaches.service
 * @创建人：yyx
 * @创建时间：16/12/1 下午9:55
 */
public interface MemberService {

    /**
     * 添加会员信息
     *
     * @param userId
     * @param req    会员请求对象
     * @return
     */
    public Boolean addMemberTransactional(String userId, MemberReq req);

    /**
     * 更新会员信息
     *
     * @param userId
     * @param memberId 会员id
     * @param req      会员请求对象
     * @return
     */
    public Boolean updateMemberTransactional(String userId, String memberId, MemberReq req);

    /**
     * 删除会员信息
     *
     * @param userId
     * @param memberId 会员id
     * @return
     */
    public Boolean delMemberTransactional(String userId, String memberId);

    /**
     * 查询会员信息是否唯一
     *
     * @param mchId 商户id
     * @param phone 会员手机号
     * @return
     */

    Boolean uniqueMemberInfo(String mchId, String phone);

    /**
     * 禁用会员
     *
     * @param userId
     * @param memberId 会员id
     * @return
     */
    Boolean disabledMember(String userId, String memberId);

    /**
     * 启用会员
     *
     * @param userId
     * @param memberId 会员id
     * @return
     */
    Boolean enabledMember(String userId, String memberId);

    /**
     * 根据条件查询会员分页信息
     *
     * @param mchId       商户id
     * @param name        会员名称
     * @param phone       会员电话
     * @param page        页码
     * @param pageSize  每页记录数
     * @return
     */
    PageInfo<MemberRsp> findMemberByCondition(String mchId, String name, String phone, Integer page, Integer pageSize);

    /**
     * 查询会员信息
     *
     * @param memberId 会员id
     * @return
     */
    MemberRsp findMemberById(String memberId);

    /**
     * 根据openId查询会员信息
     *
     * @param openId 支付宝或者微信的openId
     * @return
     */
    MemberRsp findMemberByOpenId(String openId);

}
