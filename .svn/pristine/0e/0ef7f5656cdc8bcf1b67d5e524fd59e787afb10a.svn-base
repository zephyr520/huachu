package com.huachu.core.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.huachu.core.dao.TRecyclingDispatchMapper;
import com.huachu.core.manager.RecyclingAccessoryManager;

@Component
@Configuration
@EnableScheduling
public class ScheduleTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleTask.class);
	
	@Autowired
	TRecyclingDispatchMapper recyclingDispatchMapper;
	@Autowired
	RecyclingAccessoryManager recyclingAccessoryManager;

	@Scheduled(cron = "0 0 1 * * ?")
	public void execDispatchRecycling() {
		long start = System.currentTimeMillis();
		logger.info("开始回收派单已过期的回收单及配件......");
		List<String> recyclingNoList = recyclingDispatchMapper.queryExpiredRecyclingNos();
		if (recyclingNoList != null && !recyclingNoList.isEmpty()) {
			for (String recyclingNo : recyclingNoList) {
				try {
					recyclingAccessoryManager.dispatchRecycling(recyclingNo);
				} catch (Exception e) {
					e.printStackTrace();
					if (logger.isErrorEnabled()) {
						logger.error("出错的回收单号：{}", recyclingNo);
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		logger.info("结束回收派单已过期的回收单及配件......, 耗时：{} 毫秒" , (end - start));
	}
}
