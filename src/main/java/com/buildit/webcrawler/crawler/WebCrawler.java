package com.buildit.webcrawler.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class WebCrawler {

    private HashSet<String> internalUrls = new HashSet<>();

    private static final String QUERY_REF = "a[href]";
    private static final String ATTRIBUTE_KEY = "abs:href";

    public HashSet<String> crawlTheWeb(String url) throws IOException {
        crawl(url);
        return internalUrls;
    }

    private void crawl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements links = document.select(QUERY_REF);

        for (Element page : links) {
            String newLink = page.attr(ATTRIBUTE_KEY);
            if (newLink.contains(url) && !internalUrls.contains(newLink)) {
                internalUrls.add(newLink);
                crawl(newLink);
            }
        }
    }
}
