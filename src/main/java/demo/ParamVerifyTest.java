package demo;

import com.hand.util.ParamterVerifyUtil;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Ray Ma on 2017/5/11.
 */
public class ParamVerifyTest {
    
    public static void main(String[] args) throws ParseException {
//        String ts = "2011-09-04 10:09:20";
//
//        ParamterVerifyUtil paramterVerifyUtil = new ParamterVerifyUtil();
//        Timestamp timestamp = paramterVerifyUtil.verifyTimestamp(ts);
//        System.out.println(timestamp);
        
//        25//Timestamp timestamp = ParamterVerifyUtil.verifyTimestamp(ts);
//        //System.out.println(timestamp);
//        boolean validDate = ParamterVerifyUtil.isValidDate(ts);
//        //ParamterVerifyUtil.verifyTimestamp(ts);
//        System.out.println(validDate);
//        System.out.println(ParamterVerifyUtil.getValidDate(ts));
//
//        String reg = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
//        boolean matches = ts.matches(reg);
//        System.out.println(matches);


//        String booleanString = "Flse";
//        Boolean aBoolean = ParamterVerifyUtil.verifyBoolean(booleanString);
//        System.out.println(aBoolean);

        Date date = new Date();
        System.out.println(date);
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
        
    }
}
