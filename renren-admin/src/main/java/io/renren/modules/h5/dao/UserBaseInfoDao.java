/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.h5.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.eatingplan.entity.UserBaseInfo;
import io.renren.modules.eatingplan.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UserBaseInfoDao extends BaseMapper<UserBaseInfo> {


}
