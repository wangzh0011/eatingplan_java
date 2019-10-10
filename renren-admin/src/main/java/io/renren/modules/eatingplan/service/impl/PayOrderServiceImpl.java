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
import io.renren.modules.eatingplan.dao.PayOrderDao;
import io.renren.modules.eatingplan.dao.WxAppShareInfoDao;
import io.renren.modules.eatingplan.entity.PayOrder;
import io.renren.modules.eatingplan.entity.WxAppShareInfo;
import io.renren.modules.eatingplan.service.PayOrderService;
import io.renren.modules.eatingplan.service.WxAppShareInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderDao, PayOrder> implements PayOrderService {


	@Override
	public List<PayOrder> query(Long uid) {

		List<PayOrder> order = baseMapper.selectList(new QueryWrapper<PayOrder>().eq("uid",uid));

		return order;
	}


	@Override
	public boolean save(PayOrder order) {
		baseMapper.insert(order);
		return true;
	}







}
