package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(String userName,Long shareUid){

        Map map = new HashMap<>();

        //保存用户信息到数据库
        List<Users> users = usersInfoService.queryByUserName(userName);
        Users user = new Users();

        String isNewUser = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (users.size() > 0) {
            user = users.get(0);
            user.setLastLoginTime(sdf.format(new Date()));
            usersInfoService.update(user);
            isNewUser = "N";

        }else {//注册
            user.setCreateTime(sdf.format(new Date()));
            user.setUserName(userName);
            //设置分享者id
            if(shareUid != null) {
                user.setShareUid(shareUid);
            }
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

        usersInfoService.save(user);//保存user信息
        user = usersInfoService.queryByUserName(user.getUserName()).get(0);
        return user;
    }

    /**
     * 更新
     * @param user
     */
    @RequestMapping("/updateUser")
    public R updateUser(Users user) {

        if(usersInfoService.update(user)) return R.ok();

        return R.error();
    }


}
