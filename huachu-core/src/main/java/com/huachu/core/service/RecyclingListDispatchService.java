package com.huachu.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huachu.core.dao.TRecyclingDispatchMapper;
import com.huachu.dto.response.RecyclingListDispatchRespDTO;

@Service
public class RecyclingListDispatchService {

	@Autowired
	TRecyclingDispatchMapper recyclingDispatchMapper;
	
	public List<RecyclingListDispatchRespDTO> queryListByRecyclingNo(String recyclingNo) {
		return recyclingDispatchMapper.queryListByRecyclingNo(recyclingNo);
	}
}
