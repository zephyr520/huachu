package com.huachu.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huachu.common.annotation.ApiAuthorized;
import com.huachu.common.constants.Constant;
import com.huachu.common.exception.BOException;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.AdminUserService;
import com.huachu.dto.request.AdminUserReqDTO;
import com.huachu.dto.request.query.QueryAdminUserReqDTO;
import com.huachu.dto.response.AdminUserRespDTO;
import com.huachu.dto.response.RecyclingUserRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author zephyr
 * @DATE 2018/8/29
 */
@Api(description = "系统用户接口")
@RestController
public class AdminUserApi extends AbstractApi {

	@Autowired
	AdminUserService adminUserService;
	
	@ApiOperation("分页获取系统用户信息")
	@ApiAuthorized("admin:user:query")
	@PostMapping("/admin/user/list")
	public ApiResult<PageInfo<AdminUserRespDTO>> queryList(@RequestBody QueryAdminUserReqDTO reqDto) {
		PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
		Page<AdminUserRespDTO> page = adminUserService.queryList(reqDto);
		return new ApiResult<>(page.toPageInfo());
	}
	
	@ApiOperation("创建用户")
	@ApiAuthorized("admin:user:create")
	@PostMapping("/admin/user/create")
	public ApiResult<Boolean> createUser(@RequestBody @Validated AdminUserReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
		return new ApiResult<>(adminUserService.createUser(reqDto));
	}
	
	@ApiOperation("修改用户")
	@ApiAuthorized("admin:user:modify")
	@PostMapping("/admin/user/modify")
	public ApiResult<Boolean> modifyUser(@RequestBody @Validated AdminUserReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
		return new ApiResult<>(adminUserService.modifyUser(reqDto));
	}
	
	@ApiOperation("删除用户信息")
	@ApiAuthorized("admin:user:delete")
	@PostMapping("/admin/user/{userId}/delete")
	public ApiResult<Boolean> deleteUser(@PathVariable Integer userId) {
		return new ApiResult<>(adminUserService.deleteUser(userId));
	}
	
	@ApiOperation("获取回收员用户信息列表")
	@GetMapping("/admin/user/recycling/list")
	public ApiResult<List<RecyclingUserRespDTO>> queryRecyclingUserList() {
		return new ApiResult<>(adminUserService.queryRecyclingUserList(Constant.ROLE_RECYCLING));
	}
}
