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
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.h5.dao.UserBaseInfoDao;
import io.renren.modules.h5.service.UserBaseInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserBaseInfoServiceImpl extends ServiceImpl<UserBaseInfoDao, UserBaseInfo> implements UserBaseInfoService {

	@Override
	public List<UserBaseInfo> queryByUserName(String userName) {
		List<UserBaseInfo> user = baseMapper.selectList(new QueryWrapper<UserBaseInfo>().eq("user_name",userName));
		return user;
	}

	@Override
	public List<UserBaseInfo> query(String openid) {

		List<UserBaseInfo> user = baseMapper.selectList(new QueryWrapper<UserBaseInfo>().eq("openid",openid));

		return user;
	}

	@Override
	public List<UserBaseInfo> queryByUid(Long uid) {

		List<UserBaseInfo> user = baseMapper.selectList(new QueryWrapper<UserBaseInfo>().eq("uid",uid));

		return user;
	}

	@Override
	public List<UserBaseInfo> queryByUid(Long uid, String myReport) {
		List<UserBaseInfo> user = baseMapper.selectList(new QueryWrapper<UserBaseInfo>()
				.eq("uid",uid)
				.eq("my_report",myReport)
		);

		return user;
	}

	@Override
	public boolean save(UserBaseInfo user) {
		baseMapper.insert(user);
		return true;
	}

	@Override
	public void update(UserBaseInfo user) {
		baseMapper.updateById(user);
	}


}
