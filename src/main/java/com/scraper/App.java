package com.scraper;

import com.scraper.controller.ScraperController;

public class App {
    private static final int PAGES_TO_LOAD = Integer.parseInt(System.getenv().getOrDefault("PAGES_TO_LOAD", "3"));

    public static void main(String[] args) {
        ScraperController controller = new ScraperController();
        controller.executeScraping(PAGES_TO_LOAD);
    }
}
