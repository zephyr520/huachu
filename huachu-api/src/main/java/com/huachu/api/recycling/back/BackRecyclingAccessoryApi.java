package com.huachu.api.recycling.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huachu.common.annotation.ApiAuthorized;
import com.huachu.common.exception.BOException;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.RecyclingAccessoryService;
import com.huachu.dto.request.AccessoryIfRecyclingFailedReqDTO;
import com.huachu.dto.request.RecyclingAccessoryIfTakePhotoReqDTO;
import com.huachu.dto.request.RecyclingAccessoryReqDTO;
import com.huachu.dto.request.query.QueryRecyclingAccessoryReqDTO;
import com.huachu.dto.response.RecyclingAccessoryRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "回收单配件接口")
@RestController
@RequestMapping("/back")
public class BackRecyclingAccessoryApi extends AbstractApi {

	@Autowired
	RecyclingAccessoryService recyclingAccessoryService;
	
	@ApiOperation("分页获取配件列表")
	@ApiAuthorized("back:accessory:list:query")
	@PostMapping("/recycling/accessory/list")
	public ApiResult<PageInfo<RecyclingAccessoryRespDTO>> queryList(@RequestBody QueryRecyclingAccessoryReqDTO reqDto) {
		PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
		Page<RecyclingAccessoryRespDTO> page = recyclingAccessoryService.queryList(reqDto, getUser());
		return new ApiResult<>(page.toPageInfo());
	}
	
	@ApiOperation("标记/撤销配件是否回收失败")
	@ApiAuthorized("back:accessory:recycling:failure")
	@PostMapping("/recycling/accessory/failure")
	public ApiResult<Boolean> accessoryRecyclingFailure(@RequestBody @Validated AccessoryIfRecyclingFailedReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
		return new ApiResult<>(recyclingAccessoryService.accessoryRecyclingFailure(reqDto));
	}
	
	@ApiOperation("配件回收是否需要拍照")
	@ApiAuthorized("back:accessory:take:photo")
	@PostMapping("/recycling/accessory/take/photo")
	public ApiResult<Boolean> accessoryTakePhoto(@RequestBody @Validated RecyclingAccessoryIfTakePhotoReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
		return new ApiResult<>(recyclingAccessoryService.accessoryTakePhoto(reqDto));
	}
	
	@ApiOperation("标记配件为已回收")
	@ApiAuthorized("back:accessory:has:recycled")
	@PostMapping("/accessory/{id}/has/recycled")
	public ApiResult<Boolean> accessoryHasRecycled(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryHasRecycled(id, getUser()));
	}
	

	@ApiOperation("撤销已回收配件为未回收")
	@ApiAuthorized("back:accessory:cancel:recycled")
	@PostMapping("/accessory/{id}/cancel/recycled")
	public ApiResult<Boolean> accessoryCancelRecycled(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryCancelRecycled(id, getUser()));
	}
	
	@ApiOperation("标记配件为已入库")
	@ApiAuthorized("back:accessory:has:storage")
	@PostMapping("/accessory/{id}/has/storage")
	public ApiResult<Boolean> accessoryHasStorage(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryHasStorage(id, getUser()));
	}
	
	@ApiOperation("撤销已入库配件为未入库")
	@ApiAuthorized("back:accessory:cancel:storage")
	@PostMapping("/accessory/{id}/cancel/storage")
	public ApiResult<Boolean> accessoryCancelStorage(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryCancelStorage(id, getUser()));
	}
	
	@ApiOperation("删除配件")
	@ApiAuthorized("back:recycling:accessory:delete")
	@PostMapping("/accessory/{id}/delete")
	public ApiResult<Boolean> accessoryDelete(@PathVariable Long id) {
		return new ApiResult<>(recyclingAccessoryService.accessoryDelete(id, getUser()));
	}
	
	@ApiOperation("修改配件信息")
	@ApiAuthorized("back:recycling:accessory:modify")
	@PostMapping("/accessory/modify")
	public ApiResult<Boolean> accessoryModify(@RequestBody @Validated RecyclingAccessoryReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
		return new ApiResult<>(recyclingAccessoryService.accessoryModify(reqDto, getUser()));
	}
}
