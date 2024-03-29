package com.huachu.api.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.huachu.common.constants.FileTypeEnum;
import com.huachu.common.exception.BOException;
import com.huachu.common.util.FileUtils;
import com.huachu.common.web.AbstractApi;
import com.huachu.common.web.ApiResult;
import com.huachu.common.web.ApiResultCode;
import com.huachu.core.service.RecyclingAccessoryFileService;
import com.huachu.dto.request.RecyclingAccessoryFileReqDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "配件图片上传接口")
@RestController
public class RecyclingAccessoryFileApi extends AbstractApi {

	@Value("${file.url}")
    private String fileUrl;
    @Value("${upload.filepath}")
    private String fileBasePath;
    @Autowired
    RecyclingAccessoryFileService recyclingAccessoryFileService;
    
    @ApiOperation("配件图片上传")
    @PostMapping("/accessory/image/upload")
    public ApiResult<String> uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        FileUtils.checkImageContentType(file);
        String pathName = FileUtils.uploadFile(file, fileBasePath, FileTypeEnum.ACCESSORY_IMAGE.getPath());
        return new ApiResult<>(fileUrl + pathName);
    }
    
    @ApiOperation("保存配件图片附件")
    @PostMapping("/accessory/file/create")
    public ApiResult<Boolean> createAccessoryFile(@RequestBody @Validated RecyclingAccessoryFileReqDTO reqDto, BindingResult errorResult) {
    	if (errorResult.hasErrors()) {
			List<ObjectError> errors = errorResult.getAllErrors();
			throw new BOException(ApiResultCode.PARAM_EMPTY, errors.get(0).getDefaultMessage());
		}
    	return new ApiResult<>(recyclingAccessoryFileService.createAccessoryFile(reqDto, getUser()));
    }
    
    @ApiOperation("删除配件图片")
    @PostMapping("/accessory/image/delete")
    public ApiResult<Boolean> deleteFile(@RequestParam String fileUrl) {
    	return new ApiResult<>(recyclingAccessoryFileService.deleteFile(fileUrl, fileBasePath));
    }
}
