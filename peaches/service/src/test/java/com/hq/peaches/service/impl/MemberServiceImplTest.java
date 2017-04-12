package com.hq.peaches.service.impl;

import com.hq.peaches.service.MemberService;
import com.hq.peaches.service.entity.request.MemberReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mvc-test.xml", })
public class MemberServiceImplTest {

    @Resource
    private MemberService memberService;

    @Test
    public void testAddMemberTransactional() throws Exception {
        MemberReq req = new MemberReq();
        req.setName("ceshi");
        req.setPhone("18611111111");
        req.setMchId("123456");
        req.setSex("1");
        memberService.addMemberTransactional("123",req);
    }
}