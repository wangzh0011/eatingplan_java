package io.renren.modules.eatingplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.PayOrder;
import io.renren.modules.eatingplan.entity.Users;

import java.util.List;


public interface PayOrderService extends IService<PayOrder> {

    List<PayOrder> query(Long uid);

    List<PayOrder> queryByTradeNo(String tradeNo);

    /**
     * 保存配置信息
     */
    boolean save(PayOrder order);

    public boolean update(PayOrder order);

}
