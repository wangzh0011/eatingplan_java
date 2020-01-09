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
import io.renren.common.utils.TranslationUtil;
import io.renren.modules.salarytool.dao.SalaryInfoDao;
import io.renren.modules.salarytool.entity.SalaryInfo;
import io.renren.modules.salarytool.service.SalaryInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SalaryInfoServiceImpl extends ServiceImpl<SalaryInfoDao, SalaryInfo> implements SalaryInfoService {


	@Override
	public List<SalaryInfo> query(String openid) {

		List<SalaryInfo> salaryInfo = baseMapper.selectList(new QueryWrapper<SalaryInfo>().eq("openid",openid));

		return salaryInfo;
	}

	@Override
	public List<SalaryInfo> queryByUid(Long uid) {

		List<SalaryInfo> salaryInfo = baseMapper.selectList(new QueryWrapper<SalaryInfo>().eq("sid",uid));

		return salaryInfo;
	}

	@Override
	public List<SalaryInfo> queryByCondition(SalaryInfo salaryInfo) {

		String ageInterval = salaryInfo.getAgeInterval();
		String ages = TranslationUtil.ageIntervalTranslation(ageInterval);
		String age[] = new String[2];
		if (ages != "") {
			age = ages.split(",");
		}

		List<SalaryInfo> salaryInfoList = baseMapper.selectList(new QueryWrapper<SalaryInfo>()
				.eq(StringUtils.isNotBlank(salaryInfo.getCountry()),"country",salaryInfo.getCountry())
				.eq(StringUtils.isNotBlank(salaryInfo.getProvince()),"province",salaryInfo.getProvince())
				.eq(StringUtils.isNotBlank(salaryInfo.getCity()),"city",salaryInfo.getCity())
				.between(StringUtils.isNotBlank(ages),"age",age[0],age[1])
				.eq(StringUtils.isNotBlank(salaryInfo.getGender()),"gender",salaryInfo.getGender())
				.eq(StringUtils.isNotBlank(salaryInfo.getEducation()),"education",salaryInfo.getEducation())
				.orderByAsc("salary+0")
		);
		return salaryInfoList;
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

	@Override
	public String queryAvg() {
		return baseMapper.queryAvg();
	}

	@Override
	public String queryMoreAvg() {
		return baseMapper.queryMoreAvg();
	}

	@Override
	public String queryRanking(SalaryInfo salaryInfo) {
		return baseMapper.queryRanking(salaryInfo);
	}


}
