/**
 * 
 */
package com.hq.manny.upload;


import java.util.Map;

/**
 * @author Zale
 *
 */
public interface FileHandler {
	
	Object execute(String fileName, Map params);
}
