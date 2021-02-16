package com.abouerp.zsc.library.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Abouerp
 */
public class RandomDateUtils {

    public static String dateTime(){
        return new SimpleDateFormat("yyyy-MM-dd").format(randomDate("2000-01-01","2021-01-31"));
    }


    private static Date randomDate(String beginDate,String endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);

            if(start.getTime() >= end.getTime()){
                return null;
            }
            long date = random(start.getTime(),end.getTime());
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long random(long begin,long end){
        long rtn = begin + (long)(Math.random() * (end - begin));
        if(rtn == begin || rtn == end){
            return random(begin,end);
        }
        return rtn;
    }
}
