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
import io.renren.modules.eatingplan.entity.WithdrawHistory;
import io.renren.modules.h5.dao.WithdrawHistoryDao;
import io.renren.modules.h5.service.WithdrawHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WithdrawHistoryServiceImpl extends ServiceImpl<WithdrawHistoryDao, WithdrawHistory> implements WithdrawHistoryService {


	@Override
	public List<WithdrawHistory> queryByUid(Long uid) {

		//查询未处理的数据
		List<WithdrawHistory> withdrawHistories = baseMapper.selectList(new QueryWrapper<WithdrawHistory>()
				.eq("uid",uid)
				.eq("status",0)
		);

		return withdrawHistories;
	}

	@Override
	public boolean save(WithdrawHistory withdrawHistory) {
		baseMapper.insert(withdrawHistory);
		return true;
	}

	@Override
	public void update(WithdrawHistory withdrawHistory) {
		baseMapper.updateById(withdrawHistory);
	}


}
