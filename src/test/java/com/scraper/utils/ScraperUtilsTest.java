package com.scraper.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ScraperUtilsTest {

    @Test
    public void testParseISODateValid() {
        String isoDate = "2021-01-01T12:00:00Z";
        LocalDateTime expectedDate = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
        LocalDateTime actualDate = ScraperUtils.parseISODate(isoDate);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testParseISODateInvalid() {
        String invalidDate = "data-inválida";
        LocalDateTime beforeCall = LocalDateTime.now().minusSeconds(1);
        LocalDateTime actualDate = ScraperUtils.parseISODate(invalidDate);
        LocalDateTime afterCall = LocalDateTime.now().plusSeconds(1);

        assertTrue(actualDate.isAfter(beforeCall) && actualDate.isBefore(afterCall));
    }

    @Test
    public void testFormatDate() {
        LocalDateTime date = LocalDateTime.of(2021, 1, 1, 12, 0, 0);
        String formattedDate = ScraperUtils.formatDate(date);
        assertEquals("01/01/2021 12:00", formattedDate);
    }
}
