/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.eatingplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.eatingplan.dao.UsersInfoDao;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.eatingplan.service.UsersInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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

	@Override
	public PageUtils queryMyTeamPage(Map<String, Object> params) {
		String uid = (String)params.get("uid");
		String type = (String)params.get("type");
		IPage<Users> page = null;
		if(Constant.ONE.equals(type)) {//一级代理
			page = this.page(
					new Query<Users>().getPage(params),
					new QueryWrapper<Users>()
							.eq(StringUtils.isNotBlank(uid),"share_uid", uid)
			);

		} else if (Constant.TWO.equals(type)) {//二级代理


			//一级代理数组
			List<Users> list = baseMapper.selectList(new QueryWrapper<Users>().eq(StringUtils.isNotBlank(uid),"share_uid", uid));
			String[] uids = new String[]{};
			for (int i = 0; i < list.size(); i++) {
				uids[i] = String.valueOf(list.get(i).getId());
			}

			//二级代理
			page = this.page(
					new Query<Users>().getPage(params),
					new QueryWrapper<Users>()
							.in(StringUtils.isNotBlank(uids.toString()),"share_uid", uids)
			);

		} else if (Constant.THREE.equals(type)) {//三级代理

			//一级代理数组
			List<Users> listOne = baseMapper.selectList(new QueryWrapper<Users>().eq(StringUtils.isNotBlank(uid),"share_uid", uid));
			String[] uids = new String[]{};
			for (int i = 0; i < listOne.size(); i++) {
				uids[i] = String.valueOf(listOne.get(i).getId());
			}

			//二级代理
			List<Users> listTwo = baseMapper.selectList(new QueryWrapper<Users>().in(StringUtils.isNotBlank(uids.toString()),"share_uid", uids));
			String[] uidsTwo = new String[]{};
			for (int i = 0; i< listTwo.size(); i++) {
				uidsTwo[i] = String.valueOf(listTwo.get(i).getId());
			}

			//三级代理
			page = this.page(
					new Query<Users>().getPage(params),
					new QueryWrapper<Users>()
							.in(StringUtils.isNotBlank(uidsTwo.toString()),"share_uid", uidsTwo)
			);

		}



		return new PageUtils(page);
	}


}
