package com.buildit.webcrawler.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class WebCrawlerTest {
    private static final String HOMEPAGE = "http://homepage.com";
    private WebCrawler testee;

    @Mock
    private WebDocumentService webDocumentService;

    @BeforeEach
    public void setup() throws IOException {
        testee = new WebCrawler(webDocumentService);
        Mockito.when(webDocumentService.getDocumentElements(anyString())).thenReturn(new Elements());
    }

    /**
     * Home page has two links, one internal and one external
     * @throws IOException
     */
    @Test
    public void testCrawlerRecursionForSingleLevel() throws IOException {

        Mockito.when(webDocumentService.getDocumentElements(HOMEPAGE)).thenReturn(setUpFirstLevelMock());

        HashSet<String> resultUrls = testee.crawl(HOMEPAGE);
        assertEquals(1, resultUrls.size());
        resultUrls.forEach(link -> Assertions.assertTrue(link.contains(HOMEPAGE)));
    }

    /**
     * Home page has two links, one internal and one external
     * Internal link of home page has 3 more links, two internal and one extenal
     * @throws IOException
     */
    @Test
    public void testCrawlerRecursionForMultipleLevels() throws IOException {

        Mockito.when(webDocumentService.getDocumentElements(HOMEPAGE)).thenReturn(setUpFirstLevelMock());
        Mockito.when(webDocumentService.getDocumentElements("http://homepage.com/firstPage")).thenReturn(setUpSecondLevelMock());

        HashSet<String> resultUrls = testee.crawl(HOMEPAGE);
        assertEquals(3, resultUrls.size());
        resultUrls.forEach(link -> Assertions.assertTrue(link.contains(HOMEPAGE)));
    }

    private Elements setUpFirstLevelMock() {
        Elements homepageElements = new Elements();
        homepageElements.add(getAnElementForUrl("http://homepage.com/firstPage"));
        homepageElements.add(getAnElementForUrl("http://external.com/firstpage"));
        return homepageElements;
    }

    private Elements setUpSecondLevelMock() {
        Elements firstPageElements = new Elements();
        firstPageElements.add(getAnElementForUrl("http://homepage.com/firstPage/internal_1"));
        firstPageElements.add(getAnElementForUrl("http://homepage.com/firstpage/internal_2"));
        firstPageElements.add(getAnElementForUrl("http://external.com/firstpage/external"));
        return firstPageElements;
    }

    private Element getAnElementForUrl(String url) {
        return new Element(Tag.valueOf("a"), "")
                .text("Fake Website")
                .attr("href", url)
                .attr("target", "_blank");
    }

}
