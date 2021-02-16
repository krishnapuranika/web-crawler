package com.buildit.webcrawler.crawler;

import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WebDocumentServiceTest {

    private WebDocumentService testee;

    @BeforeEach
    public void setUp() {
        testee = new WebDocumentService();
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void getDocumentElementsWhenTheUrlIsEmpty(String input) {
        Throwable malformedUrl = assertThrows(IllegalArgumentException.class, () -> {
            testee.getDocumentElements("");
        });
        assertTrue(malformedUrl.getMessage().contains("Must supply a valid URL"));
    }

    @Test
    public void getDocumentElementsWhenTheUrlIsInvalid() {
        Throwable malformedUrl = assertThrows(IllegalArgumentException.class, () -> {
            testee.getDocumentElements("invalid");
        });
        assertTrue(malformedUrl.getMessage().contains("Malformed URL:"));
    }

    @Test
    public void getDocumentElementsWhenTheUrlIsValid() throws IOException {
        Elements documentElements = testee.getDocumentElements("https://www.google.co.uk");
        assertFalse(documentElements.isEmpty());
    }
}
