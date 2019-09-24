package io.renren.modules.eatingplan.controller;

import io.renren.modules.eatingplan.entity.Parameter;
import io.renren.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eatingplan")
public class ParameterController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getParameters")
    public Parameter getParameters(){
        Parameter p = new Parameter();
        p.setRMB(sysConfigService.getValue("RMB"));
        p.setCoin(sysConfigService.getValue("coin"));
        p.setCoinContext(sysConfigService.getValue("coinDesc"));
        return p;
    }

}
