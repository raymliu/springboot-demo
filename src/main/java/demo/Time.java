package demo;

import com.hand.util.SignatureCryptUtil;
import org.joda.time.DateTime;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ray Ma on 2017/10/24.
 */
public class Time {
    public static void main(String[] args){
//        long millis = DateTime.now().minusMinutes(30).getMillis();
//        System.out.println(millis);
//        System.out.println("-------------------");
//        System.out.println(DateTime.now().getMillis() - millis);
//        String c = "abccba";
//        String signature = "cdvsdfvsdfvvbfvf";
//        String context = c+"&"+millis;
//        String encrypt = SignatureCryptUtil.Encrypt(context, signature);
//        String encode = URLEncoder.encode(encrypt);
//        System.out.println(encode);
//        String decode = URLDecoder.decode(encode);
//        String decrypt = SignatureCryptUtil.Decrypt(decode,signature);
//        System.out.println(decrypt);

        List<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");
        System.out.println(a.toString());
        
    }
}
