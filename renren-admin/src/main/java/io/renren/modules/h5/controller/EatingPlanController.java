package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.EatingPlan;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.h5.service.EatingPlanService;
import io.renren.modules.h5.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/h5")
public class EatingPlanController {

    @Autowired
    private EatingPlanService eatingPlanService;

    @Autowired
    private UsersInfoService usersInfoService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @RequestMapping("/addEatingPlan")
    public R addEatingPlan(EatingPlan eatingPlan, HttpServletRequest request) {

        if(eatingPlan == null || eatingPlan.getMyReport() == null || eatingPlan.getUid() == null) {
            return R.error("未获取到用户信息，请重新登录");
        }

        //更新当前计划
        List<Users> userList = usersInfoService.queryByUid(eatingPlan.getUid());
        userList.get(0).setCurrentPlan(eatingPlan.getMyReport());
        usersInfoService.update(userList.get(0));

//        //更新当前计划  UserInfoBase
//        List<UserBaseInfo> userBaseInfoList = userBaseInfoService.queryByUid(eatingPlan.getUid());
//        userBaseInfoList.get(0).setMyReport(eatingPlan.getMyReport());
//        userBaseInfoService.update(userBaseInfoList.get(0));
//
//        //更新当前计划  EatingPlan
//        List<EatingPlan> eatingPlanList = eatingPlanService.queryByUid(eatingPlan.getUid());
//        eatingPlanList.get(0).setMyReport(eatingPlan.getMyReport());
//        eatingPlanService.update(eatingPlanList.get(0));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        eatingPlan.setCreateTime(sdf.format(new Date()));
        eatingPlan.setTimeStamp(String.valueOf(System.currentTimeMillis()));

        List<EatingPlan> planList1 = eatingPlanService.queryByUid(eatingPlan.getUid());
        if(planList1.size() > 6) {
            return R.error("亲，您已达到最大购买数量");
        }

        List<EatingPlan> planList2 = eatingPlanService.queryByUid(eatingPlan.getUid(),eatingPlan.getMyReport());
        if(planList2.size() > 0 ) {
            return R.error("计划id已存在，不可重复设置");
        }

        eatingPlanService.save(eatingPlan);

        return R.ok();
    }

    @RequestMapping("/switchEatingPlan")
    public R switchEatingPlan(Users user, HttpServletRequest request) {

        //更新当前计划  Users
        List<Users> userList = usersInfoService.queryByUid(user.getId());
        userList.get(0).setCurrentPlan(user.getCurrentPlan());
        usersInfoService.update(userList.get(0));

//        //更新当前计划  UserInfoBase
//        List<UserBaseInfo> userBaseInfoList = userBaseInfoService.queryByUid(user.getId());
//        userBaseInfoList.get(0).setMyReport(user.getCurrentPlan());
//        userBaseInfoService.update(userBaseInfoList.get(0));
//
//        //更新当前计划  EatingPlan
//        List<EatingPlan> eatingPlanList = eatingPlanService.queryByUid(user.getId());
//        eatingPlanList.get(0).setMyReport(user.getCurrentPlan());
//        eatingPlanService.update(eatingPlanList.get(0));

        return R.ok();

    }

    @RequestMapping("/getEatingPlanList")
    public R getEatingPlanList(Long uid, HttpServletRequest request) {

        List<EatingPlan> planList = eatingPlanService.queryByUid(uid);

        List<Users> userList = usersInfoService.queryByUid(uid);

        return R.ok().put("planList",planList)
                     .put("userInfo",userList.get(0));

    }

}
