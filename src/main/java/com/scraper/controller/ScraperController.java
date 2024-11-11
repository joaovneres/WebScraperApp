package com.scraper.controller;

import com.scraper.service.NewsScraperService;

public class ScraperController {
    private final NewsScraperService scraperService;

    public ScraperController() {
        this.scraperService = new NewsScraperService();
    }

    public ScraperController(NewsScraperService scraperService) {
        this.scraperService = scraperService;
    }

    public void executeScraping(int pages) {
        scraperService.scrape(pages);
        scraperService.getArticles().forEach(System.out::println);
    }
}
