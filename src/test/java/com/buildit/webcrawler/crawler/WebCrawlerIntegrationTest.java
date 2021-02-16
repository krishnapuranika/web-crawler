package com.buildit.webcrawler.crawler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *  Integration tests to test end to end functionality
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebCrawlerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private WebDocumentService webDocumentService;

    @ParameterizedTest
    @ValueSource(strings = {"https://buildit.wiprodigital.com"})
    public void crawlerCrawlsTheWebSite(String inputUrl) throws Exception {
        WebCrawler webCrawler = new WebCrawler(webDocumentService);

        HashSet<String> resultUrls = webCrawler.crawl(inputUrl);
        assertFalse(resultUrls.isEmpty());
    }

    @Test
    public void crawlRestApiReturnsResponse() throws Exception {
        assertThat(this.testRestTemplate.getForObject(
                "http://localhost:" + port + "/web-crawler/crawl?url=https://buildit.wiprodigital.com",
                String.class))
                .contains("https://buildit.wiprodigital.com/careers/");
    }


}
