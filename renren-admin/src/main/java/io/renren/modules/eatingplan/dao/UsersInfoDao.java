/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.eatingplan.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.eatingplan.entity.Users;
import io.renren.modules.sys.entity.FoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UsersInfoDao extends BaseMapper<Users> {


}
