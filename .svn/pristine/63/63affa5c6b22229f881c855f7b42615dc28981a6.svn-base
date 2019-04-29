package com.huachu.api.repairshop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huachu.common.exception.BOException;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.RepairShopService;
import com.huachu.dto.request.RepairShopReqDTO;
import com.huachu.dto.request.query.QueryRepairShopReqDTO;
import com.huachu.dto.response.RepairShopRespDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 修理厂信息接口
 * @author zephyr
 * @date 2018/12/31
 */
@Api(description = "修理厂信息接口")
@RestController
public class RepairShopApi extends AbstractApi {

    @Autowired
    RepairShopService repairShopService;

    @ApiOperation("分页查询修理厂信息")
    @PostMapping("/repair/shop/list")
    public ApiResult<PageInfo<RepairShopRespDTO>> queryList(@RequestBody QueryRepairShopReqDTO reqDto) {
        PageHelper.startPage(reqDto.getPageNo(), reqDto.getPageSize());
        Page<RepairShopRespDTO> page = repairShopService.queryList(reqDto);
        return new ApiResult<>(page.toPageInfo());
    }

    @ApiOperation("创建修理厂信息")
    @PostMapping("/repair/shop/create")
    public ApiResult<Boolean> create(@RequestBody @Validated RepairShopReqDTO reqDto, BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
        return new ApiResult<>(repairShopService.create(reqDto));
    }

    @ApiOperation("修改修理厂信息")
    @PostMapping("/repair/shop/modify")
    public ApiResult<Boolean> modify(@RequestBody @Validated RepairShopReqDTO reqDto, BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            List<ObjectError> errors = errorResult.getAllErrors();
            throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
        }
        return new ApiResult<>(repairShopService.modify(reqDto));
    }

    @ApiOperation("删除修理厂信息")
    @PostMapping("/repair/shop/{id}/delete")
    public ApiResult<Boolean> delete(@PathVariable Integer id) {
        return new ApiResult<>(repairShopService.delete(id));
    }
}
