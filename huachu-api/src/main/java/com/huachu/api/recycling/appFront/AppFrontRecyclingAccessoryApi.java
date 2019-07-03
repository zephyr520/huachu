package com.huachu.api.recycling.appFront;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.core.service.RecyclingAccessoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "app配件接口")
@RestController
@RequestMapping("/app/accessory")
public class AppFrontRecyclingAccessoryApi extends AbstractApi {

	@Autowired
	private RecyclingAccessoryService recyclingAccessoryService;
	
	@ApiOperation("标记配件为已回收")
	@PostMapping("/{id}/has/recycled")
	public ApiResult<Boolean> accessoryHasRecycled(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryHasRecycled(id, getUser()));
	}
	
	@ApiOperation("撤销已回收配件为未回收")
	@PostMapping("/{id}/cancel/recycled")
	public ApiResult<Boolean> accessoryCancelRecycled(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryCancelRecycled(id, getUser()));
	}
}
