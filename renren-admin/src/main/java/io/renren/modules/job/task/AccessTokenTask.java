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
 * accessTokenTask为spring bean的名称
 *
 * @author
 */
@Component("accessTokenTask")
public class AccessTokenTask implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysConfigService sysConfigService;

	@Override
	public void run(String params){
		String result = (String) RequestWeixinApi.requestApi(Constant.TokenUrl_H5,"GET",null);
		Map map = (Map) JSON.parse(result);
		String access_token = (String) map.get("access_token");

		String access_token_db = sysConfigService.getValue("access_token");

		if(access_token_db == null) {
			SysConfigEntity config = new SysConfigEntity();
			config.setParamKey("access_token");
			config.setParamValue(access_token);
			sysConfigService.saveConfig(config);
		} else {
			sysConfigService.updateValueByKey("access_token",access_token);
		}

		logger.info("access_token:" + access_token);

	}
}
