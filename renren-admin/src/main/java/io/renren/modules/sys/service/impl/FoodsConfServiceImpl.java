/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.FoodsConfDao;
import io.renren.modules.sys.entity.FoodsEntity;
import io.renren.modules.sys.service.FoodsConfService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service
public class FoodsConfServiceImpl extends ServiceImpl<FoodsConfDao, FoodsEntity> implements FoodsConfService {


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("paramKey");

		IPage<FoodsEntity> page = this.page(
			new Query<FoodsEntity>().getPage(params),
			new QueryWrapper<FoodsEntity>()
				.like(StringUtils.isNotBlank(paramKey),"param_key", paramKey)
		);

		return new PageUtils(page);
	}

	@Override
	public void saveConfig(FoodsEntity config) {
		this.save(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(FoodsEntity config) {
		this.updateById(config);
	}

	@Override
	public void updateValueByKey(String key, String value) {

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			FoodsEntity config = this.getById(id);
		}

		this.removeByIds(Arrays.asList(ids));
	}


}
