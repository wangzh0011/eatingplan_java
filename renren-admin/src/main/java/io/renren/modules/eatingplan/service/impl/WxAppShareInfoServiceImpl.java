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
import io.renren.modules.eatingplan.dao.UsersInfoDao;
import io.renren.modules.eatingplan.dao.WxAppShareInfoDao;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WxAppShareInfoServiceImpl extends ServiceImpl<WxAppShareInfoDao, WxAppShareInfo> implements WxAppShareInfoService {


	@Override
	public List<WxAppShareInfo> query(Long shareuid) {

		List<WxAppShareInfo> shareInfos = baseMapper.selectList(new QueryWrapper<WxAppShareInfo>().eq("shareuid",shareuid));

		return shareInfos;
	}

	@Override
	public List<WxAppShareInfo> query(Long shareuid, String ispay) {
		List<WxAppShareInfo> shareInfos = baseMapper.selectList(new QueryWrapper<WxAppShareInfo>().eq("shareuid",shareuid)
				.eq("is_pay",ispay));

		return shareInfos;
	}

	@Override
	public boolean save(WxAppShareInfo shareInfo) {
		baseMapper.insert(shareInfo);
		return true;
	}







}
