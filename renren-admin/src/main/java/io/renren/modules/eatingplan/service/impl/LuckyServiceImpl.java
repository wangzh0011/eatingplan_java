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
import io.renren.modules.eatingplan.dao.LuckyDao;
import io.renren.modules.eatingplan.entity.Lucky;
import io.renren.modules.eatingplan.service.LuckyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LuckyServiceImpl extends ServiceImpl<LuckyDao, Lucky> implements LuckyService {


	@Override
	public List<Lucky> query(Long uid) {

		List<Lucky> info = baseMapper.selectList(new QueryWrapper<Lucky>().eq("uid",uid));

		return info;
	}

	@Override
	public boolean save(Lucky info) {
		baseMapper.insert(info);
		return true;
	}

	@Override
	public boolean update(Lucky info) {
		if(baseMapper.updateById(info) == 1)
			return true;
		else
			return false;
	}


}
