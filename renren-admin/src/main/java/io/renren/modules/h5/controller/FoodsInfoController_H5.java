package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.UserFoodsEntity;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UserFoodsService;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/h5")
public class FoodsInfoController_H5 extends BaseController {

    @Autowired
    private UserFoodsService userFoodsService;

    @Autowired
    private UsersInfoService usersInfoService;

    /**
     * 保存或更新用户食谱
     * @param foodsEntity
     * @param request
     *
     */
    @RequestMapping("/saveUserFoods")
    public R saveUserFoods(UserFoodsEntity foodsEntity, HttpServletRequest request)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //获取当前计划
        List<Users> userList = usersInfoService.queryByUid(foodsEntity.getUid());
        String myReport = userList.get(0).getCurrentPlan();

        List<UserFoodsEntity> foodsList = userFoodsService.queryFoodsInfo(foodsEntity.getUid(),myReport);
        if(foodsList.size() > 0 ) {
            //更新
            foodsEntity.setId(foodsList.get(0).getId());

            userFoodsService.update(foodsEntity);

        } else {
            //新建
            foodsEntity.setCreateTime(sdf.format(new Date()));

            foodsEntity.setMyReport(myReport);

            userFoodsService.saveConfig(foodsEntity);

        }

        return R.ok();
    }

    /**
     * 更新
     * @param breakfastArray
     * @param lunchArray
     * @param dinnerArray
     * @param uid
     * @return  废弃
     */
    @RequestMapping("/updateUserFoods")
    public R updateUserFoods(@RequestParam("breakfastArray") String breakfastArray,
                           @RequestParam("lunchArray") String lunchArray,
                           @RequestParam("dinnerArray") String dinnerArray,Long uid,String myReport)
    {

        List<UserFoodsEntity> userFoods = userFoodsService.queryFoodsInfo(uid,myReport);



        //早餐数组
        UserFoodsEntity breakfast = new UserFoodsEntity();
        breakfast.setUid(uid);
        breakfast.setType("breakfast");
        breakfast.setFoodsArray(breakfastArray);

        breakfast.setMyReport(myReport);

        //午餐数组
        UserFoodsEntity lunch = new UserFoodsEntity();
        lunch.setUid(uid);
        lunch.setType("lunch");
        lunch.setFoodsArray(lunchArray);
//        lunch.setId(lunchId);
        lunch.setMyReport(myReport);

        //晚餐数组
        UserFoodsEntity dinner = new UserFoodsEntity();
        dinner.setUid(uid);
        dinner.setType("dinner");
        dinner.setFoodsArray(dinnerArray);
//        dinner.setId(dinnerId);
        dinner.setMyReport(myReport);



        return R.ok();
    }

    /**
     * 获取用户食谱
     * @param uid
     * @return
     */
    @RequestMapping("/getUserFoods")
    public R getUserFoods(Long uid, HttpServletRequest request) {

        //获取当前计划
        List<Users> userList = usersInfoService.queryByUid(uid);
        String myReport = userList.get(0).getCurrentPlan();

        List<UserFoodsEntity> userFoods = userFoodsService.queryFoodsInfo(uid,myReport);

        if(userFoods.size() == 0) {
            return R.error("无uid=" + uid + ",myReport=" + myReport +"对应的信息");
        }

        return R.ok().put("foods",userFoods.get(0));
    }

}
