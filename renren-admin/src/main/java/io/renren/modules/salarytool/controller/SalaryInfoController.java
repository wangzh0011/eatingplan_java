package io.renren.modules.salarytool.controller;

import io.renren.common.utils.R;
import io.renren.modules.salarytool.entity.SalaryInfo;
import io.renren.modules.salarytool.service.SalaryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salaryTool")
public class SalaryInfoController {

    @Autowired
    private SalaryInfoService salaryInfoService;

    @RequestMapping("/saveUserInfo")
    public R saveUserInfo(SalaryInfo salaryInfo) {

        List<SalaryInfo> list = salaryInfoService.query(salaryInfo.getTemp());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //更新
        if(list.size() > 0) {
            SalaryInfo salaryInfo_db = list.get(0);
            if(salaryInfo_db.getUpdateTime().contains(sdf.format(new Date()))){
                return R.error("亲，每日只能设置一次哦^_^");
            }
            salaryInfo.setId(salaryInfo_db.getId());
            salaryInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            salaryInfoService.update(salaryInfo);
        }

        //新建
        salaryInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        salaryInfo.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if(salaryInfoService.save(salaryInfo)) {
            List<SalaryInfo> list1 = salaryInfoService.query(salaryInfo.getTemp());
            return R.ok().put("salaryInfo",list1.get(0));
        }
        return R.error();
    }


    @RequestMapping("/getSalaryInfo")
    public R getSalaryInfo(SalaryInfo salaryInfo) {
        List<SalaryInfo> salaryInfoList = salaryInfoService.queryByCondition(salaryInfo);
        List<SalaryInfo> salary = salaryInfoService.query(salaryInfo.getTemp());
        int ranking = 0;
        for (int i = 0; i < salaryInfoList.size(); i++) {
            if(Integer.valueOf(salary.get(0).getSalary()) <= Integer.valueOf(salaryInfoList.get(i).getSalary())) {
                ranking = i;
                break;
            }
        }

        //数字格式化
        double percentTemp = (double) ranking / salaryInfoList.size();
        DecimalFormat df = new DecimalFormat("0%");
        String percent = df.format(percentTemp);

        return R.ok().put("percent",percent).put("salaryInfoList",salaryInfoList);
    }


}
