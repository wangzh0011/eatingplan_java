package io.renren.modules.eatingplan.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/eatingplan")
public class UserInfoController {
    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 登录
     * @param code
     * @return
     */
    @RequestMapping("/login")
    public Object login(@PathVariable String code){

        //请求url
        String url = Constant.OpenIdUrl.replace("JSCODE",code);

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //设置超时
        requestFactory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String result = restTemplate.getForObject(url,String.class);

        //获取openid
        String openId = (String) JSON.parseObject(result, Map.class).get("openid");
        log.info("***************openId*************** " + openId);

        //保存openID到数据库
        new QueryWrapper<>();

        return JSON.parse(result);
    }
}
