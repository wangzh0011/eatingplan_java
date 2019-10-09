package io.renren.modules.eatingplan.controller;

import io.renren.common.utils.*;
import io.renren.modules.eatingplan.entity.PayParameter;
import io.renren.modules.eatingplan.entity.UnifiedorderParameter;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppPayController extends BaseController{

    @Autowired
    private SysConfigService sysConfigService;

    private Integer rmb = Integer.valueOf(sysConfigService.getValue("RMB"));

    /**
     * 发起支付
     * @param openId
     * @param request
     * @return
     */
    @RequestMapping("/pay")
    public Object getPayParameter(String openId, HttpServletRequest request){

        String timeStamp = String.valueOf(new Date().getTime()/1000);
        String nonceStr = UUID.randomUUID().toString().replace("-","");

        //设置统一下单参数
        UnifiedorderParameter unifiedorder = new UnifiedorderParameter();
        unifiedorder.setAppid(Constant.AppId_JK);
        unifiedorder.setMch_id(Constant.mchId);
        unifiedorder.setNonce_str(nonceStr);
        unifiedorder.setBody("jk");
        unifiedorder.setOut_trade_no(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + Math.round(Math.random()*899+100));
        unifiedorder.setTotal_fee(rmb*100);
        unifiedorder.setSpbill_create_ip(IPUtils.getIpAddr(request));
        unifiedorder.setNotify_url(Constant.notifyUrl);
        unifiedorder.setTrade_type(Constant.tradeType);
        unifiedorder.setOpenid(openId);
        //获取统一下单参数xml
        String unifiedorderXml = getUnifiedorder(unifiedorder);
        log.info("统一下单参数xml==============>>" + unifiedorderXml);
        String payUrl = Constant.unifiedorderUrl;
        //请求统一下单接口
        String prepayId = null;
        try {
//            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//            //设置超时
//            requestFactory.setConnectTimeout(5000);
//
//            RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//            String result = restTemplate.postForObject(payUrl,unifiedorderXml,String.class);

//            Object result = RequestWeixinApi.requestApi(payUrl,Constant.POST,unifiedorderXml);

            String xmlStr = HttpUtil.doPostToStr(payUrl,unifiedorderXml);
            log.info("调用统一接口返回结果：" + xmlStr);
            prepayId = XmlUtil.getXmlAttribute(xmlStr,"prepay_id");
            log.info("prepayId:" + prepayId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取小程序调取支付接口所需参数
        PayParameter pay = new PayParameter();
        pay.setNonceStr(nonceStr);
        pay.setPackage_pay("prepay_id=" + prepayId);
        pay.setSingTpye("MD5");
        pay.setTimeStamp(timeStamp);

        pay = getPayRequestParameter(pay);

        return pay;
    }

    /**
     * 获取小程序所需参数
     * @param pay
     * @return
     */
    public PayParameter getPayRequestParameter(PayParameter pay){

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();

        parameters.put("appId", Constant.AppId_JK);
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
    public String getUnifiedorder(UnifiedorderParameter order){

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

        String xml = null;
        /**转换成xml格式**/
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document document = db.newDocument();

            document.setXmlStandalone(true);
            Element element = document.createElement("xml");
            document.appendChild(element);

            /**xml子元素**/
            Element appid = document.createElement("appid");
            appid.setTextContent(order.getAppid());
            element.appendChild(appid);

            Element body = document.createElement("body");
            body.setTextContent(order.getBody());
            element.appendChild(body);

            Element mch_id = document.createElement("mch_id");
            mch_id.setTextContent(order.getMch_id());
            element.appendChild(mch_id);

            Element nonce_str = document.createElement("nonce_str");
            nonce_str.setTextContent(order.getNonce_str());
            element.appendChild(nonce_str);

            Element notify_url = document.createElement("notify_url");
            notify_url.setTextContent(order.getNotify_url());
            element.appendChild(notify_url);

            Element openid = document.createElement("openid");
            openid.setTextContent(order.getOpenid());
            element.appendChild(openid);

            Element out_trade_no = document.createElement("out_trade_no");
            out_trade_no.setTextContent(order.getOut_trade_no());
            element.appendChild(out_trade_no);

            Element spbill_create_ip = document.createElement("spbill_create_ip");
            spbill_create_ip.setTextContent(order.getSpbill_create_ip());
            element.appendChild(spbill_create_ip);

            Element total_fee = document.createElement("total_fee");
            total_fee.setTextContent(String.valueOf(order.getTotal_fee()));
            element.appendChild(total_fee);

            Element trade_type = document.createElement("trade_type");
            trade_type.setTextContent(order.getTrade_type());
            element.appendChild(trade_type);

            Element sign = document.createElement("sign");
            sign.setTextContent(createPaySign(parameters,Constant.key));//签名
            element.appendChild(sign);
            /**xml子元素**/


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            transformer.transform(domSource, new StreamResult(bos));
            xml = bos.toString(Charset.forName("utf-8"));
            xml = xml.substring(xml.indexOf("<",1));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return xml;
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
