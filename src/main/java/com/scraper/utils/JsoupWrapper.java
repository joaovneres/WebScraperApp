package com.scraper.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class JsoupWrapper {
    public Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
