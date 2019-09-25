package io.renren.modules.eatingplan.controller;

import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eatingplan")
public class WxAppShareInfoController extends BaseController{

    @Autowired
    private WxAppShareInfoService wxAppShareInfoService;

    @RequestMapping("/setShareInfo")
    public void setShareInfo(WxAppShareInfo shareInfo){
        log.info("WxAppShareInfo ==> " + shareInfo);
        wxAppShareInfoService.save(shareInfo);
    }
}
