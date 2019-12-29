/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.salarytool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.salarytool.dao.SalaryInfoDao;
import io.renren.modules.salarytool.entity.SalaryInfo;
import io.renren.modules.salarytool.service.SalaryInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SalaryInfoServiceImpl extends ServiceImpl<SalaryInfoDao, SalaryInfo> implements SalaryInfoService {


	@Override
	public List<SalaryInfo> query(String openid) {

		List<SalaryInfo> user = baseMapper.selectList(new QueryWrapper<SalaryInfo>().eq("temp",openid));

		return user;
	}

	@Override
	public List<SalaryInfo> queryByUid(Long uid) {

		List<SalaryInfo> salaryInfo = baseMapper.selectList(new QueryWrapper<SalaryInfo>().eq("sid",uid));

		return salaryInfo;
	}


	@Override
	public boolean save(SalaryInfo salaryInfo) {
		baseMapper.insert(salaryInfo);
		return true;
	}

	@Override
	public void update(SalaryInfo salaryInfo) {
		baseMapper.updateById(salaryInfo);
	}


}
