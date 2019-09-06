package io.renren.modules.eatingplan.controller;

import io.renren.common.utils.Constant;
import io.renren.common.utils.IPUtils;
import io.renren.common.utils.MD5Util;
import io.renren.modules.eatingplan.entity.PayParameter;
import io.renren.modules.eatingplan.entity.UnifiedorderParameter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/eatingplan")
public class AppPayController {

    @RequestMapping("/pay")
    public Object getPayParameter(String openId, HttpServletRequest request){

        String timeStamp = String.valueOf(new Date().getTime()/1000);
        String nonceStr = UUID.randomUUID().toString().replace("-","");

        //设置统一下单参数
        UnifiedorderParameter unifiedorder = new UnifiedorderParameter();
        unifiedorder.setAppid(Constant.AppId);
        unifiedorder.setMch_id(Constant.mchId);
        unifiedorder.setNonce_str(nonceStr);
        unifiedorder.setBody("健康饮食计划");
        unifiedorder.setOut_trade_no(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (Math.random()*899+100));
        unifiedorder.setTotal_fee(1);
        unifiedorder.setSpbill_create_ip(IPUtils.getIpAddr(request));
        unifiedorder.setNotify_url(Constant.notifyUrl);
        unifiedorder.setTrade_type(Constant.tradeType);
        unifiedorder.setOpenid(openId);
        //获取统一下单参数xml
        String unifiedorderXml = getUnifiedorder(unifiedorder,Constant.key);
        String payUrl = Constant.unifiedorderUrl;
        //请求统一下单接口

        //获取小程序调取支付接口所需参数
        PayParameter pay = new PayParameter();
        pay.setNonceStr(nonceStr);
        pay.setPackage_pay("");
        pay.setSingTpye("MD5");
        pay.setTimeStamp(timeStamp);

        pay = getPayRequestParameter(pay,Constant.key);

        return pay;
    }

    /**
     * 获取小程序所需参数
     * @param pay
     * @param key
     * @return
     */
    public PayParameter getPayRequestParameter(PayParameter pay, String key){

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();

        parameters.put("appId", Constant.AppId);
        parameters.put("timeStamp", pay.getTimeStamp());
        parameters.put("nonceStr", pay.getNonceStr());
        parameters.put("package", pay.getPackage_pay());
        parameters.put("signType", pay.getSingTpye());
        //数据签名
        String paySign = createPaySign(parameters,Constant.key);
        pay.setPaySign(paySign);

        return pay;
    }

    /**
     * 将统一下单参数封装成xml
     * @param order
     * @return
     */
    public String getUnifiedorder(UnifiedorderParameter order,String key){

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();

        parameters.put("appid", order.getAppid());
        parameters.put("body", order.getBody());
        parameters.put("mch_id", order.getMch_id());
        parameters.put("nonce_str", order.getNonce_str());
        parameters.put("notify_url", order.getNotify_url());
        parameters.put("openid", order.getOpenid());
        parameters.put("out_trade_no", order.getOut_trade_no());
        parameters.put("spbill_create_ip", order.getSpbill_create_ip());
        parameters.put("total_fee", order.getTotal_fee());
        parameters.put("trade_type", order.getTrade_type());
        String sign = createPaySign(parameters,key);

        /**转换成xml格式**/
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();

            document.setXmlStandalone(true);
            Element element = document.createElement("xml");
            document.appendChild(element);
            Element appid = document.createElement("appid");
            appid.setTextContent(order.getAppid());
            Element body = document.createElement("body");
            body.setTextContent(order.getBody());
            Element mch_id = document.createElement("mch_id");
            Element nonce_str = document.createElement("nonce_str");
            Element notify_url = document.createElement("notify_url");
            Element openid = document.createElement("appid");
            Element out_trade_no = document.createElement("out_trade_no");
            Element spbill_create_ip = document.createElement("spbill_create_ip");
            Element total_fee = document.createElement("total_fee");
            Element trade_type = document.createElement("trade_type");
            Element sign = document.createElement("sign");

            element.appendChild(appid);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transformer.transform(domSource, new StreamResult(bos));
            String xml = bos.toString(Charset.forName("utf-8"));
            System.out.print(xml);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 签名算法
     *
     * 第一步：对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：
     *
     * stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
     *
     * 第二步：拼接API密钥：
     *
     * stringSignTemp=stringA+"&key=192006250b4c09247ec02edce69f6a2d" //注：key为商户平台设置的密钥key
     *
     * sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" //注：MD5签名方式
     *
     * sign=hash_hmac("sha256",stringSignTemp,key).toUpperCase()="6A9AE1657590FD6257D693A078E1C3E4BB6BA4DC30B23E0EE2496E54170DACD6" //注：HMAC-SHA256签名方式
     * @param parameters
     * @param key
     * @return
     */
    public String createPaySign(SortedMap parameters, String key){
        StringBuffer signTemp = new StringBuffer();
        Set es = parameters.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if(null != v && !"".equals(v)) {
                signTemp.append(k + "=" + v + "&");
            }
        }
        signTemp = signTemp.append("key="+key);
        //MD5加密,结果转换为大写字符
        String sign = MD5Util.md5_32Length(signTemp.toString()).toUpperCase();
        return sign;
    }

    public static void main(String[] args){

    }
}
