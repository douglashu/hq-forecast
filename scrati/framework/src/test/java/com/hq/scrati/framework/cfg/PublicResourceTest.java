package com.hq.scrati.framework.cfg;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by Zale on 2016/11/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test-esbsdk.xml")
public class PublicResourceTest {
    @Autowired
    private PublicResource publicResource;
    @Test
    public void getProvs() throws Exception {
        Assert.assertNotNull(publicResource.getProvs());
    }

    @Test
    public void getCities() throws Exception {
        Assert.assertNotNull(publicResource.getCities("00000018"));
    }

    @Test
    public void getBankProvs() throws Exception {
        Assert.assertNotNull(publicResource.getBankProvs());
    }

    @Test
    public void getBankCities() throws Exception {
        Assert.assertNotNull(publicResource.getBankCities("130"));
    }

    @Test
    public void getBankArea() throws Exception {
        Assert.assertNotNull(publicResource.getBankArea("3333"));
    }

    @Test
    public void getBankInfo() throws Exception {
        Assert.assertNotNull(publicResource.getBankInfo());
    }

    @Test
    public void getBankIn() throws Exception {
        Assert.assertNotNull(publicResource.getBankIn("001110012017"));
    }

    @Test
    public void getBankIn1() throws Exception {
        Assert.assertNotNull(publicResource.getBankIn("001","120" ,"1100","1100"));
    }

    @Test
    public void getParams() throws Exception {
        Assert.assertNotNull(publicResource.getParams("USR_STATUS"));
    }

    @Test
    public void getParam() throws Exception {
        Assert.assertNotNull(publicResource.getParam("USR_STATUS","1"));
    }

    @Test
    public void getMccs() throws Exception {
        Assert.assertNotNull(publicResource.getMccs("003"));
    }

    @Test
    public void getMccInfo() throws Exception {
        Assert.assertNotNull(publicResource.getMccInfo("4457"));
    }

    @Test
    public void getUfit() throws Exception {
        Assert.assertNotNull(publicResource.getUfit("6230200550855162"));
    }

    @Test
    public void getYpets() throws Exception {
        Assert.assertNotNull(publicResource.getYpets("003"));
    }

    @Test
    public void getYpets1() throws Exception {
        Assert.assertNotNull(publicResource.getYpets());
    }

}