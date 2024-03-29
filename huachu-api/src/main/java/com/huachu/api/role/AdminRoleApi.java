package com.huachu.api.role;

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
import com.huachu.common.exception.BOException;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.AdminRoleService;
import com.huachu.domain.TAdminRole;
import com.huachu.dto.request.RoleMenuIdsReqDTO;
import com.huachu.dto.request.query.QueryAdminRoleReqDTO;
import com.huachu.dto.response.AdminRoleRespDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "系统角色")
@RestController
public class AdminRoleApi extends AbstractApi {

	@Autowired
	AdminRoleService adminRoleService;
	
	@ApiOperation("所有系统角色信息列表")
	@GetMapping("/admin/role/all/list")
	public ApiResult<List<TAdminRole>> listAllRoles() {
		return new ApiResult<>(adminRoleService.queryAllRoles());
	}
	
	@ApiOperation("分页查询系统角色信息列表")
	@ApiAuthorized("admin:role:query")
	@PostMapping("/admin/role/list")
	public ApiResult<PageInfo<AdminRoleRespDTO>> queryList(@RequestBody QueryAdminRoleReqDTO reqDto) {
		PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
		Page<AdminRoleRespDTO> page = adminRoleService.queryList(reqDto);
		return new ApiResult<>(page.toPageInfo());
	}
	
	@ApiOperation("删除系统角色信息")
	@ApiAuthorized("admin:role:delete")
	@PostMapping("/admin/role/{roleId}/delete")
	public ApiResult<Boolean> deleteRole(@PathVariable Integer roleId) {
		return new ApiResult<>(adminRoleService.deleteRole(roleId));
	}
	
	@ApiOperation("角色授权")
	@ApiAuthorized("admin:role:auth")
	@PostMapping("/admin/role/auth/menu")
	public ApiResult<Boolean> roleAuthMenu(@RequestBody @Validated RoleMenuIdsReqDTO reqDto, BindingResult errorResult) {
		if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
		return new ApiResult<>(adminRoleService.roleAuth(reqDto));
	}
}
