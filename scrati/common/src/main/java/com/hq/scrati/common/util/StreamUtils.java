package com.hq.scrati.common.util;

import com.hq.scrati.common.msg.MessageBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.ConcurrentModificationException;


/**
 * 流工具
 * @author Zale
 */
public class StreamUtils {
	
	/**
	 * 对流进行一次性读取
	 * @param is 输入流
	 * @return 读取结果
	 * @throws Exception
	 */
	public static byte [] readStreamOnce(InputStream is) throws Exception{
		return readStreamOnceIfClose(is, true);
	}
	
	/**
	 * 对流进行一次性读取
	 * @param is 输入流
	 * @param closeStream 是否关闭输入流
	 * @return 读取结果
	 * @throws Exception
	 */
	public static byte [] readStreamOnceIfClose(InputStream is,boolean closeStream) throws Exception{
		BufferedInputStream bis=null;
		MessageBuffer messageBuffer=null;
		byte [] commBuffer=null;
		try{
			bis=new BufferedInputStream(is);
			messageBuffer=new MessageBuffer();
			commBuffer=new byte[1024];
			int len = 0;
			while (-1 != (len = bis.read(commBuffer))) {
				byte [] tmp=new byte[len];
				System.arraycopy(commBuffer, 0, tmp, 0, len);
				messageBuffer.append(tmp);
			}
		}catch (Exception e) {
			throw e;
		}finally{
			if(closeStream){
				StreamUtils.closeStream(is);
				StreamUtils.closeStream(bis);
			}
			commBuffer=null;
		}
		return messageBuffer.toByte();
	}
	
	/**
	 * 写出流
	 * @param outputStream 输出流
	 * @param sendBytes 发送数据
	 * @throws Exception
	 */
	public static void writeStreamOnce(OutputStream outputStream,byte [] sendBytes) throws Exception{
		writeStreamOnceIfClose(outputStream, sendBytes, true);
	}
	
	/**
	 * 写出流
	 * @param outputStream 输出流
	 * @param sendBytes 发送数据
	 * @param closeStream 是否关闭流
	 * @throws Exception
	 */
	public static void writeStreamOnceIfClose(OutputStream outputStream,
			byte [] sendBytes,boolean closeStream) throws Exception{
		try{
			outputStream.write(sendBytes);
			outputStream.flush();
		}catch (Exception e) {
			throw e;
		}finally{
			if(closeStream){
				StreamUtils.closeStream(outputStream);
			}
		}
	}
	
	
	/**
	 * 将二进制数组转换为对象
	 * @param buff
	 * @return
	 */
	public static Object transByteArray2Object(byte [] buff){
		if(ValidateUtils.isByteArrEmpty(buff)){
			return null;
		}
		
		ObjectInputStream objectInputStream=null;
		ByteArrayInputStream byteArrayInputStream=null;
		Object object=null;
		try{
			byteArrayInputStream=new ByteArrayInputStream(buff);
			objectInputStream=new ObjectInputStream(byteArrayInputStream);
			object=objectInputStream.readObject();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeStream(byteArrayInputStream);
			closeStream(objectInputStream);
		}
		
		return object;
	}
	
	
	/**
	 * 将对象转换为二进制数组
	 * @param cache
	 * @return
	 */
	public static byte [] transObject2ByteArray(Object object){
		if(null == object){
			return null;
		}
		ObjectOutputStream objectOutputStream =null;
		ByteArrayOutputStream byteArrayOutputStream=null;
		try{
			byteArrayOutputStream = new ByteArrayOutputStream(); 
			objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception e) {
			//如果是同步错误，则不打印
			if(e instanceof ConcurrentModificationException){
				
			}else{
				e.printStackTrace();
			}
		}finally{
			closeStream(byteArrayOutputStream);
			closeStream(objectOutputStream);
		}
		
		if(null !=byteArrayOutputStream){
			return byteArrayOutputStream.toByteArray();
		}else{
			return null;
		}
	}
	/**
	 * 流进度接口
	 */
	public static interface IStreamPro{
		
		public void hook(int len);
	
	}
	
	/**
	 * 读取流然后写出流
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void readAndWrite(InputStream is,OutputStream os) throws Exception{
		readAndWrite(is, os, null);
	}
	
	/**
	 * 读取流然后写出流
	 * @param is
	 * @param os
	 * @throws Exception
	 */
	public static void readAndWrite(InputStream is,OutputStream os,IStreamPro streamPro) throws Exception{
		try{
			byte [] fileByteBuff=new byte[1024];
			
			while(true){
				int len=is.read(fileByteBuff);
				if(-1 == len){
					break;
				}
				os.write(fileByteBuff, 0, len);
				os.flush();
				
				if(null!=streamPro){
					streamPro.hook(len);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			closeStream(is);
			closeStream(os);
		}
	}
	

	/**
	 * 关闭流
	 * @param cs 流对象
	 */
	public static void closeStream(Closeable cs){
		if(null!=cs){
			try {
				cs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 关闭流集合
	 * @param collection 流集合
	 */
	public static void closeStreamCollection(Collection<InputStream> collection){
		if(ValidateUtils.isCollectionEmpty(collection)){
			return;
		}
		
		for(Closeable cs:collection){
			closeStream(cs);
		}
	}
	
	/**
	 * 关闭输入流数组
	 * @param isArr
	 */
	public static void closeInputStreamArr(InputStream [] isArr){
		if(ValidateUtils.isArrEmpty(isArr)){
			return;
		}
		
		for(InputStream is:isArr){
			closeStream(is);
		}
	}
}
