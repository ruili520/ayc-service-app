package com.ayc.service.app.common.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * xml解析工具类
 *
 * @author wwq
 * @version 1.0
 * @createDate 2019/2/14 17:52
 */
public class XMLUtil {
    private XMLUtil(){}

    /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     * @param strxml
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map doXMLParse(String strxml) {

        Map m = new HashMap();

        try {
            strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

            if(null == strxml || "".equals(strxml)) {
                return null;
            }

            InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String k = e.getName();
                String v = "";
                List children = e.getChildren();
                if(children.isEmpty()) {
                    v = e.getTextNormalize();
                } else {
                    v = XMLUtil.getChildrenText(children);
                }

                m.put(k, v);
            }

            //关闭流
            in.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return m;
    }

    /**
     * 获取子结点的xml
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if(!children.isEmpty()) {
            Iterator it = children.iterator();
            while(it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if(!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @param paramMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXmlQuery(SortedMap<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set set = paramMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 微信支付将请求参数转换为xml格式的String
     *
     * @param paramMap
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<String, String> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set set = paramMap.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

}
