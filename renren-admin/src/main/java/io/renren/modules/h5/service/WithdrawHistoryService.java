package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.WithdrawHistory;

import java.util.List;


public interface WithdrawHistoryService extends IService<WithdrawHistory> {


    List<WithdrawHistory> queryByUid(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(WithdrawHistory arg);

    void update(WithdrawHistory arg);

}
