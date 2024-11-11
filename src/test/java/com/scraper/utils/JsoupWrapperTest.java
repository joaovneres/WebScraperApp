package com.scraper.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JsoupWrapperTest {

    @Test
    public void testConnect() throws IOException {
        String url = "http://example.com";
        Document mockDocument = mock(Document.class);

        try (MockedStatic<Jsoup> jsoupMock = mockStatic(Jsoup.class)) {
            Connection connection = mock(Connection.class);
            jsoupMock.when(() -> Jsoup.connect(url)).thenReturn(connection);
            when(connection.get()).thenReturn(mockDocument);

            JsoupWrapper wrapper = new JsoupWrapper();
            Document result = wrapper.connect(url);

            assertSame(mockDocument, result);

            jsoupMock.verify(() -> Jsoup.connect(url), times(1));
        }
    }
}
