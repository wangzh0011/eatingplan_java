package io.renren.modules.eatingplan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.Constant;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.modules.eatingplan.entity.WxCodeUnlimitedResponse;
import io.renren.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/")
public class QcCodeController extends BaseController{

    @Value("${img.location}")
    private String location;

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/getWxacode")
    public WxCodeUnlimitedResponse getWxacode(String path) {
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
            //将微信接口返回的byte数组转换成图片
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
            //设置二维码图片名
            String imageName = "qcCode/"
                    + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + ".jpg";
            File file = new File(location + imageName);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                BufferedImage img = ImageIO.read(bais);
                ImageIO.write(img, "jpg", file);
            } catch (IOException e) {
                log.error("【二维码生成失败】: {}", e.getMessage());
            }

            res.setErrcode("0");
            res.setErrmsg("ok");
            res.setImageName(imageName);

        }

        return res;
    }

}
