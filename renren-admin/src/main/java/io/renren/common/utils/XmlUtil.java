package io.renren.common.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

public class XmlUtil {


    public static String getXmlAttribute(String xml,String tagName) {
        Document doc = null;
        String element = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            Iterator iter = rootElt.elementIterator(tagName); // 获取根节点下的子节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                element = recordEle.getText(); // 拿到节点下值

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return element;
    }

}
