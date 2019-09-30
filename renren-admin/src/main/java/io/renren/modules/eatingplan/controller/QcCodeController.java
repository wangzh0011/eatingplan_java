package io.renren.modules.eatingplan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.Constant;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.modules.eatingplan.entity.WxCodeUnlimitedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.Buffer;

@RestController
@RequestMapping("/eatingplan")
public class QcCodeController extends BaseController{

    @RequestMapping("/getWxacode")
    public Buffer getWxacode(String path) {
        WxCodeUnlimitedResponse res = new WxCodeUnlimitedResponse();
        //获取token
        String result = (String) RequestWeixinApi.requestApi(Constant.TokenUrl_JK,Constant.GET,null);
        JSONObject json = JSON.parseObject(result);
        String token = json.getString("access_token");
        log.info(path);
        //小程序返回结果
        byte[] byteArray = null;
        ResponseEntity<byte[]> entity = (ResponseEntity<byte[]>) RequestWeixinApi.requestApi(Constant.WXACODE_URL.replace("ACCESS_TOKEN",token),Constant.POST,path);
        byteArray = entity.getBody();// 图片或错误信息

        String wxReturnStr = new String(byteArray);// 微信返回内容，byte[]转为string
        if (wxReturnStr.indexOf("errcode") != -1) {
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(wxReturnStr);
            res.setErrcode(jsonObject.get("errcode").toString());
            res.setErrmsg(jsonObject.get("errmsg").toString());
        } else {
            res.setErrcode("0");
            res.setErrmsg("ok");
            res.setBuffer(byteArray);
        }

        return null;
    }

}
