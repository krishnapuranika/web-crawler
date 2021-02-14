package com.buildit.webcrawler.controller;

import com.buildit.webcrawler.crawler.WebCrawler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

/**
 * Friendly end point to retrieve the web crawler output given a input url
 * Ref: http://localhost:8080/web-crawler/crawl?url=https://buildit.wiprodigital.com
 */
@RestController
public class WebCrawlerController {

    @GetMapping("/web-crawler/crawl")
    public Set<String> crawlWeb(@RequestParam String url) throws IOException {
        WebCrawler webCrawler = new WebCrawler();
        return webCrawler.crawlTheWeb(url);
    }
}
