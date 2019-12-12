/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.h5.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.annotation.DataFilter;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.eatingplan.entity.IncomeHistory;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.h5.dao.IncomeHistoryDao;
import io.renren.modules.h5.dao.UserBaseInfoDao;
import io.renren.modules.h5.service.IncomeHistoryService;
import io.renren.modules.h5.service.UserBaseInfoService;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class IncomeHistoryServiceImpl extends ServiceImpl<IncomeHistoryDao, IncomeHistory> implements IncomeHistoryService {


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String uid = (String)params.get("uid");
		String type = (String)params.get("type");
		IPage<IncomeHistory> page = null;
		if("directIncome".equals(type)) {
			page = this.page(
					new Query<IncomeHistory>().getPage(params),
					new QueryWrapper<IncomeHistory>()
							.eq(StringUtils.isNotBlank(uid),"uid", uid)
							.eq(StringUtils.isNotBlank(type), "type", Constant.ONE)
			);
		} else if ("indirectIncome".equals(type)) {
			page = this.page(
					new Query<IncomeHistory>().getPage(params),
					new QueryWrapper<IncomeHistory>()
							.eq(StringUtils.isNotBlank(uid),"uid", uid)
							.eq(StringUtils.isNotBlank(type), "type", Constant.TWO)
							.or()
							.eq(StringUtils.isNotBlank(type), "type", Constant.THREE)
			);
		}

		return new PageUtils(page);
	}


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
