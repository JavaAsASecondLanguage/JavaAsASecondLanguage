package io.github.javaasasecondlanguage.lecture07.practice1;

import org.junit.jupiter.api.Test;

import java.util.List;

public class HackerNewsListener {
    /**
     * Practice: Collect data about terms from Hacker News
     */
    @Test
    void hackerNewsAnalytics() {
        var analytics = new Analytics();
        analytics.analyzeHackerNews(List.of(
                "java", "kotlin", "python", "k8s", "hacker",
                "c++", "graham", "trump", "biden", "usa", "russia"
        ));
        System.out.println(analytics.getStats());
    }
}
