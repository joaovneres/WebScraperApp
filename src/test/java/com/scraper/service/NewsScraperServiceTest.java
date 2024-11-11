package com.scraper.service;

import com.scraper.model.NewsArticle;
import com.scraper.utils.JsoupWrapper;
import com.scraper.utils.ScraperUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.openqa.selenium.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NewsScraperServiceTest {
    private static final String TITLE_SELECTOR = System.getenv().getOrDefault("TITLE_SELECTOR", "div[data-ds-component='article-title'] h1");
    private static final String SUBTITLE_SELECTOR = System.getenv().getOrDefault("SUBTITLE_SELECTOR", "div[data-ds-component='article-title'] div");
    private static final String AUTHOR_SELECTOR = System.getenv().getOrDefault("AUTHOR_SELECTOR", "div[data-ds-component='author-small'] a");
    private static final String DATE_SELECTOR = System.getenv().getOrDefault("DATE_SELECTOR", "div[data-ds-component='author-small'] time");
    private static final String CONTENT_SELECTOR = System.getenv().getOrDefault("CONTENT_SELECTOR", "article[data-ds-component='article'] > p");

    @Mock
    private WebDriver driver;

    @Mock
    private JsoupWrapper jsoupWrapper;

    @Mock
    private WebElement footerElement;

    @Mock
    private WebElement loadMoreButton;

    @Mock
    private Document document;

    @InjectMocks
    private NewsScraperService scraperService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        driver = mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));

        scraperService = new NewsScraperService(driver, jsoupWrapper);
    }

    @Test
    public void testScrape() throws Exception {
        when(driver.getPageSource()).thenReturn("<html></html>");
        when(driver.findElement(By.tagName("footer"))).thenReturn(footerElement);
        when(footerElement.getLocation()).thenReturn(new Point(0, 1000));
        when(driver.findElement(By.xpath("//button[contains(text(), 'Carregar mais')]"))).thenReturn(loadMoreButton);

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        when(jsExecutor.executeScript(anyString(), (Object[]) any())).thenReturn(null);

        scraperService.scrape(1);

        verify(driver, times(1)).get(anyString());
        verify(jsExecutor, times(0)).executeScript(anyString(), (Object[]) any());
    }

    @Test
    public void testGetContent() throws IOException {
        String url = "http://example.com/article";
        Document articleDoc = mock(Document.class);

        when(jsoupWrapper.connect(url)).thenReturn(articleDoc);

        when(articleDoc.select(anyString())).thenReturn(new Elements(new Element("div")));

        NewsArticle article = scraperService.getContent(url);

        assertNotNull(article);
    }

    @Test
    public void testParseArticleContent() {
        String url = "http://example.com/article";
        Document articleDoc = mock(Document.class);

        Elements titleElements = new Elements(new Element("h1").text("Título de Exemplo"));
        Elements subtitleElements = new Elements(new Element("div").text("Subtítulo de Exemplo"));
        Elements authorElements = new Elements(new Element("a").text("Autor Exemplo"));
        Elements dateElements = new Elements(new Element("time").attr("datetime", "2021-01-01T12:00:00Z"));
        Elements contentElements = new Elements(new Element("p").text("Conteúdo de exemplo."));

        when(articleDoc.select(TITLE_SELECTOR)).thenReturn(titleElements);
        when(articleDoc.select(SUBTITLE_SELECTOR)).thenReturn(subtitleElements);
        when(articleDoc.select(AUTHOR_SELECTOR)).thenReturn(authorElements);
        when(articleDoc.select(DATE_SELECTOR)).thenReturn(dateElements);
        when(articleDoc.select(CONTENT_SELECTOR)).thenReturn(contentElements);

        try (MockedStatic<ScraperUtils> scraperUtilsMock = mockStatic(ScraperUtils.class)) {
            LocalDateTime mockedDate = LocalDateTime.of(2021, 1, 1, 12, 0);
            scraperUtilsMock.when(() -> ScraperUtils.parseISODate(anyString())).thenReturn(mockedDate);
            scraperUtilsMock.when(() -> ScraperUtils.formatDate(mockedDate)).thenReturn("01/01/2021 12:00");

            NewsArticle article = scraperService.parseArticleContent(articleDoc, url);

            assertNotNull(article);
            assertEquals("Título de Exemplo", article.getTitle());
            assertEquals("Subtítulo de Exemplo", article.getSubtitle());
            assertEquals("Autor Exemplo", article.getAuthor());
            assertEquals("01/01/2021 12:00", article.getPublicationDate());
            assertEquals("Conteúdo de exemplo.", article.getContent());
        }
    }

    @Test
    public void testProcessArticleElements() throws ExecutionException, InterruptedException {
        Elements newsElements = new Elements();
        Element newsElement = new Element("div");
        newsElement.appendElement("a").attr("href", "http://example.com/article");
        newsElements.add(newsElement);

        NewsScraperService spyService = spy(scraperService);
        NewsArticle mockArticle = new NewsArticle();
        doReturn(mockArticle).when(spyService).getContent(anyString());

        spyService.processArticleElements(newsElements);

        assertEquals(1, spyService.getArticles().size());
        assertSame(mockArticle, spyService.getArticles().get(0));
    }
}
