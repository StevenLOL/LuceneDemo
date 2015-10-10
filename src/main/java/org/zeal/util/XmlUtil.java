package org.zeal.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

/**
 * Created by wangjw on 2015/10/10.
 */
public class XmlUtil {
    private static String noResult = "<root>no result</root>";

    /**
     * @param obj
     * @return
     * @Author:wangjw
     * @Description: 将java对象转化为xml格式的字符串
     */
    public static String parseObjToXmlString(Object obj) {
        if (obj == null) {
            return noResult;
        }
        StringWriter sw = new StringWriter();
        JAXBContext jAXBContext;
        Marshaller marshaller;
        try {
            jAXBContext = JAXBContext.newInstance(obj.getClass());
            marshaller = jAXBContext.createMarshaller();
            marshaller.marshal(obj, sw);
            return sw.toString();
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return noResult;
    }

    /**
     * @param xml
     * @return
     * @Author: wangjw
     * @Description: 将xml String对象转化为xml对象
     */
    public static Document createFromString(String xml) {
        try {
            return DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param xpath
     * @param node
     * @return
     * @Author: wangjw
     * @Description: 获取指定xpath的文本，当解析失败返回null
     */
    public static String getTextFromNode(String xpath, Node node) {
        try {
            return node.selectSingleNode(xpath).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param path
     * @return xml文件对应的Document
     * @Author: wangjw
     * @Description: 读取xml文件
     */
    public static Document createFromPath(String path) {
        return createFromString(readFile(path));
    }

    /**
     * @param path
     * @return 返回文件内容字符串
     * @Author: wangjw
     * @Description: 读文件
     */
    private static String readFile(String path) {
        File file = new File(path);
        FileInputStream fileInputStream;
        StringBuffer sb = new StringBuffer();
        try {
            fileInputStream = new FileInputStream(file);
            //错误使用UTF-8读取内容
            String charset = CharsetUtil.getStreamCharset(file.toURI().toURL(), "utf-8");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, charset);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                s = s.replaceAll("\t", "").trim();
                if (s.length() > 0) {
                    sb.append(s);
                }
            }
            fileInputStream.close();
            bufferedReader.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {
    }
}
