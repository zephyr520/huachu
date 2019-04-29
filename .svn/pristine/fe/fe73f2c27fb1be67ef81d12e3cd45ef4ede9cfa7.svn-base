package com.huachu.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.huachu.core.dao.TRecyclingAccessoryMapper;

@Component
public class RecyclingAccessoryManager {
	
	private static final Logger logger = LoggerFactory.getLogger(RecyclingAccessoryManager.class);

	@Autowired
	TRecyclingAccessoryMapper recyclingAccessoryMapper;
	
	/**
	 * @description 对已过期的派单配件回收
	 * @param recyclingNo
	 */
	@Transactional(rollbackFor = Exception.class)
	public void dispatchRecycling(String recyclingNo) {
		int count = recyclingAccessoryMapper.accessoryDispatchRecycling(recyclingNo);
		logger.info("回收单：{},回收配件数量：{}", recyclingNo, count);
	}
}
