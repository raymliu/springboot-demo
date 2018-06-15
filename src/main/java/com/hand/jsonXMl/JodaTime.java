package com.hand.jsonXMl;

import org.joda.time.DateTime;

/**
 * Created by hand on 2017/4/24.
 */
public class JodaTime {

    public static void main(String[] args){
        DateTime date = new DateTime();
        System.out.println(date);
        System.out.println(date.toString("yyyy-MM-dd HH:mm:ss"));
    }
}
