package com.hq.scrati.model.request.pagination;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public class BasePaginationRequest {

	private Integer page;
	private Integer pageSize;
	
	public BasePaginationRequest(Integer page, Integer pageSize){
		this.page = page;
		this.pageSize = pageSize;
	}

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
