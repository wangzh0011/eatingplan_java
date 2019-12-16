package io.renren.modules.eatingplan.controller;

import io.renren.common.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 设置session信息
     * @param request
     * @return
     */
    public String getSession(HttpServletRequest request) {
        //设置session信息
        String myReport = (String)request.getSession().getAttribute("myReport");
        if(myReport == null) {
            request.getSession().setAttribute("myReport", Constant.DEFAULT_REPORT);
            myReport = Constant.DEFAULT_REPORT;
        }
        return myReport;
    }

}
