package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.LuckyHistory;

import java.util.List;


public interface LuckyHistoryService extends IService<LuckyHistory> {

    List<LuckyHistory> query(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(LuckyHistory info);

    void update(LuckyHistory info);

}
