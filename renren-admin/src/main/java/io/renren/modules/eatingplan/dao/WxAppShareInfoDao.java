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
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置信息
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface WxAppShareInfoDao extends BaseMapper<WxAppShareInfo> {


}
