package com.huachu.api.recycling.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huachu.common.annotation.ApiAuthorized;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.core.service.RecyclingListDispatchService;
import com.huachu.dto.response.RecyclingListDispatchRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "派单记录接口")
@RestController
@RequestMapping("/back")
public class BackRecyclingListDispatchApi extends AbstractApi {

	@Autowired
	RecyclingListDispatchService recyclingListDispatchService;
	
	@ApiOperation("获取回收单的派单记录")
	@ApiAuthorized("back:recycling:dispatch:record")
	@GetMapping("/recycling/{recyclingNo}/dispatch/list")
	public ApiResult<List<RecyclingListDispatchRespDTO>> queryListByRecyclingNo(@PathVariable String recyclingNo) {
		return new ApiResult<>(recyclingListDispatchService.queryListByRecyclingNo(recyclingNo));
	}
}
