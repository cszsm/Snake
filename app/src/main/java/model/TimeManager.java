package model;

import java.util.GregorianCalendar;

/**
 * Created by zscse on 2015. 09. 28..
 */
public class TimeManager {

    public static long getTime() {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.getTimeInMillis();
    }

    public static String getTime(long milliseconds) {
        String ms = String.valueOf(milliseconds % 1000);
        milliseconds /= 1000;
        String s = String.valueOf(milliseconds % 60);
        milliseconds /= 60;
        String min = String.valueOf(milliseconds % 60);
        milliseconds /= 60;
        String h = String.valueOf(milliseconds % 24);

        return h + ":" + min + ":" + s + "." + ms;
    }
}
