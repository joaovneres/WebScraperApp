package com.scraper.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewsArticleTest {

    @Test
    public void testGettersAndSetters() {
        NewsArticle article = new NewsArticle();

        article.setUrl("http://example.com");
        article.setTitle("Título de Exemplo");
        article.setSubtitle("Subtítulo de Exemplo");
        article.setAuthor("Autor Exemplo");
        article.setPublicationDate("01/01/2021");
        article.setContent("Conteúdo de exemplo.");

        assertEquals("http://example.com", article.getUrl());
        assertEquals("Título de Exemplo", article.getTitle());
        assertEquals("Subtítulo de Exemplo", article.getSubtitle());
        assertEquals("Autor Exemplo", article.getAuthor());
        assertEquals("01/01/2021", article.getPublicationDate());
        assertEquals("Conteúdo de exemplo.", article.getContent());
    }

    @Test
    public void testConstructor() {
        NewsArticle article = new NewsArticle(
                "http://example.com",
                "Título de Exemplo",
                "Subtítulo de Exemplo",
                "Autor Exemplo",
                "01/01/2021",
                "Conteúdo de exemplo."
        );

        assertEquals("http://example.com", article.getUrl());
        assertEquals("Título de Exemplo", article.getTitle());
        assertEquals("Subtítulo de Exemplo", article.getSubtitle());
        assertEquals("Autor Exemplo", article.getAuthor());
        assertEquals("01/01/2021", article.getPublicationDate());
        assertEquals("Conteúdo de exemplo.", article.getContent());
    }

    @Test
    public void testToString() {
        NewsArticle article = new NewsArticle(
                "http://example.com",
                "Título de Exemplo",
                "Subtítulo de Exemplo",
                "Autor Exemplo",
                "01/01/2021",
                "Conteúdo de exemplo."
        );

        String expected = "URL: http://example.com\n" +
                "Title: Título de Exemplo\n" +
                "Subtitle: Subtítulo de Exemplo\n" +
                "Author: Autor Exemplo\n" +
                "Publication Date: 01/01/2021\n" +
                "Content: Conteúdo de exemplo.\n";

        assertEquals(expected, article.toString());
    }
}
