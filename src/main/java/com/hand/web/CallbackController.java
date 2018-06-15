/**
 * Copyright (c) 2017. Hand Enterprise Solution Company. All right reserved. Project
 * Name:springboot-demo Package Name:com.hand Date:2017/4/6 0006 Create
 * By:zongyun.zhou@hand-china.com
 *
 */
package com.hand.web;

import java.util.HashMap;
import java.util.Map;

import com.hand.exception.AesException;
import com.hand.util.encrypt.MsgCryptUtil;
import com.hand.dto.RequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

@Controller
public class CallbackController {
    //json
  @RequestMapping(value = "/",produces = {"application/json"})
  public ResponseEntity<Map<String,String>> greeting(@RequestBody JSONObject body)
          throws InterruptedException, AesException {
    System.out.println("=====请求体内容是======:");
    System.out.println(body.toString());
    System.out.println("=====输出结束=====");
    Map<String, String> result = new HashMap<String, String>();
    //String xmlString  = "<xml><returnCode>ERROR</returnCode><returnMsg>这是一个错误</returnMsg></xml>";
    result.put("returnCode", "SUCCESS");
    result.put("returnMsg", "这是一个错误");

    String encodingAesKey = "sdvsdfvsdfvvbfvfbgrde5";
    String token = "abc123";
    RequestBodyDTO requestBodyDTO = JSONObject.toJavaObject(body,RequestBodyDTO.class);
    String message = requestBodyDTO.getMessage();
    String companyOid = requestBodyDTO.getCompanyOID();
    String signature = requestBodyDTO.getSignature();
    String timestamp = requestBodyDTO.getTimestamp();
    String nonce = requestBodyDTO.getNonce();
    MsgCryptUtil msgCryptUtil = new MsgCryptUtil(token,encodingAesKey,companyOid);

    String s = msgCryptUtil.decryptMSg(signature, timestamp, nonce, message);
    System.out.println("========================");
    System.out.println("解码之后的明文"+ s);

    return ResponseEntity.ok(result) ;
  }

  //xml
//  @RequestMapping(value = "/")
//  public ResponseEntity<String> greeting(@RequestBody String body)
//          throws InterruptedException, AesException, ParserConfigurationException, SAXException {
//    System.out.println("=====请求体内容是======:");
//    System.out.println(body);
//    System.out.println("=====输出结束=====");
//
//
//    //Map<String, String> result = new HashMap<String, String>();
//    String jsonString = JsonXmlUtils.XML2Json(body);
//    net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonString);
//    //net.sf.json.JSONObject.toBean(body,RequestBodyDTO.class)
//    RequestBodyDTO requestBodyDTO = (RequestBodyDTO) net.sf.json.JSONObject.toBean(jsonObject, RequestBodyDTO.class);
//
//    String token = "abc123";
//    String encodingAesKey = "sdvsdfvsdfvvbfvfbgrde5";
//    String companyOid = requestBodyDTO.getCompanyOID();
//    String signature = requestBodyDTO.getSignature();
//    String message = requestBodyDTO.getMessage();
//    String timestamp = requestBodyDTO.getTimestamp();
//    String nonce = requestBodyDTO.getNonce();
//    MsgCryptUtil msgCryptUtil = new MsgCryptUtil(token,encodingAesKey,companyOid);
//
//    String s = msgCryptUtil.decryptMSg(signature, timestamp, nonce, message);
//    System.out.println("========================");
//    System.out.println("解码之后的明文"+ s);
//
//    String xmlString  = "<xml><returnCode>ERROeturnCode><returnMsg>这是一个错误</returnMsg></xml>";
//
//    return ResponseEntity.status(300).body(xmlString) ;
//  }

}
