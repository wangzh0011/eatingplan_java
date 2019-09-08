package io.renren.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

public class HttpUtil {

    /**
     * <p>方法说明: HTTP POST 请求
     * <p>编码格式: UTF8
     * <p>参数说明: String urL 请求的路径
     * <p>参数说明: String parAms 请求的参数
     * <p>返回说明: JSONObject
     * */
    public static JSONObject doPost(String url, String params) throws Exception {
        Request request = Request.Post(url);
        request.bodyByteArray(params.getBytes("UTF8"));
        Response response = request.execute();
        String jsonData = response.returnContent().asString();

        /* 转化为 JSONObject 数据 */
        JSONObject json = JSONObject.fromObject(jsonData);
        return json;
    }

    /**
     * <p>方法说明: HTTP GET 请求
     * <p>编码格式: UTF8
     * <p>参数说明: String urL 请求的路径
     * <p>返回说明: JSONObject
     * */
    public static JSONObject doGet(String url) throws Exception{
        Request request = Request.Get(url);
        request.setHeader("Content-type", "application/json;charset=UTF8");
        Response response = request.execute();
        String jsonData = response.returnContent().asString();
        JSONObject json = JSONObject.fromObject(jsonData);
        return json;
    }

    /**
     * <p>方法说明: HTTP GET 请求
     * <p>编码格式: UTF8 , 微信编码转为UTF-8
     * <p>参数说明: String urL 请求的路径
     * <p>返回说明: JSONObject
     * */
    public static JSONObject doGetUTF8(String url) throws Exception {
        Request request = Request.Get(url);
        request.setHeader("Content-type", "application/json;charset=UTF8");
        Response response = request.execute();
        String jsonData = response.returnContent().asString();
        String string = new String(jsonData.getBytes("ISO-8859-1"),"UTF-8");
        JSONObject json = JSONObject.fromObject(string);
        return json;
    }


    /**
     * <p>方法说明: HTTP POST 请求
     * <p>编码格式: UTF8
     * <p>参数说明: String urL 请求的路径
     * <p>参数说明: String parAms 请求的参数
     * <p>返回说明: String
     * */
    public static String doPostToStr(String url, String params) throws Exception {
        Request request = Request.Post(url);
        request.bodyByteArray(params.getBytes("UTF8"));
        Response response = request.execute();
        return response.returnContent().asString();
    }

    /**
     * <p>方法说明: HTTP GET 请求
     * <p>编码格式: UTF8
     * <p>参数说明: String urL 请求的路径
     * <p>返回说明: String
     * */
    public static String doGetToStr(String url) throws Exception {
        Request request = Request.Get(url);
        request.setHeader("Content-type", "application/json;charset=UTF8");
        Response response = request.execute();
        return response.returnContent().asString();
    }

}
