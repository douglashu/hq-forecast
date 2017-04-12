package com.hq.scrati.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Zale on 2016/12/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-esbsdk.xml")
public class IDGeneratorTest {
    @Autowired
    private IDGenerator idGenerator;

    @Test
    public void testMenberId() throws InterruptedException {
        String memberId = idGenerator.generateMemberNo(1);
        System.out.println(memberId);
    }
    @Test
    public void testMerchantNo() throws InterruptedException {
        String memberId = idGenerator.generateMerchantNo("1234");
        System.out.println(memberId);
    }
}