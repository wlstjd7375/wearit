package kr.wearit.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KimJS on 2016-09-07.
 */
public class DateUtil {

    @SuppressWarnings("deprecation")
    public static boolean isSameYMD(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return false;

        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
    }

    public static boolean isToday(Date date) {
        return isSameYMD(date, new Date());
    }

    public static boolean checkDay(Date date, int period) {
        if (date == null)
            return false;

        return date.getTime() + period * 24 * 60 * 60 * 1000 >= new Date().getTime();
    }

    @SuppressWarnings("deprecation")
    public static int getIntDate() {
        Date today = new Date();

        return (today.getYear() + 1900) * 10000 + (today.getMonth() + 1) * 100 + today.getDate();
    }

    public static String chageFormat(String inputDate){

        String result = inputDate;

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date tempDate = df.parse(inputDate);
            DateFormat dfout = new SimpleDateFormat("MM.dd");
            result = dfout.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
