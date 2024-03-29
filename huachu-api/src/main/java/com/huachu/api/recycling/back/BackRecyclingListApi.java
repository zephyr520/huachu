package com.huachu.api.recycling.back;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huachu.common.annotation.ApiAuthorized;
import com.huachu.common.exception.BOException;
import com.huachu.common.util.FileUtils;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.RecyclingListService;
import com.huachu.dto.request.RecyclingListDispatchReqDTO;
import com.huachu.dto.request.RecyclingListReqDTO;
import com.huachu.dto.request.query.QueryRecyclingListReqDTO;
import com.huachu.dto.response.RecyclingListRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Api(description = "后端回收单接口")
@RestController
@RequestMapping("/back")
public class BackRecyclingListApi extends AbstractApi {

	@Autowired
	RecyclingListService recyclingListService;
	
	@ApiOperation("分页查询回收单信息列表")
	@ApiAuthorized("back:recycling:list:query")
	@PostMapping("/recycling/list/page")
	public ApiResult<PageInfo<RecyclingListRespDTO>> queryList(@RequestBody QueryRecyclingListReqDTO reqDto) {
		PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
		Page<RecyclingListRespDTO> page = recyclingListService.queryList(reqDto, getUser());
		return new ApiResult<>(page.toPageInfo());
	}
	
	@ApiOperation("回收单信息导入")
	@ApiAuthorized("back:recycling:list:import")
	@PostMapping("/recycling/list/import")
	public ApiResult<Boolean> recyclingListImport(@RequestParam("file") MultipartFile file) {
		FileUtils.checkExcelContentType(file);
		FileUtils.checkFileSize(file);
		return new ApiResult<>(recyclingListService.recyclingListImport(file.getOriginalFilename(), file));
	}
	
	@ApiOperation("回收单派单")
	@ApiAuthorized("back:recycling:list:dispatch")
	@PostMapping("/recycling/list/dispatch")
	public ApiResult<Boolean> dispatchRecyclingList(@RequestBody @Valid RecyclingListDispatchReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
		return new ApiResult<>(recyclingListService.dispatchRecyclingList(reqDto, getUser()));
	}
	
	@ApiOperation("删除回收单")
	@ApiAuthorized("back:recycling:list:delete")
	@PostMapping("/recycling/list/{recyclingNo}/delete")
	public ApiResult<Boolean> deleteRecyclingList(@PathVariable String recyclingNo) {
		return new ApiResult<>(recyclingListService.deleteRecyclingList(recyclingNo));
	}
	
	@ApiOperation("创建回收单")
	@ApiAuthorized("back:recycling:list:create")
	@PostMapping("/recycling/list/create")
	public ApiResult<Boolean> createRecyclingList(@RequestBody @Validated RecyclingListReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
		return new ApiResult<>(recyclingListService.addRecyclingList(reqDto, getUser()));
	}
}
