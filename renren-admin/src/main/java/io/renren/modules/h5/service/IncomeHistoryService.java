package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.UserBaseInfo;

import java.util.List;


public interface IncomeHistoryService extends IService<IncomeHistory> {


    List<IncomeHistory> queryByUid(Long uid);

    List<IncomeHistory> queryByUidAndDate(Long uid,String date);

    /**
     * 保存配置信息
     */
    boolean save(IncomeHistory income);

    void update(IncomeHistory income);

}
