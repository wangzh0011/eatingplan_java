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
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersInfoServiceImpl extends ServiceImpl<UsersInfoDao, Users> implements UsersInfoService {

	@Override
	public List<Users> queryByUserName(String userName) {
		List<Users> user = baseMapper.selectList(new QueryWrapper<Users>().eq("user_name",userName));
		return user;
	}

	@Override
	public List<Users> query(String openid) {

		List<Users> user = baseMapper.selectList(new QueryWrapper<Users>().eq("openid",openid));

		return user;
	}

	@Override
	public List<Users> queryByUid(Long uid) {

		List<Users> user = baseMapper.selectList(new QueryWrapper<Users>().eq("id",uid));

		return user;
	}

	@Override
	public List<Users> queryByShareUid(Long shareUid) {

		List<Users> user = baseMapper.selectList(new QueryWrapper<Users>().eq("share_uid",shareUid));

		return user;
	}

	@Override
	public List<Users> queryByShareUidAndDate(Long shareUid,String date) {

		List<Users> user = baseMapper.selectList(new QueryWrapper<Users>()
				.eq("share_uid",shareUid)
				.like("be_agent_time",date)
		);

		return user;
	}

	@Override
	public boolean save(Users user) {
		baseMapper.insert(user);
		return true;
	}

	@Override
	public boolean update(Users user) {

		if(baseMapper.updateById(user) == 1) return true;

		return false;
	}


}
