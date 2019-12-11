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
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.h5.dao.IncomeHistoryDao;
import io.renren.modules.h5.dao.UserBaseInfoDao;
import io.renren.modules.h5.service.IncomeHistoryService;
import io.renren.modules.h5.service.UserBaseInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IncomeHistoryServiceImpl extends ServiceImpl<IncomeHistoryDao, IncomeHistory> implements IncomeHistoryService {


	@Override
	public List<IncomeHistory> queryByUid(Long uid) {

		List<IncomeHistory> income = baseMapper.selectList(new QueryWrapper<IncomeHistory>().eq("uid",uid));

		return income;
	}

	@Override
	public List<IncomeHistory> queryByUidAndDate(Long uid,String date) {

		List<IncomeHistory> income = baseMapper.selectList(new QueryWrapper<IncomeHistory>()
				.eq("uid",uid)
				.like("create_time",date)
		);

		return income;
	}

	@Override
	public boolean save(IncomeHistory income) {
		baseMapper.insert(income);
		return true;
	}

	@Override
	public void update(IncomeHistory income) {
		baseMapper.updateById(income);
	}


}
