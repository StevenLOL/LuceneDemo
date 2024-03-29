package org.zeal.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by wangjw on 2015/10/10.
 */
public class JsonUtil {
    public static final String NO_DATA = "{\"data\":null}";
    public static final String NO_RESULT = "{\"result\":false}";
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        //转换json时，如果对象中属性值为null，则不生成该属性
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    /***
     * @param json
     * @return 当解析失败返回null
     * @Author: wangjw
     * @Description: 给定json字符串获得json对象
     */
    public static JsonNode josn2Object(String json) {
        try {
            return mapper.readTree(json);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /***
     * @param obj
     * @return 当解析失败返回{datas:null}
     * @Author: wangjw
     * @Description: 给定java对象生成对应json
     */
    public static String parseJson(Object obj) {

        if (obj == null) {
            return NO_DATA;
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return NO_DATA;
        }
    }

    /***
     * @param obj
     * @param root
     * @return 当解析失败返回{datas:null}
     * @Author: wangjw
     * @Description:给定java对象生成对应json,可以指定一个json的root名
     */
    public static String parseJson(Object obj, String root) {

        if (obj == null) {
            return NO_DATA;
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("{\"");
            sb.append(root);
            sb.append("\":");
            sb.append(mapper.writeValueAsString(obj));
            sb.append("}");
            return sb.toString();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return NO_DATA;
        }
    }

    /***
     * @param json
     * @param var
     * @return 若传入var为null，则默认变量名为datas
     * @Author: wangjw
     * @Description:将json字符串包装成jsonp，例如var data={}方式
     */
    public static String wrapperJsonp(String json, String var) {
        if (var == null) {
            var = "datas";
        }
        return new StringBuilder().append("var ").append(var).append("=").append(json).toString();
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hash = new HashMap<String, Integer>();
        hash.put("key1", 1);
        hash.put("key2", 2);
        hash.put("key3", 3);
        System.out.println(JsonUtil.parseJson(hash));
    }
}
