/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.h5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.eatingplan.entity.Account;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.h5.dao.AccountDao;
import io.renren.modules.h5.dao.IncomeHistoryDao;
import io.renren.modules.h5.service.AccountService;
import io.renren.modules.h5.service.IncomeHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements AccountService {


	@Override
	public List<Account> queryByUid(Long uid) {

		List<Account> accounts = baseMapper.selectList(new QueryWrapper<Account>().eq("uid",uid));

		return accounts;
	}

	@Override
	public boolean save(Account account) {
		baseMapper.insert(account);
		return true;
	}

	@Override
	public void update(Account account) {
		baseMapper.updateById(account);
	}


}
