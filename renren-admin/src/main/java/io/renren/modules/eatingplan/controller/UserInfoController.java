package io.renren.modules.eatingplan.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.Constant;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eatingplan")
public class UserInfoController extends BaseController{

    @Autowired
    private UsersInfoService usersInfoService;

    /**
     * 登录
     * @param code
     * @return
     */
    @RequestMapping("/login")
    public Object login(String code){

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


        //保存openID到数据库  创建用户
        List<Users> users = usersInfoService.query(openId);
        Users user = new Users();
        if (users.size() > 0) {
            user = users.get(0);
        }else {
            user.setOpenid(openId);
//            usersInfoService.save(user);
//            //查询新增用户的id
//            user.setId(usersInfoService.query(openId).get(0).getId());
        }

        return user;
    }


    /**
     * 注册
     * @param openid
     */
    @RequestMapping("/register")
    public Users registerUser(String openid) {
        //注册之前查看系统是否已注册该用户
        List<Users> users = usersInfoService.query(openid);
        if(users.size() > 0) {
            log.info("系统中已注册过openid为【" + openid + "】的用户");
            return null;
        }
        Users user = new Users();
        user.setOpenid(openid);
        usersInfoService.save(user);//保存user信息
        user.setId(usersInfoService.query(openid).get(0).getId());
        return user;
    }


}
