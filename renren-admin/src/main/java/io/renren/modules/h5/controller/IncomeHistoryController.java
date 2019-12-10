package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.h5.service.IncomeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/h5")
public class IncomeHistoryController {

    @Autowired
    private IncomeHistoryService incomeHistoryService;

    @RequestMapping("/getInfomations")
    public R getInfomations(Long uid) {

        List<IncomeHistory> list = incomeHistoryService.queryByUid(uid);
        //计算
        for (IncomeHistory h : list) {

        }

        return R.ok();

    }


}
