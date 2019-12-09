package io.renren.modules.h5.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.UserFoodsEntity;
import io.renren.modules.eatingplan.service.UserFoodsService;
import io.renren.modules.sys.entity.FoodsEntity;
import io.renren.modules.sys.service.FoodsConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/h5")
public class FoodsInfoController_H5 {

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
    public void saveUserFoods(@RequestParam("breakfastArray") List breakfastArray,
                              @RequestParam("lunchArray") List lunchArray,
                              @RequestParam("dinnerArray") List dinnerArray,Long uid)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //早餐数组
        UserFoodsEntity breakfast = new UserFoodsEntity();
        breakfast.setUid(uid);
        breakfast.setType("breakfast");
        breakfast.setFoodsArray(breakfastArray.toString());
        breakfast.setCreateTime(sdf.format(new Date()));

        //午餐数组
        UserFoodsEntity lunch = new UserFoodsEntity();
        lunch.setUid(uid);
        lunch.setType("lunch");
        lunch.setFoodsArray(lunchArray.toString());
        lunch.setCreateTime(sdf.format(new Date()));

        //晚餐数组
        UserFoodsEntity dinner = new UserFoodsEntity();
        dinner.setUid(uid);
        dinner.setType("dinner");
        dinner.setFoodsArray(dinnerArray.toString());
        dinner.setCreateTime(sdf.format(new Date()));

        List<UserFoodsEntity> list = userFoodsService.queryFoodsInfo(uid);
        //设置第N份报告
        if(list.size() > 0) {
            int myReport = list.get(0).getMyReport() + 1;
            breakfast.setMyReport(myReport);
            lunch.setMyReport(myReport);
            dinner.setMyReport(myReport);
        }

        userFoodsService.saveConfig(breakfast);
        userFoodsService.saveConfig(lunch);
        userFoodsService.saveConfig(dinner);
    }

    /**
     * 获取用户食谱
     * @param uid
     * @return
     */
    @RequestMapping("/getUserFoods")
    public R getUserFoods(Long uid) {
        List<UserFoodsEntity> userFoods = userFoodsService.queryFoodsInfo(uid);
        List breakfastArray = new ArrayList();
        List lunchArray = new ArrayList();
        List dinnerArray = new ArrayList();
        String str = null;
        for (UserFoodsEntity foods : userFoods) {
            if(foods.getType().equals("breakfast")) {
                str = foods.getFoodsArray();
                breakfastArray = (List)JSON.parse(str);
            } else if(foods.getType().equals("lunch")) {
                str = foods.getFoodsArray();
                lunchArray = (List)JSON.parse(str);
            } else if(foods.getType().equals("dinner")) {
                str = foods.getFoodsArray();
                dinnerArray = (List)JSON.parse(str);
            }
        }
        Map map = new HashMap<>();
        map.put("breakfastArray",breakfastArray);
        map.put("lunchArray",lunchArray);
        map.put("dinnerArray",dinnerArray);
        return R.ok().put("foods",map);
    }

}
