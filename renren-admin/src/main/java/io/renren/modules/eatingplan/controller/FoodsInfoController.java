package io.renren.modules.eatingplan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.sys.entity.FoodsEntity;
import io.renren.modules.sys.service.FoodsConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eatingplan")
public class FoodsInfoController {

    @Autowired
    private FoodsConfService foodsConfService;

    /**
     * 根据类型获取早，中，晚餐数据
     * @return
     */
    @RequestMapping("/foods")
    public List getFoods(String type) {
        List<FoodsEntity> foodsList = foodsConfService.list(new QueryWrapper<FoodsEntity>().eq("type",type));
        return foodsList;
    }
}
