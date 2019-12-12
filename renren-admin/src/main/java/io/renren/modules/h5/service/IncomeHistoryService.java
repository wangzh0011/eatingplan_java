package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.UserBaseInfo;

import java.util.List;
import java.util.Map;


public interface IncomeHistoryService extends IService<IncomeHistory> {

    PageUtils queryPage(Map<String, Object> params);

    List<IncomeHistory> queryByUid(Long uid);

    List<IncomeHistory> queryByUidAndDate(Long uid,String date);

    /**
     * 保存配置信息
     */
    boolean save(IncomeHistory income);

    void update(IncomeHistory income);

}
