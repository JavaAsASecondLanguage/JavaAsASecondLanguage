package io.github.javaasasecondlanguage.lecture07.practice1;

import java.util.List;
import java.util.Map;

public class Analytics {

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms) {
        throw new RuntimeException("Not implemented");
    }


    public Map<String, Stats> getStats() {
        throw new RuntimeException("Not implemented");
    }

    public static class Stats {
        volatile int mentions;
        volatile int score;

        @Override
        public String toString() {
            return "Stats{"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + score / (mentions + 1.)
                    + '}';
        }

        public Stats(int mentions, int score) {
            this.mentions = mentions;
            this.score = score;
        }
    }
}
