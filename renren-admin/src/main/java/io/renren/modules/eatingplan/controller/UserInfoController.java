package io.renren.modules.eatingplan.controller;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.Constant;
import io.renren.common.utils.RequestWeixinApi;
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
    public Object login(String code,String type){
        String url = null;
        if("JK".equals(type)){
            //请求url
            url = Constant.OpenIdUrl_JK.replace("JSCODE",code);
        }else if("FQ".equals(type)){
            url = Constant.OpenIdUrl_FQ.replace("JSCODE",code);
        }

        String result = (String) RequestWeixinApi.requestApi(url,Constant.GET,null);

        //获取openid
        String openId = (String) JSON.parseObject(result, Map.class).get("openid");
        log.info("***************openId*************** " + openId);


        //保存openID到数据库
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
     * @param user
     */
    @RequestMapping("/register")
    public Users registerUser(Users user) {
        //注册之前查看系统是否已注册该用户
        List<Users> users = usersInfoService.query(user.getOpenid());
        if(users.size() > 0) {
            log.info("系统中已注册过openid为【" + user.getOpenid() + "】的用户");
            return null;
        }
        usersInfoService.save(user);//保存user信息
        user.setId(usersInfoService.query(user.getOpenid()).get(0).getId());
        return user;
    }

    /**
     * 更新
     * @param user
     */
    @RequestMapping("/updateUser")
    public void updateUser(Users user) {
        usersInfoService.update(user);
    }


}
