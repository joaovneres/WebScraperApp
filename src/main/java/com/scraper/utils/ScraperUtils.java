package com.scraper.utils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class ScraperUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static LocalDateTime parseISODate(String dateString) {
        try {
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateString);
            return offsetDateTime.toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDateTime.now();
        }
    }

    public static String formatDate(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }
}
