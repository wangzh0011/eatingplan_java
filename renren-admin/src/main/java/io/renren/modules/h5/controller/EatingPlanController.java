package io.renren.modules.h5.controller;

import io.renren.common.utils.R;
import io.renren.modules.eatingplan.entity.EatingPlan;
import io.renren.modules.h5.service.EatingPlanService;
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

    @RequestMapping("/addEatingPlan")
    public R addEatingPlan(EatingPlan eatingPlan, HttpServletRequest request) {

        if(eatingPlan == null || eatingPlan.getMyReport() == null || eatingPlan.getUid() == null) {
            return R.error("未获取到用户信息，请重新登录");
        }

        request.getSession().setAttribute("myReport",eatingPlan.getMyReport());

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
    public R switchEatingPlan(String myReport, HttpServletRequest request) {

        request.getSession().setAttribute("myReport",myReport);

        return R.ok();

    }

}
