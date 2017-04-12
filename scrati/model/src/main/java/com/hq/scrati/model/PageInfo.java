/**
 * 
 */
package com.hq.scrati.model;

import java.util.List;

/**
 * @author zhouyang
 *
 */
public class PageInfo<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 总的记录条数
	 */
	private Integer totalCount;

	/**
	 * 当前页码
	 */
	private Integer page;

	/**
	 * 分页长度
	 */
	private Integer length;

	/**
	 * 记录集合
	 */
	private List<T> dataList;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
