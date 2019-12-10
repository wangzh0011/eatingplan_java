package io.renren.modules.h5.controller;

import io.renren.common.utils.*;
import io.renren.modules.eatingplan.controller.BaseController;
import io.renren.modules.eatingplan.entity.*;
import io.renren.modules.eatingplan.service.PayOrderService;
import io.renren.modules.eatingplan.service.UsersInfoService;
import io.renren.modules.h5.service.IncomeHistoryService;
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
@RequestMapping("/h5")
public class AppPayController_H5 extends BaseController{

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private UsersInfoService usersInfoService;

    @Autowired
    private IncomeHistoryService incomeHistoryService;


    //获取系统配置
    String menberPercent = sysConfigService.getValue("memberPercent");//样例  60;0;0;0   ==>  直接受益 二级代理 三级代理 开通条件
    String agentPercent = sysConfigService.getValue("agentPercent");
    String partnerPercent = sysConfigService.getValue("partnerPercent");
    String channelPercent = sysConfigService.getValue("channelPercent");

    /**
     * 发起支付
     * @param uid
     * @param request
     * @return
     */
    @RequestMapping("/pay")
    public R getPayParameter(String uid, HttpServletRequest request){

        Double rmb_double = Double.valueOf(sysConfigService.getValue("RMB_H5")) * 100;

        Integer rmb = rmb_double.intValue();

        String timeStamp = String.valueOf(new Date().getTime()/1000);

        String nonceStr = UUID.randomUUID().toString().replace("-","");

        //设置统一下单参数
        UnifiedorderParameter unifiedorder = new UnifiedorderParameter();
        unifiedorder.setAppid(Constant.AppId_H5);
        unifiedorder.setMch_id(Constant.mchId);
        unifiedorder.setNonce_str(nonceStr);
        unifiedorder.setBody("支付减脂杀手");
        unifiedorder.setOut_trade_no(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + Math.round(Math.random()*899+100));
        unifiedorder.setTotal_fee(rmb);
        unifiedorder.setSpbill_create_ip(IPUtils.getIpAddr(request));
        unifiedorder.setNotify_url(Constant.notifyUrl);
        unifiedorder.setTrade_type(Constant.tradeType_h5);
        /**
         * WAP网站应用
         * {"h5_info": //h5支付固定传"h5_info"
         *    {"type": "",  //场景类型
         *     "wap_url": "",//WAP网站URL地址
         *     "wap_name": ""  //WAP 网站名
         *     }
         * }
         */
        unifiedorder.setScene_info("{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + Constant.notifyUrl +"\",\"wap_name\": \"脂肪杀手支付\"}}");
        //获取统一下单参数xml
        String unifiedorderXml = getUnifiedorder(unifiedorder);
        log.info("统一下单参数xml==============>>" + unifiedorderXml);
        String payUrl = Constant.unifiedorderUrl;
        //请求统一下单接口
        String prepayId = null;
        try {

            String xmlStr = HttpUtil.doPostToStr(payUrl,unifiedorderXml);
            log.info("调用统一接口返回结果：" + xmlStr);
            prepayId = XmlUtil.getXmlAttribute(xmlStr,"prepay_id");
            log.info("prepayId:" + prepayId);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return R.ok();
    }

    /**
     * 保存支付记录 并给上级代理加佣金
     * @param order
     */
    @RequestMapping("/savePayOrder")
    public void savePayOrder (PayOrder order) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //支付记录
        order.setCreateTime(sdf.format(new Date()));
        payOrderService.save(order);

        //查询userinfo
        List<Users> list = usersInfoService.queryByUid(order.getUid());
        //计算佣金
        setIncome(list,order.getTotalFee(),0);

    }

    /**
     * 查询是否有支付记录
     * @param uid
     * @return
     */
    @RequestMapping("/getPayOrder")
    public boolean getPayOrder (Long uid) {
        List<PayOrder> list = payOrderService.query(uid);
        if (list.size() > 0 ) {
            return true;
        }
        return false;
    }

    /**
     * 给上级代理计算佣金
     * @param list
     */
    public void setIncome(List<Users> list,String money,int i) {

        if(i > 2) {
            return;
        }

        Long shareUid = list.get(0).getShareUid();
        //若shareUidD不为空则给对应的用户增加佣金
        if(shareUid != null) {
            //查询shareUid的用户类型
            List<Users> listPar = usersInfoService.queryByUid(shareUid);
            String typePar = listPar.get(0).getType();
            Double income = null;
            if(Constant.MEMBER.equals(typePar)) {
                //会员
                String[] memberPercents = menberPercent.split(";");
                Double percent = Double.valueOf(memberPercents[i]) / 100;//受益百分比
                //计算受益
                income = Double.valueOf(money) * percent ;


            } else if(Constant.AGENT.equals(typePar)) {
                //代理
                String[] agentPercents = agentPercent.split(";");
                Double percent = Double.valueOf(agentPercents[i]) / 100;//受益百分比
                //计算受益
                income = Double.valueOf(money) * percent ;


            } else if(Constant.PARTNER.equals(typePar)) {
                //合伙人
                String[] partnerPercents = partnerPercent.split(";");
                Double percent = Double.valueOf(partnerPercents[i]) / 100;//受益百分比
                //计算受益
               income = Double.valueOf(money) * percent ;


            } else if(Constant.CHANNEL.equals(typePar)) {
                //渠道伙伴
                String[] channelPercents = channelPercent.split(";");
                Double percent = Double.valueOf(channelPercents[i]) / 100;//受益百分比
                //计算受益
               income = Double.valueOf(money) * percent ;

            } else {
                return;
            }

            //存入数据库
            String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            IncomeHistory incomeHistory = new IncomeHistory(shareUid,list.get(0).getId(),income,String.valueOf(i),createTime);
            incomeHistoryService.save(incomeHistory);
            //上级代理
            setIncome(listPar,money,i+1);
        }
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
        parameters.put("scene_info",order.getScene_info());

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

            Element scene_info = document.createElement("scene_info");
            scene_info.setTextContent(order.getScene_info());
            element.appendChild(scene_info);

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
