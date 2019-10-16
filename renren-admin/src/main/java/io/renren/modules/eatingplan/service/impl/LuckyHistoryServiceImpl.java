/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.eatingplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.eatingplan.dao.LuckyHistoryDao;
import io.renren.modules.eatingplan.entity.LuckyHistory;
import io.renren.modules.eatingplan.service.LuckyHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LuckyHistoryServiceImpl extends ServiceImpl<LuckyHistoryDao, LuckyHistory> implements LuckyHistoryService {


	@Override
	public List<LuckyHistory> query(Long uid) {

		List<LuckyHistory> info = baseMapper.selectList(new QueryWrapper<LuckyHistory>().eq("uid",uid));

		return info;
	}

	@Override
	public boolean save(LuckyHistory info) {
		baseMapper.insert(info);
		return true;
	}

	@Override
	public void update(LuckyHistory info) {
		baseMapper.updateById(info);
	}


}
