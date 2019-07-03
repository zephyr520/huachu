package com.huachu.api.recycling.appFront;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huachu.common.web.AbstractApi;

import io.swagger.annotations.Api;

@Api(description = "app回收单接口")
@RestController
@RequestMapping("/app/recycling")
public class AppFrontRecyclingListApi extends AbstractApi {
	
}
