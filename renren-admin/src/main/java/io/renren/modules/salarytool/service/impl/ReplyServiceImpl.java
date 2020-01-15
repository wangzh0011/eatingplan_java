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
import io.renren.modules.salarytool.dao.ReplyDao;
import io.renren.modules.salarytool.entity.Reply;
import io.renren.modules.salarytool.service.ReplyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyDao, Reply> implements ReplyService {


	@Override
	public List<Reply> queryById(Long id) {
		List<Reply> reply = baseMapper.selectList(new QueryWrapper<Reply>().eq("id",id));

		return reply;
	}

	@Override
	public List<Reply> queryByUid(Long uid) {

		List<Reply> reply = baseMapper.selectList(new QueryWrapper<Reply>().eq("sid",uid));

		return reply;
	}

	@Override
	public List<Reply> queryAllByCid(Long cid) {
		List<Reply> comment = baseMapper.selectList(new QueryWrapper<Reply>()
				.eq("cid",cid)
				.orderByDesc("create_time")
				.last("limit 100"));

		return comment;
	}

	@Override
	public Integer queryTotalNum(Long cid) {
		Integer total = baseMapper.selectCount(new QueryWrapper<Reply>().eq("cid",cid));
		return total;
	}


	@Override
	public boolean save(Reply reply) {
		baseMapper.insert(reply);
		return true;
	}


	@Override
	public void update(Reply reply) {
		baseMapper.updateById(reply);
	}


}
