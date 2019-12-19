package io.renren.modules.h5.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.EatingPlan;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.h5.service.EatingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/h5")
public class UserInfoController_H5 extends BaseController{

    @Autowired
    private UsersInfoService usersInfoService;

    @Autowired
    private EatingPlanService eatingPlanService;

    /**
     * 登录
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody Users user, HttpServletRequest request){


        Map map = new HashMap<>();
        log.info("传入的userName为：" + user.getUserName() + ",shareUid为：" + user.getShareUid());
        //保存用户信息到数据库
        List<Users> users = usersInfoService.queryByUserName(user.getUserName());

        String isNewUser = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        EatingPlan plan = null;
        if (users.size() > 0) {
            user = users.get(0);
            user.setLastLoginTime(sdf.format(new Date()));
            usersInfoService.update(user);
            isNewUser = "N";

            //获取我的计划
            List<EatingPlan> list = eatingPlanService.queryByUid(users.get(0).getId(),user.getCurrentPlan());
            if(list.size() == 0) {
                plan = eatingPlanService.queryByUid(users.get(0).getId(),"1").get(0);
            } else {
                plan = list.get(0);
            }

        }else {
            user.setCreateTime(sdf.format(new Date()));
            //设置分享者id
            if(user.getShareUid() != null) {
                user.setShareUid(user.getShareUid());
            }

            user.setCurrentPlan(Constant.DEFAULT_REPORT);

            //注册
            user = registerUser(user);

            //保存我的计划
            plan = new EatingPlan(user.getId(),Constant.DEFAULT_REPORT,sdf.format(new Date()),String.valueOf(System.currentTimeMillis()));
            eatingPlanService.save(plan);

            isNewUser = "Y";
        }

        return R.ok().put("userInfo",user)
                .put("isNewUser",isNewUser)
                .put("serverId","1024")
                .put("platformId","666")
                .put("plan",plan);
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

        List<Users> list = usersInfoService.queryByUid(user.getId());

        if(list.size() == 0) {
            return R.error("此用户不存在");
        }

        if(usersInfoService.update(user)) return R.ok().put("userInfo",list.get(0));

        return R.error();
    }


}
