package com.hand.util.encrypt;

import com.hand.exception.AesException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Created by Ray Ma on 2017/4/6.
 */
public class MsgCryptUtil {
    static Charset CHARSET = Charset.forName("utf-8");
    byte[] aesKey;
    String token ;
    String companyId;

    public MsgCryptUtil(String token,String encodingAesKey,String companyId) throws AesException {
        if (encodingAesKey.length() != 22) {
           throw new AesException(AesException.IllegalAesKey);
        }
        this.token = token ;
        this.companyId = companyId ;
        aesKey = Base64.getDecoder().decode(encodingAesKey);
    }

    // 生成4个字节的网络字节序
    byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原4个字节的网络字节序
    int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 随机生成16位字符串
    String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 对明文进行加密.
     *
     * @param text 需要加密的明文
     * @return 加密后base64编码的字符串
     * @throws AesException aes加密失败
     */
    public String encrypt( String text) throws AesException {
        String randomStr = getRandomStr();
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = text.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] companyIdBytes = companyId.getBytes(CHARSET);

        // randomStr + networkBytesOrder + text + corpid
        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(textBytes);
        byteCollector.addBytes(companyIdBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey,0,16));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] encrypted = cipher.doFinal(unencrypted);
            String base64Encrypted = Base64.getEncoder().encodeToString(encrypted);
            return base64Encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.EncryptAESError);
        }
    }

    /**
     * 对密文进行解密.
     * @param  signature 证书
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param postData 密文，对应POST请求的数据
     * @return 解密得到的明文
     * @throws AesException aes解密失败
     */
    public String decryptMSg(String signature,String timestamp,String nonce,String postData) throws AesException {
        byte[] original;
        // 验证安全签名
        String msgSignature = SHA1.getSHA1(token, timestamp, nonce, postData);
        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
            byte[] encrypted = Base64.getDecoder().decode(postData);
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AesException(AesException.DecryptAESError);
        }
        String decryptMsg, _companyId;
        try {
            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);
            // 分离16位随机字符串,网络字节序和companyId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
            int xmlLength = recoverNetworkBytesOrder(networkOrder);
            decryptMsg = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
            _companyId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length),
                CHARSET);
        } catch (Exception e) {
            throw new AesException(AesException.IllegalBuffer);
        }
        // 判断companyId是否正确
        if (!_companyId.equals(companyId)) {
            throw new AesException(AesException.ValidateCompanyIdError);
        }
        return decryptMsg;
    }

    /**
     * 用SHA1算法生成签名
     */
    public String createSignature(String token, String timeStamp, String nonce, String echoStr) throws AesException {

        // 生成安全签名
        if (timeStamp == "") {
            timeStamp = Long.toString(System.currentTimeMillis());
        }

        String signature = SHA1.getSHA1(token, timeStamp, nonce, echoStr);
        return  signature ;
    }

    /**
     * 验证URL
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param echoStr 随机串，对应URL参数的echostr
     * @return 解密之后的echostr
     * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
     */
    public String verifyURL(String msgSignature, String timestamp, String nonce, String echoStr)
            throws AesException {
        String signature = SHA1.getSHA1(token, timestamp, nonce, echoStr);

        if (!signature.equals(msgSignature)) {
            throw new AesException(AesException.ValidateSignatureError);
        }

        String result = decryptMSg(msgSignature,timestamp,nonce,echoStr);
        return result;
    }
}
