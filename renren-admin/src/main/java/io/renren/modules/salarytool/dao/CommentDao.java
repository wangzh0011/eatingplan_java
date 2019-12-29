/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.salarytool.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.salarytool.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息
 *
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {


}
