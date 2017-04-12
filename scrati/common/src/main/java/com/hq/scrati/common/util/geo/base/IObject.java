package com.hq.scrati.common.util.geo.base;

/**
 * 基础对象
 * 运动的物体模型
 * 包含所有最基本的属性
 * @author Karma
 *
 */
public interface IObject extends StatusStorage{
	//唯一关键字（物体ID）
	String KEY_UNI_OBJECT_ID="objectId";
	/**
	 * 获取该物体的唯一识别符
	 * @return
	 */
	String getObjectId();
	/**
	 * 获取物体的名称
	 * @return
	 */
	String getObjectName();
}
