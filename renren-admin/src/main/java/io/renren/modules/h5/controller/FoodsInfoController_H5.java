package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.UserFoodsEntity;
import io.renren.modules.eatingplan.service.UserFoodsService;
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

    /**
     * 保存用户食谱
     * @param breakfastArray
     * @param lunchArray
     * @param dinnerArray
     * @param uid
     */
    @RequestMapping("/saveUserFoods")
    public R saveUserFoods(@RequestParam("breakfastArray") String breakfastArray,
                           @RequestParam("lunchArray") String lunchArray,
                           @RequestParam("dinnerArray") String dinnerArray, Long uid, HttpServletRequest request)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //早餐数组
        UserFoodsEntity breakfast = new UserFoodsEntity();
        //午餐数组
        UserFoodsEntity lunch = new UserFoodsEntity();
        //晚餐数组
        UserFoodsEntity dinner = new UserFoodsEntity();

        breakfast.setUid(uid);
        breakfast.setType("breakfast");
        breakfast.setFoodsArray(breakfastArray);

        lunch.setUid(uid);
        lunch.setType("lunch");
        lunch.setFoodsArray(lunchArray);

        dinner.setUid(uid);
        dinner.setType("dinner");
        dinner.setFoodsArray(dinnerArray);

        //获取session
        int myReport = Integer.valueOf(getSession(request));

        List<UserFoodsEntity> foodsList = userFoodsService.queryFoodsInfo(uid,myReport);
        if(foodsList.size() > 0 ) {
            //更新
            Long breakfastId = null;
            Long lunchId = null;
            Long dinnerId = null;
            for (UserFoodsEntity foods : foodsList) {
                if(foods.getType().equals("breakfast")) {
                    breakfastId = foods.getId();
                } else if(foods.getType().equals("lunch")) {
                    lunchId = foods.getId();
                } else if(foods.getType().equals("dinner")) {
                    dinnerId = foods.getId();
                }
            }

            breakfast.setId(breakfastId);
            lunch.setId(lunchId);
            dinner.setId(dinnerId);

            userFoodsService.update(breakfast);
            userFoodsService.update(lunch);
            userFoodsService.update(dinner);

        } else {
            //新建
            breakfast.setCreateTime(sdf.format(new Date()));
            lunch.setCreateTime(sdf.format(new Date()));
            dinner.setCreateTime(sdf.format(new Date()));

            breakfast.setMyReport(myReport);
            lunch.setMyReport(myReport);
            dinner.setMyReport(myReport);

            userFoodsService.saveConfig(breakfast);
            userFoodsService.saveConfig(lunch);
            userFoodsService.saveConfig(dinner);
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
                           @RequestParam("dinnerArray") String dinnerArray,Long uid,int myReport)
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

        String myReport = getSession(request);

        List<UserFoodsEntity> userFoods = userFoodsService.queryFoodsInfo(uid,Integer.valueOf(myReport));
        String breakfastArray = new String();
        String lunchArray = new String();
        String dinnerArray = new String();
        String str = null;
        for (UserFoodsEntity foods : userFoods) {
            if(foods.getType().equals("breakfast")) {
                str = foods.getFoodsArray();
                breakfastArray = (str);
            } else if(foods.getType().equals("lunch")) {
                str = foods.getFoodsArray();
                lunchArray = (str);
            } else if(foods.getType().equals("dinner")) {
                str = foods.getFoodsArray();
                dinnerArray = (str);
            }
        }
        Map map = new HashMap<>();
        map.put("breakfastArray",breakfastArray);
        map.put("lunchArray",lunchArray);
        map.put("dinnerArray",dinnerArray);
        return R.ok().put("foods",map);
    }

}
