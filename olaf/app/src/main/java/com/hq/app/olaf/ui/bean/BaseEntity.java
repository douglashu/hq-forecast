package com.hq.app.olaf.ui.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 所有实体类的父类,要求强制继承此类
 * 
 * @param <T>
 */
public abstract class BaseEntity<T> implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseEntity() {
	}

	/**
	 * 返回深拷贝副本方法
	 * 
	 * @return 深拷贝的副本
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public T copyEntity()
	{
		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try
		{
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(this);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

			ObjectInputStream in = new ObjectInputStream(byteIn);
			T result = (T) in.readObject();
			return result;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (byteArrayOutputStream != null)
			{
				try
				{
					byteArrayOutputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (objectOutputStream != null)
			{
				try
				{
					objectOutputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return copyEntity();
	}

}
