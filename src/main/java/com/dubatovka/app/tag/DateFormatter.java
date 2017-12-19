package com.dubatovka.app.tag;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The class provides custom function to use on JSP pages which provides easier date and time formatting.
 */
public class DateFormatter {
    
    /**
     * Prints if given to method date or time parameter is null.
     */
    private static final String NOT_AVAILABLE = "-";
    
    /**
     * Outer forbidding to create this class instances.
     */
    private DateFormatter() {
    }
    
    /**
     * Formats given object due to given pattern.
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        return (localDateTime != null) ? localDateTime.format(DateTimeFormatter.ofPattern(pattern)) : NOT_AVAILABLE;
    }
    
    /**
     * Formats given object due to given pattern.
     */
    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return (localDate != null) ? localDate.format(DateTimeFormatter.ofPattern(pattern)) : NOT_AVAILABLE;
    }
}