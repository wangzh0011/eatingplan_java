package io.renren.modules.eatingplan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eatingplan")
public class FoodsInfoController {

    /**
     * 获取早餐数据
     * @return
     */
    @RequestMapping("/breakfast")
    public List getBreakfast() {
        return null;
    }

    /**
     * 获取午餐数据
     * @return
     */
    @RequestMapping("/lunch")
    public List getLunch() {
        return null;
    }

    /**
     * 获取晚餐数据
     * @return
     */
    @RequestMapping("/dinner")
    public List getDinner() {
        return null;
    }
}
