package io.renren.modules.eatingplan.controller;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/eatingplan")
public class FoodsInfoController {

    @Autowired
    private FoodsConfService foodsConfService;

    @Autowired
    private UserFoodsService userFoodsService;

    /**
     * 根据类型获取早，中，晚餐数据
     * @return
     */
    @RequestMapping("/foods")
    public List getFoods(String type) {
        List<FoodsEntity> foodsList = foodsConfService.list(new QueryWrapper<FoodsEntity>().eq("type",type));
        return foodsList;
    }


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

        userFoodsService.saveConfig(breakfast);
        userFoodsService.saveConfig(lunch);
        userFoodsService.saveConfig(dinner);
    }

    @RequestMapping("/getUserFoods")
    public R getUserFoods(Long uid) {
        List<UserFoodsEntity> userFoods = userFoodsService.queryFoodsInfo(uid);
        List breakfastArray = new ArrayList();
        List lunchArray = new ArrayList();
        List dinnerArray = new ArrayList();
        for (UserFoodsEntity foods : userFoods) {
            if(foods.getType().equals("breakfast")) {
                foods.getFoodsArray();
            }
        }
        return R.ok();
    }

}
