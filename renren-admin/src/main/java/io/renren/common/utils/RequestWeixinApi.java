package io.renren.common.utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RequestWeixinApi {

    /**
     *
     * @param url
     * @param method 请求方法
     * @param object 通过POST请求时的对象信息
     * @return
     */
    public static Object requestApi(String url, String method, Object object) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        //设置超时
        requestFactory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        Object result = null;
        if (method.equals("POST")) {
            result = restTemplate.postForEntity(url,object,byte[].class);
        } else if (method.equals("GET")) {
            result= restTemplate.getForObject(url,String.class);
        }

        return result;
    }

}
