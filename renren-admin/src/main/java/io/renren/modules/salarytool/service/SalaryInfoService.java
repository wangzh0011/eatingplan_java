package io.renren.modules.salarytool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.salarytool.entity.SalaryInfo;

import java.util.List;


public interface SalaryInfoService extends IService<SalaryInfo> {

    List<SalaryInfo> query(String openid);

    List<SalaryInfo> queryByUid(Long uid);

    List<SalaryInfo> queryByCondition(SalaryInfo salaryInfo);

    /**
     * 保存配置信息
     */
    boolean save(SalaryInfo salaryInfo);

    void update(SalaryInfo salaryInfo);

    String queryAvg();

    String queryMoreAvg();

    String queryRanking(String openid);

}
