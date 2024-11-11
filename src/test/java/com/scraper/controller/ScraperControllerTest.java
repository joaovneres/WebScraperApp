package com.scraper.controller;

import com.scraper.model.NewsArticle;
import com.scraper.service.NewsScraperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ScraperControllerTest {

    @Mock
    private NewsScraperService scraperService;

    private ScraperController scraperController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        scraperController = new ScraperController(scraperService);
    }

    @Test
    public void testExecuteScraping() {
        int pages = 5;
        List<NewsArticle> articles = Arrays.asList(
                new NewsArticle("http://example.com/1", "Título 1", "Subtítulo 1", "Autor 1", "01/01/2021", "Conteúdo 1"),
                new NewsArticle("http://example.com/2", "Título 2", "Subtítulo 2", "Autor 2", "02/01/2021", "Conteúdo 2")
        );

        when(scraperService.getArticles()).thenReturn(articles);

        scraperController.executeScraping(pages);

        verify(scraperService, times(1)).scrape(pages);
        verify(scraperService, times(1)).getArticles();
    }
}
