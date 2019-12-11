package io.renren.modules.h5.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.eatingplan.entity.Account;
import io.renren.modules.eatingplan.entity.IncomeHistory;

import java.util.List;


public interface AccountService extends IService<Account> {


    List<Account> queryByUid(Long uid);

    /**
     * 保存配置信息
     */
    boolean save(Account account);

    void update(Account account);

}
