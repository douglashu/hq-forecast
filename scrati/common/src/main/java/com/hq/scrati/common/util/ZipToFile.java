package com.hq.scrati.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;


public class ZipToFile {
	private static final Logger logger = Logger.getLogger(ZipToFile.class);
	/**
	 *
	 * 打ZIP包
	 */
	public static boolean zipCompress(List<String> srcFiles, String desFile) {
		boolean isSuccessful = false;
         logger.info("压缩开始");
		String[] fileNames = new String[srcFiles.size()];
		for (int i = 0; i < srcFiles.size(); i++) {
			fileNames[i] = parse(srcFiles.get(i));
		}

		try {

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(desFile));
			ZipOutputStream zos = new ZipOutputStream(bos);
			String entryName = null;
			if(fileNames.length>1){
				entryName = fileNames[0];
			}

			for (int i = 0; i < fileNames.length; i++) {
				entryName = fileNames[i];

				// 创建Zip条目
				ZipEntry entry = new ZipEntry(entryName);
				zos.putNextEntry(entry);
				BufferedInputStream bis;
				try {
					bis = new BufferedInputStream(new FileInputStream(
							srcFiles.get(i)));
				} catch (IOException e) {
					continue;
				}
				byte[] b = new byte[1024];

				while (bis.read(b, 0, 1024) != -1) {
					zos.write(b, 0, 1024);
				}
				bis.close();
				zos.closeEntry();
			}

			zos.flush();
			zos.close();
			isSuccessful = true;
		}catch(ZipException e1){
			logger.error("无压缩文件");
			return false;
		}catch (IOException e) {
			logger.error("压缩失败", e);
			return false;
		}
		logger.info("压缩成功");
		return isSuccessful;
	}

	// 解析文件名
	private static String parse(String srcFile) {
		//int location = srcFile.lastIndexOf("\\");
		int location = srcFile.lastIndexOf("/");
		String fileName = srcFile.substring(location + 1);
		return fileName;
	}
}
