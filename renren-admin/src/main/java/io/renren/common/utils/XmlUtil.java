package io.renren.common.utils;

import ch.qos.logback.core.joran.spi.XMLUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {

//    public static Map<String, String> doXMLParse(String xmlStr) throws JDOMException, IOException {
//        xmlStr = xmlStr.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
//
//        Map<String, String> xmlMap = new HashMap<>();
//        InputStream in = new ByteArrayInputStream(xmlStr.getBytes("UTF-8"));
//        SAXBuilder builder = new SAXBuilder();
//        Document doc = builder.build(in);
//        Element root = doc.getRootElement();
//        List<Element> list = root.getChildren();
//        Iterator<Element> it = list.iterator();
//        while (it.hasNext()) {
//            Element e = (Element) it.next();
//            String k = e.getName();
//            String v = "";
//            List<Element> children = e.getChildren();
//            if (children.isEmpty()) {
//                v = e.getTextNormalize();
//            } else {
//                v = XMLUtil.getXMLStr(children);
//            }
//            xmlMap.put(k, v);
//        }
//        in.close(); // 关闭流
//        return xmlMap;
//    }

}
