package com.scraper.service;

import com.scraper.model.NewsArticle;
import com.scraper.utils.JsoupWrapper;
import com.scraper.utils.ScraperUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class NewsScraperService {
    private static final String DRIVER_PATH = System.getenv().getOrDefault("BROWSER_DRIVER_PATH", Objects.requireNonNull(NewsScraperService.class.getClassLoader().getResource("chromedriver.exe")).getPath());
    private static final String BASE_URL = System.getenv().getOrDefault("NEWS_SITE_URL", "https://www.infomoney.com.br/mercados/");
    private static final String ARTICLE_SELECTOR = System.getenv().getOrDefault("ARTICLE_SELECTOR", "div[data-ds-component='card-sm']");
    private static final String TITLE_SELECTOR = System.getenv().getOrDefault("TITLE_SELECTOR", "div[data-ds-component='article-title'] h1");
    private static final String SUBTITLE_SELECTOR = System.getenv().getOrDefault("SUBTITLE_SELECTOR", "div[data-ds-component='article-title'] div");
    private static final String AUTHOR_SELECTOR = System.getenv().getOrDefault("AUTHOR_SELECTOR", "div[data-ds-component='author-small'] a");
    private static final String DATE_SELECTOR = System.getenv().getOrDefault("DATE_SELECTOR", "div[data-ds-component='author-small'] time");
    private static final String CONTENT_SELECTOR = System.getenv().getOrDefault("CONTENT_SELECTOR", "article[data-ds-component='article'] > p");
    private static final int SCROLL_MARGIN_BEFORE_FOOTER = 300;
    private List<NewsArticle> articles = new ArrayList<>();
    private WebDriver driver;
    private JsoupWrapper jsoupWrapper;

    public NewsScraperService() {
        setupWebDriver();
        this.jsoupWrapper = new JsoupWrapper();
    }

    public NewsScraperService(WebDriver driver, JsoupWrapper jsoupWrapper) {
        this.driver = driver;
        this.jsoupWrapper = jsoupWrapper;
    }

    private void setupWebDriver() {
        System.setProperty("webdriver.gecko.driver", DRIVER_PATH);
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        this.driver = new FirefoxDriver(options);
    }


    public void scrape(int pagesToLoad) {
        try {
            driver.get(BASE_URL);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            for (int i = 0; i < pagesToLoad - 1; i++) {
                scrollAndClickLoadMoreButton(jsExecutor, wait);
            }

            Document doc = Jsoup.parse(driver.getPageSource());
            extractArticles(doc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void scrollAndClickLoadMoreButton(JavascriptExecutor jsExecutor, WebDriverWait wait) throws InterruptedException {
        WebElement footer = driver.findElement(By.tagName("footer"));
        int footerPosition = footer.getLocation().getY();

        jsExecutor.executeScript("window.scrollTo(0, arguments[0] - arguments[1]);", footerPosition, SCROLL_MARGIN_BEFORE_FOOTER);
        Thread.sleep(3000);

        WebElement loadMoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Carregar mais')]")));
        wait.until(ExpectedConditions.visibilityOf(loadMoreButton));
        loadMoreButton.click();
        Thread.sleep(2500);
    }

    private void extractArticles(Document doc) {
        Elements newsElements = doc.select(ARTICLE_SELECTOR);
        processArticleElements(newsElements);
    }

    void processArticleElements(Elements newsElements) {
        List<CompletableFuture<NewsArticle>> futures = new ArrayList<>();

        for (Element newsElement : newsElements) {
            String url = newsElement.select("a").attr("href");

            CompletableFuture<NewsArticle> future = CompletableFuture.supplyAsync(() -> getContent(url));
            futures.add(future);
        }

        for (CompletableFuture<NewsArticle> future : futures) {
            try {
                NewsArticle article = future.get();
                if (article != null) {
                    articles.add(article);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    NewsArticle getContent(String url) {
        try {
            Document articleDoc = jsoupWrapper.connect(url);
            return parseArticleContent(articleDoc, url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    NewsArticle parseArticleContent(Document articleDoc, String url) {
        String title = articleDoc.select(TITLE_SELECTOR).text();
        String subtitle = articleDoc.select(SUBTITLE_SELECTOR).text();
        String author = articleDoc.select(AUTHOR_SELECTOR).text();

        String datetimeStr = articleDoc.select(DATE_SELECTOR).attr("datetime");
        LocalDateTime publicationDate = ScraperUtils.parseISODate(datetimeStr);
        String formattedDate = ScraperUtils.formatDate(publicationDate);

        Elements contentElements = articleDoc.select(CONTENT_SELECTOR);
        StringBuilder contentBuilder = new StringBuilder();
        for (Element paragraph : contentElements) {
            contentBuilder.append(paragraph.text()).append(" ");
        }
        String content = contentBuilder.toString().trim();

        return new NewsArticle(url, title, subtitle, author, formattedDate, content);
    }

    public List<NewsArticle> getArticles() {
        return articles;
    }
}
