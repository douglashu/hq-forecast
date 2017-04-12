/**
 * 
 */
package com.hq.manny.upd.service.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

/**
 * @author Zale
 *
 */
@Service
public class FileStoreServiceImpl implements FileStoreService {

	@Autowired
	private GridFsOperations operations;

	@Override
	public void saveFile(File file, String fileName) throws IOException {
		if (fileName.startsWith("/")) {
			fileName = fileName.replaceFirst("/", "");
		}
		FileInputStream inputStream = new FileInputStream(file);
		operations.store(inputStream, fileName);
		if (inputStream != null) {
			inputStream.close();
		}
	}

	@Override
	public void saveFile(InputStream inputStream, String fileName)
			throws IOException {
		if (fileName.startsWith("/")) {
			fileName = fileName.replaceFirst("/", "");
		}
		operations.store(inputStream, fileName);
	}

	@Override
	public void remove(String filename) {
		if (filename.startsWith("/")) {
			filename = filename.replaceFirst("/", "");
		}
		Query query = Query.query(Criteria.where("filename").is(filename));
		operations.delete(query);
	}

}
