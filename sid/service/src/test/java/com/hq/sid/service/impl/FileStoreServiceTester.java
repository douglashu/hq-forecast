package com.hq.sid.service.impl;

import com.github.pagehelper.PageInfo;
import com.hq.scrati.common.log.Logger;
import com.hq.sid.SpringJUnitConfiger;
import com.hq.sid.dao.generate.TSidMercMapper;
import com.hq.sid.service.MercService;
import com.hq.sid.service.OpertorService;
import com.hq.sid.service.entity.request.MercSearchReq;
import com.hq.sid.service.entity.request.OperSearchReq;
import com.hq.sid.service.fs.FileStoreService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * @author Yan
 * @date 2017年1月12日
 * @version V1.0
 */
public class FileStoreServiceTester extends SpringJUnitConfiger {

	@Autowired
	private FileStoreService fileStoreService;

    @Test
	public void testSaveFile() throws IOException {
        File file = new File("/Users/yyx/Downloads/扫码支付.png");
        String fileName = "template.png";

        fileStoreService.saveFile(file,fileName);

    }
}
