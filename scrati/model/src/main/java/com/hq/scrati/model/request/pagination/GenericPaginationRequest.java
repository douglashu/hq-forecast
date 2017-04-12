package com.hq.scrati.model.request.pagination;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public class GenericPaginationRequest extends BasePaginationRequest implements Serializable{

	public GenericPaginationRequest(Integer page, Integer pageSize) {
		super(page, pageSize);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3174463820306884962L;

	private Map<String, Object> req;

	public Map<String, Object> getReq() {
		return req;
	}

	public void setReq(Map<String, Object> req) {
		this.req = req;
	}
}
