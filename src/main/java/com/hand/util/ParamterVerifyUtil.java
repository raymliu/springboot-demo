package com.hand.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ray Ma on 2017/5/11.
 */
public class ParamterVerifyUtil {
        public  static Timestamp verifyTimestamp(String timestampString){
            Timestamp timestamp  ;
            boolean validDate = isValidDate(timestampString);
            String reg = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
            if(timestampString.matches(reg) && validDate) {
                timestamp  = Timestamp.valueOf(timestampString);
            }else{
                throw new RuntimeException("传入日期格式异常:[ " + timestampString + " ],正确格式yyyy-MM-dd HH:mm:ss");
            }
            return timestamp;
        }

    public static Boolean verifyBoolean(String booleanString){
        Boolean flag = null ;
        if("false".equalsIgnoreCase(booleanString) || "true".equalsIgnoreCase(booleanString)){
            flag = Boolean.valueOf(booleanString);
        }else{
            throw new RuntimeException("传入boolean格式异常:[ "+booleanString+" ],请传入true或者false");
        }
        return flag;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess=false;
        }
        return convertSuccess;
    }

    public static String getValidDate(String str) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            format.setLenient(false);
        Date parse = format.parse(str);
        return parse.toString();
    }


}
