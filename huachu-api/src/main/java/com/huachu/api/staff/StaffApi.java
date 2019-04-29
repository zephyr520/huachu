package com.huachu.api.staff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huachu.common.exception.BOException;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.StaffService;
import com.huachu.dto.request.StaffReqDTO;
import com.huachu.dto.request.query.QueryStaffReqDTO;
import com.huachu.dto.response.StaffRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 定损员信息接口
 * @author zephyr
 * @date 2018/12/31
 */
@Api(description = "定损员信息接口")
@RestController
public class StaffApi extends AbstractApi {

    @Autowired
    StaffService staffService;

    @ApiOperation("分页获取定损员信息")
    @PostMapping("/staff/list")
    public ApiResult<PageInfo<StaffRespDTO>> queryList(@RequestBody QueryStaffReqDTO reqDto) {
        PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
        Page<StaffRespDTO> page = staffService.queryList(reqDto);
        return new ApiResult<>(page.toPageInfo());
    }
    
    @ApiOperation("创建定损员信息")
    @PostMapping("/staff/create")
    public ApiResult<Boolean> createStaff(@RequestBody @Validated StaffReqDTO reqDto, BindingResult errorResult) {
    	if (errorResult.hasErrors()) {
    		List<ObjectError> errors = errorResult.getAllErrors();
    		throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
    	}
    	return new ApiResult<>(staffService.create(reqDto));
    }
    
    @ApiOperation("修改定损员信息")
    @PostMapping("/staff/modify")
    public ApiResult<Boolean> modifyStaff(@RequestBody @Validated StaffReqDTO reqDto, BindingResult errorResult) {
    	if (errorResult.hasErrors()) {
    		List<ObjectError> errors = errorResult.getAllErrors();
    		throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
    	}
    	return new ApiResult<>(staffService.modify(reqDto));
    }
}
