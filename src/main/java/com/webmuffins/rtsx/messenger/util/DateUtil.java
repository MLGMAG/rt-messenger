package com.webmuffins.rtsx.messenger.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private DateUtil() {
    }

    public static LocalDateTime stringIntoDate(String date, String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(date, dateFormat);
    }

    public static String dateIntoString(LocalDateTime date, String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
        return date.format(dateFormat);
    }
}
