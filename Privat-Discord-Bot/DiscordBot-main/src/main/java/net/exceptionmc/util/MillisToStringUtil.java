package net.exceptionmc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MillisToStringUtil {

    public String formatMillis(Long millis) {

        String dateFormat = "dd.MM.yyyy - HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }
}
