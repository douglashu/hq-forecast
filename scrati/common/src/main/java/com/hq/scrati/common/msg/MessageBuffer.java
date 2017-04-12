package com.hq.scrati.common.msg;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息缓存
 * @author Zale
 */
public class MessageBuffer {
	//缓存List
	private List<byte []> buffList=new ArrayList<byte []>();
	//数组总长度
	private int totalLength=0;
	/**
	 * 添加byte数组
	 * @param buff
	 */
	public void append(byte [] buff){
		if(null == buff || 0 == buff.length){
			return;
		}
		buffList.add(buff);
		totalLength+=buff.length;
	}
	/**
	 * 返回所有的byte数组
	 * @return
	 */
	public byte [] toByte(){
		//如果没有数据
		if(0 == totalLength){
			return null;
		}
		//将列表转换为Byte数组
		return covertList2Byte();
	}
	/**
	 * 将列表转换为Byte数组
	 * @return
	 */
	private byte [] covertList2Byte(){
		byte [] totalArr=new byte[totalLength];
		int copyedLength=0;
		for(byte [] buff:buffList){
			System.arraycopy(buff, 0, totalArr, copyedLength, buff.length);
			copyedLength+=buff.length;
		}
		return totalArr;
	}
	/**
	 * 获取总长度
	 */
	public int length(){
		return totalLength;
	}
}
