package com.hand.jsonXMl;

import com.hand.domain.School;
import com.hand.domain.User;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hand on 2017/4/21.
 */
public class JsonXmlUtils {

    public static void main(String[] args){
        User user = new User();
        user.setId(1);
        user.setName("张三");
        List<String> hobbys = new ArrayList<>();
        School school = new School();
        school.setCalzz("3");
        school.setNumber(20);
        hobbys.add("swim");
        hobbys.add("footbool");
        user.setHobbys(hobbys);
        user.setSchool(school);
        JSONObject jsonObject = JSONObject.fromObject(user);
//        System.out.println(jsonObject);
//        System.out.println("---------------------------");
//        String XMLString = JsonXmlUtils.Json2XML(jsonObject.toString());
//        String jsonS = "{'returnCode':'SUCCESS','returnMsg':'这是一个错误'}";
//        System.out.println(XMLString);
//        System.out.println("------------------------");
//        String jsonString = JsonXmlUtils.XML2Json(XMLString);
//        System.out.println(jsonString);
//        System.out.println("-------------------------------");
//        String xmlS = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//                "\t<returnCode>SUCCESS</returnCode>\n" +
//                "\t<returnMsg>这是一个错误</returnMsg>\n" +
//                "\t";
        System.out.println(jsonObject.toString());
        String s = JsonXmlUtils.Json2XML(jsonObject.toString());
        System.out.println(s);
        String xml = JsonXmlUtils.XML2Json(s);
        System.out.println(xml);

        Map<String,String> result = new HashMap<>();
        result.put("returnCode", "ERROR");
        result.put("returnMsg", "这是一个错误");
        JSONObject jsonObject1 = JSONObject.fromObject(result);
        String x = JsonXmlUtils.Json2XML(jsonObject1.toString());
        System.out.println(x);
    }



    public static String Json2XML(String jsonString){
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        XMLSerializer xmlSerializer =new XMLSerializer();
       // xmlSerializer.addNamespace("ray","www.rays.com");
       //xmlSerializer.setSkipNamespaces(false);
       xmlSerializer.setElementName("element");
       xmlSerializer.setTypeHintsEnabled(false);
       xmlSerializer.setTypeHintsCompatibility(false);
        xmlSerializer.setRootName("xml");
       // xmlSerializer.clearNamespaces();
        return xmlSerializer.write(jsonObject);
    }

    public static String XML2Json(String XMLString){
        XMLSerializer xmlSerializer =new XMLSerializer();
        xmlSerializer.setRootName("xml");
        xmlSerializer.setElementName("element");
        return xmlSerializer.read(XMLString).toString();
    }
}
