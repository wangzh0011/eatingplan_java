package io.renren.modules.salarytool.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.common.utils.SignatureUtil;
import io.renren.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/salaryTool")
public class SystemController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getSignature")
    public R getAccessToken(String url){

        String nonceStr = UUID.randomUUID().toString().replace("-","");
        String timeStamp = String.valueOf(new Date().getTime()/1000);

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("noncestr",nonceStr);
        parameters.put("jsapi_ticket",sysConfigService.getValue("ticket"));
        parameters.put("timestamp",timeStamp);
        parameters.put("url",url);//当前网页的url

        String signature = SignatureUtil.createJsapiSign(parameters);

        Map map = new HashMap<>();
        map.put("appId",Constant.AppId_H5);
        map.put("timestamp",timeStamp);
        map.put("nonceStr",nonceStr);
        map.put("signature",signature);

        return R.ok().put("config",map);

    }

}
