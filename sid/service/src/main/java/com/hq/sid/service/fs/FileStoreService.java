/**
 * 
 */
package com.hq.sid.service.fs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Zale
 *
 */
public interface FileStoreService {

	/**
	 * @param file
	 * @param filepath
	 * @throws java.io.IOException
	 */
	void saveFile(File file, String filepath) throws IOException;

	/**
	 * @param inputStream
	 * @param filepath
	 * @throws java.io.IOException
	 */
	void saveFile(InputStream inputStream, String filepath) throws IOException;

	/**
	 * @param filename
	 */
	void remove(String filename);
}
