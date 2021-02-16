package com.buildit.webcrawler.crawler;

import org.jsoup.HttpStatusException;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.jsoup.Jsoup.connect;

/**
 * Separated out to a service as it helps writing unit tests
 * using mocks
 */
@Service
public class WebDocumentService {

    private static final String QUERY_REF = "a[href]";

    public Elements getDocumentElements(String url) throws IOException {
        try {
            final Document document = connect(url).get();
            return document.select(QUERY_REF);
        } catch (HttpStatusException | UnsupportedMimeTypeException exception) {
            //return a blank document, when there is error fetching a valid URL
            return new Elements();
        }
    }
}
