package io.github.javaasasecondlanguage.lecture07.practice1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.List;

@Disabled
public class HackerNewsListener {
    /**
     * Practice: Collect data about terms from Hacker News
     */
    @Test
    void hackerNewsAnalytics() {
        var analytics = new Analytics();
        analytics.analyzeHackerNews(List.of("java", "python"), List.of("javascript"));
        System.out.println(analytics.getStats());
    }
}
