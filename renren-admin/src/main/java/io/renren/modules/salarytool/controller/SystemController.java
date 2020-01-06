package io.renren.modules.salarytool.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salaryTool")
public class SystemController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getSignature")
    public R getAccessToken(){

        String access_token = sysConfigService.getValue("access_token");

        return R.ok();

    }

}
