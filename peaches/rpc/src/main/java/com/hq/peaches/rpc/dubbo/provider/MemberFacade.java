package com.hq.peaches.rpc.dubbo.provider;

import com.github.pagehelper.PageInfo;
import com.hq.peaches.service.entity.response.MemberRsp;
import com.hq.scrati.model.HqRequest;

/**
 * @包名称：com.hq.peaches.rpc.dubbo.provider
 * @创建人：yyx
 * @创建时间：16/12/5 下午9:46
 */
public interface MemberFacade {

    /**
     * 新增会员
     *
     * @param hqRequest 公共参数 + 会员对象【MemberReq】
     * @return
     */
    public boolean insert(HqRequest hqRequest);

    /**
     * 删除会员
     *
     * @param hqRequest 公共参数 + 会员ID【MemberIdReq】
     * @return
     */
    public boolean delete(HqRequest hqRequest);

    /**
     * 修改会员
     *
     * @param hqRequest 公共参数 + 会员对象【MemberReq】
     * @return
     */
    public boolean update(HqRequest hqRequest);

    /**
     * 查询会员信息
     *
     * @param hqRequest 会员ID【MemberIdReq】
     * @return
     */
    public MemberRsp queryById(HqRequest hqRequest);

    /**
     * 查询会员信息是否唯一
     *
     * @param hqRequest 商户id + 会员手机号【MemberUniqueReq】
     * @return
     */

    public boolean uniqueMemberInfo(HqRequest hqRequest);

    /**
     * 禁用会员
     *
     * @param hqRequest 公共参数 + 会员ID【MemberIdReq】
     * @return
     */
    public boolean disabled(HqRequest hqRequest);

    /**
     * 启用会员
     *
     * @param hqRequest 公共参数 + 会员ID【MemberIdReq】
     * @return
     */
    public boolean enabled(HqRequest hqRequest);

    /**
     * 根据条件查询会员分页信息
     *
     * @param hqRequest 公共参数 + 会员分页对象【MemberPageReq】->{商户id,会员名称,会员电话,开始记录数,每页记录数}
     * @return
     */
    public PageInfo<MemberRsp> findMemberByCondition(HqRequest hqRequest);

    /**
     * 根据openId查询会员信息
     *
     * @param hqRequest 支付宝或者微信的openId
     * @return
     */
    public MemberRsp findMemberByOpenId(HqRequest hqRequest);

}
