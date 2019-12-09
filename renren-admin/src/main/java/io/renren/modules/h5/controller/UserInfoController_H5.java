package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/h5")
public class UserInfoController_H5 extends BaseController{

    @Autowired
    private UsersInfoService usersInfoService;

    /**
     * 登录
     * @param userName
     * @return
     */
    @RequestMapping("/login")
    public R login(String userName){

        Map map = new HashMap<>();

        //保存openID到数据库
        List<Users> users = usersInfoService.queryByUserName(userName);
        Users user = new Users();

        String isNewUser = null;
        if (users.size() > 0) {
            user = users.get(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setLastLoginTime(sdf.format(new Date()));
            usersInfoService.update(user);
            isNewUser = "N";

        }else {//注册
            user.setUserName(userName);
            user = registerUser(user);
            isNewUser = "Y";
        }

        return R.ok().put("userInfo",user).put("isNewUser",isNewUser).put("serverId","1024").put("platformId","666");
    }


    /**
     * 注册
     * @param user
     */
    public Users registerUser(Users user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(sdf.format(new Date()));
        //uuid随机数
        String secretId = UUID.randomUUID().toString().replace("-","");
        user.setSecretId(secretId);
        usersInfoService.save(user);//保存user信息
        user = usersInfoService.query(user.getOpenid()).get(0);
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
