package com.huachu.core.queue;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huachu.core.service.RecyclingListService;
import com.huachu.dto.RecyclingListDTO;

/**
 * Created by zephyr on 2019/1/12.
 */
public class RecyclingListTask implements Callable<List<RecyclingListDTO>> {

    private static final Logger logger = LoggerFactory.getLogger(RecyclingListTask.class);
    /***队列大小*/
    public static final int QUEUE_MAX_SIZE = 1000;
    /*** 线程数*/
    public static final int CORE_POOL_SIZE = 5 * Runtime.getRuntime().availableProcessors();
    
    private int start;
    private int end;
    private RecyclingListService recyclingListService;
    private List<RecyclingListDTO> recyclingListDtos;

    public RecyclingListTask(RecyclingListService recyclingListService, List<RecyclingListDTO> recyclingListDtos, int start, int end) {
    	this.recyclingListService = recyclingListService;
    	this.start = start;
    	this.end = end;
    	this.recyclingListDtos = recyclingListDtos;
    }

	@Override
	public List<RecyclingListDTO> call() throws Exception {
		List<RecyclingListDTO> subList = recyclingListDtos.subList(start, end);
        try {
        	logger.info(Thread.currentThread().getName() + "处理数量：{},{}", start, end);
            for (RecyclingListDTO dto: subList) {
              recyclingListService.createRecyclingList(dto);
            }
        }catch (Exception e) {
            e.printStackTrace();
        } 
        return subList;
	}
}
