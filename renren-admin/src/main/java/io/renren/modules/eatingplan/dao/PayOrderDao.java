/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.eatingplan.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.eatingplan.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付记录
 *
 * @author
 */
@Mapper
public interface PayOrderDao extends BaseMapper<PayOrder> {


}
