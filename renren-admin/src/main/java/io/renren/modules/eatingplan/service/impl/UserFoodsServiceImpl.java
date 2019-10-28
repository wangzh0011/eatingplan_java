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
import io.renren.modules.eatingplan.dao.UserFoodsDao;
import io.renren.modules.eatingplan.entity.UserFoodsEntity;
import io.renren.modules.eatingplan.service.UserFoodsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFoodsServiceImpl extends ServiceImpl<UserFoodsDao, UserFoodsEntity> implements UserFoodsService {


	@Override
	public List queryFoodsInfo(Long uid) {
		List<UserFoodsEntity> info = baseMapper.selectList(new QueryWrapper<UserFoodsEntity>().eq("uid",uid));
		return info;
	}

	@Override
	public void saveConfig(UserFoodsEntity config) {
		this.save(config);
	}

	@Override
	public void update(UserFoodsEntity config) {

	}

	@Override
	public void updateValueByKey(String key, String value) {

	}

	@Override
	public void deleteBatch(Long[] ids) {

	}
}
