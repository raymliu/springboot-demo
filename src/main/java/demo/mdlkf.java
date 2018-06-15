package demo;

import com.hand.exception.AesException;
import com.hand.util.encrypt.MsgCryptUtil;

/**
 * Created by hand on 2017/4/14.
 */
public class mdlkf {
    public static void main(String[] args) throws AesException {
        String aesKey = "sdvsdfvsdfvvbfvfbgrde5";
        String token = "abc123";
        String companyOid = "e4b4a421-0355-4449sdfadxzcvzxbzsfadsfsadfasdfasdfdsadfadsfasdfasdf-a610-26ff99322ab1";
        MsgCryptUtil msgCryptUtil = new MsgCryptUtil(token,aesKey,companyOid);
        String text = "[{\"costCenterfghfgs===--OID\":null,\"namsdfasdxcxcxcxcxc--==++++fasdfasdfe\":\"成本中心1\",\"companyOID\":\"59028034-d08a-458a-b916-eadb51066025\",\"companyName\":null,\"costCenterItems\":[],\"enabled\":null}]";
        String encrypt = msgCryptUtil.encrypt(text);
        System.out.println(encrypt);

        String timestmap = "19900908";
        String nonce = "209876";
        String signature = msgCryptUtil.createSignature(token, timestmap, nonce, encrypt);
        String s = msgCryptUtil.decryptMSg(signature, timestmap, nonce, encrypt);
        System.out.println(s);
    }



}
