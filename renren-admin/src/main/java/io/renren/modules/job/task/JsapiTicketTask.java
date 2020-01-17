/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.job.task;

import com.alibaba.fastjson.JSON;
import io.renren.common.utils.Constant;
import io.renren.common.utils.RequestWeixinApi;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * jsapiTicketTask为spring bean的名称
 *
 * @author
 */
@Component("jsapiTicketTask")
public class JsapiTicketTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysConfigService sysConfigService;

	@Override
	public void run(String params){

		String access_token = sysConfigService.getValue("access_token");
		String result = (String) RequestWeixinApi.requestApi(Constant.Jsapi_ticketUrl.replace("ACCESS_TOKEN",access_token),"GET",null);
		Map map = (Map) JSON.parse(result);
		String ticket = (String) map.get("ticket");

//		String ticket_db = sysConfigService.getValue("ticket");

//		if(ticket_db == null) {
//			SysConfigEntity config = new SysConfigEntity();
//			config.setParamKey("ticket");
//			config.setParamValue(ticket);
//			sysConfigService.saveConfig(config);
//		} else {
			sysConfigService.updateValueByKey("ticket",ticket);
//		}

		logger.info("ticket:" + ticket);

	}
}
