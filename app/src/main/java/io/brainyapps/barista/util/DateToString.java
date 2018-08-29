package io.brainyapps.barista.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToString {

    public static  String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
