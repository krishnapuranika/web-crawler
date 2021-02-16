package com.buildit.webcrawler.crawler;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class WebCrawler {

    private static final String ATTRIBUTE_KEY = "abs:href";
    private final HashSet<String> internalUrls = new HashSet<>();
    private final WebDocumentService webDocumentService;

    public WebCrawler(WebDocumentService webDocumentService) {
        this.webDocumentService = webDocumentService;
    }

    public HashSet<String> crawl(String url) throws IOException {
        Elements links = webDocumentService.getDocumentElements(url);
        for (Element page : links) {
            String link = page.attr(ATTRIBUTE_KEY);
            if (isLinkInternal(link, url) && !internalUrls.contains(link)) {
                internalUrls.add(link);
                crawl(link);
            }
        }
        return internalUrls;
    }

    private boolean isLinkInternal(String link, String url) {
        try {
            URI uri = new URI(url);
            return link.contains(uri.getHost());
        } catch (URISyntaxException e) {
            //ignore the urls with syntax issues
            return false;
        }
    }

}
