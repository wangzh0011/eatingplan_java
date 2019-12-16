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
import io.renren.modules.eatingplan.entity.EatingPlan;
import io.renren.modules.h5.dao.EatingPlanDao;
import io.renren.modules.h5.service.EatingPlanService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EatingPlanServiceImpl extends ServiceImpl<EatingPlanDao, EatingPlan> implements EatingPlanService {


	@Override
	public List<EatingPlan> queryByUid(Long uid,String myReport) {

		List<EatingPlan> plan = baseMapper.selectList(new QueryWrapper<EatingPlan>()
				.eq("uid",uid)
				.eq("my_report",myReport)
		);

		return plan;
	}

	@Override
	public List<EatingPlan> queryByUid(Long uid) {

		List<EatingPlan> plan = baseMapper.selectList(new QueryWrapper<EatingPlan>()
				.eq("uid",uid)
		);

		return plan;
	}

	@Override
	public boolean save(EatingPlan plan) {
		baseMapper.insert(plan);
		return true;
	}

	@Override
	public void update(EatingPlan plan) {
		baseMapper.updateById(plan);
	}


}
