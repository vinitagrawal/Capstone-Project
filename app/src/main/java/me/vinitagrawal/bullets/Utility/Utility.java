package me.vinitagrawal.bullets.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static String convertDateToString(Date publishedAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        return simpleDateFormat.format(publishedAt);
    }

    public static Date convertStringToDate(String publishedAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);
        try {
            return simpleDateFormat.parse(publishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
