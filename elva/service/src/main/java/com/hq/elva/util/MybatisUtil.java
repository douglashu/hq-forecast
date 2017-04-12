package com.hq.elva.util;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisUtil {

	private static final Logger logger = Logger.getLogger(MybatisUtil.class);

	public static RowBounds getRowBounds(Map<String, Object> req) {

		// =====================================
		int limit = 10;
		if (req.get("pageSize") != null && !req.get("pageSize").toString().isEmpty()) {
			try {
				limit = Integer.valueOf(req.get("pageSize").toString());
			} catch (NumberFormatException e) {
				logger.error("输入的显示行数不是数字【" + req.get("pageSize") + "】", e);
			}
		}

		// ============
		int offset = 0;
		if (req.get("curPage") != null && !req.get("curPage").toString().isEmpty()) {
			try {
				int curPage = Integer.valueOf(req.get("curPage").toString());
				offset = curPage;
			} catch (NumberFormatException e) {
				logger.error("输入页号不是整数【" + req.get("curPage") + "】", e);
			}
		}

		return new RowBounds(offset, limit);
	}

	public static Map<String, Object> populate(List<? extends Object> data, int totalCount) {
		Map<String, Object> retVal = new HashMap<String, Object>();
		retVal.put("data", data);
		retVal.put("totalCount", totalCount);
		return retVal;
	}

	public static boolean isEmpty(Map<String, Object> req, String paramName) {
		return req.get(paramName) == null || req.get(paramName).toString().isEmpty();
	}

	public static boolean isNotEmpty(Map<String, Object> req, String paramName) {
		return req.get(paramName) != null && !req.get(paramName).toString().isEmpty();
	}

	public static Map<String, String> populate(String key, String msg) {
		Map<String, String> repMap = new HashMap<String, String>();
		repMap.put("key", key);
		repMap.put("msg", msg);
		return repMap;
	}

	public static Map<String, String> populateFail(String msg) {
		return populate("9999", msg);
	}

	public static Map<String, String> populateSuccess(String msg) {
		return populate("0000", msg);
	}

}
