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
import io.renren.modules.salarytool.dao.CommentDao;
import io.renren.modules.salarytool.entity.Comment;
import io.renren.modules.salarytool.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {


	@Override
	public List<Comment> queryById(Long id) {
		List<Comment> comment = baseMapper.selectList(new QueryWrapper<Comment>().eq("id",id));

		return comment;
	}

	@Override
	public List<Comment> queryByUid(Long uid) {

		List<Comment> comment = baseMapper.selectList(new QueryWrapper<Comment>().eq("sid",uid));

		return comment;
	}

	@Override
	public List<Comment> queryAll() {
		List<Comment> comment = baseMapper.selectList(new QueryWrapper<Comment>()
				.orderByDesc("create_time"));

		return comment;
	}


	@Override
	public boolean save(Comment comment) {
		baseMapper.insert(comment);
		return true;
	}


	@Override
	public void update(Comment comment) {
		baseMapper.updateById(comment);
	}


}
