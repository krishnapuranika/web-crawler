package com.buildit.webcrawler.crawler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebCrawlerTest {
    private WebCrawler testee;

    @BeforeEach
    public void setup() {
        testee = new WebCrawler();
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void crawlerWhenTheUrlIsEmpty(String input) {
        Throwable malformedUrl = assertThrows(IllegalArgumentException.class, () -> {
            testee.crawlTheWeb("");
        });
        Assertions.assertTrue(malformedUrl.getMessage().contains("Must supply a valid URL"));
    }

    @Test
    public void crawlerWhenTheUrlIsInvalid() {
        Throwable malformedUrl = assertThrows(IllegalArgumentException.class, () -> {
            testee.crawlTheWeb("invalid");
        });
        Assertions.assertTrue(malformedUrl.getMessage().contains("Malformed URL:"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://wiprodigital.com", "https://buildit.wiprodigital.com"})
    public void crawlerCrawlsTheWebSiteForInternalUrls(String inputUrl) throws Exception {
        HashSet<String> resultUrls = testee.crawlTheWeb(inputUrl);
        Assertions.assertFalse(resultUrls.isEmpty());
        resultUrls.forEach(link -> Assertions.assertTrue(link.contains(inputUrl)));
    }

}
