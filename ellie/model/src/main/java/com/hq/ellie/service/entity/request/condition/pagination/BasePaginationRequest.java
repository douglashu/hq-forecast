package com.hq.ellie.service.entity.request.condition.pagination;

import com.hq.scrati.common.constants.CommonConstants;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public class BasePaginationRequest {

	private Integer page;
	private Integer pageSize;

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
