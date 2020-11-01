package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

public class Analytics {
    private final ArrayBlockingQueue<Integer> newsQueue = new ArrayBlockingQueue<>(10);
    private final ConcurrentLinkedQueue<String> sentenceQueue = new ConcurrentLinkedQueue<>();
    private final int sentimentTimeout = 10;
    private final int sentenceQueueMaxSize = 20;
    private final Map<String, Stats> stats = new ConcurrentHashMap<>();
    private volatile boolean spawnerDone = false;
    private volatile boolean sentimentProcessing = false;
    private volatile boolean done = false;

    public class Stats {
        volatile int mentions;
        volatile int score;

        @Override
        public String toString() {
            return "Stats{"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + score / (mentions + 1.0)
                    + '}';
        }

        public Stats() {
            this.mentions = 0;
            this.score = 0;
        }
    }

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms, int countToAnalyze) {
        for (var term : terms) {
            stats.put(term, new Stats());
        }

        int threadsCount = Runtime.getRuntime().availableProcessors();
        var executorService = Executors.newFixedThreadPool(threadsCount);

        // One spawn task + bunch of processing tasks
        executorService.submit(() -> spawnAnalyzeTasks(countToAnalyze));

        for (int i = 1; i < threadsCount; i++) {
            executorService.submit(this::handleTasks);
        }

        System.out.printf("Use 1 spawner thread + %d worker threads\n", threadsCount - 1);
    }

    public void join() {
        try {
            while (!spawnerDone
                    || !newsQueue.isEmpty()
                    || !sentenceQueue.isEmpty()
                    || sentimentProcessing
            ) {
                System.out.printf(
                    "spawnerDone=%s newsQueue=%d sentenceQueue=%d sentimentProcessing=%s done=%s\n",
                    spawnerDone,
                    newsQueue.size(),
                    sentenceQueue.size(),
                    sentimentProcessing,
                    done
                );

                Thread.sleep(10_000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            done = true;
        }
    }

    private void spawnAnalyzeTasks(int countToAnalyze) {
        try {
            var maxId = Integer.parseInt(HackerNewsClient.getLatestContentId());
            for (int id = maxId - countToAnalyze; id < maxId; id++) {
                newsQueue.put(id);
                System.out.printf("Put news in the queue: #%d \n", id);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            spawnerDone = true;
        }
    }

    private void handleTasks() {
        try {
            while (!done) {
                try {
                    sentimentProcessing = true;
                    var nextSentence = sentenceQueue.poll();
                    while (nextSentence != null) {
                        scoreSentenceIntent(nextSentence);
                        nextSentence = sentenceQueue.poll();
                    }
                } finally {
                    sentimentProcessing = false;
                }

                while (sentenceQueue.size() < sentenceQueueMaxSize && !newsQueue.isEmpty()) {
                    var news = newsQueue.poll();
                    if (news != null) {
                        downloadHackerNewsContent(news);
                    }
                }

                Thread.sleep(10);
            }
        } catch (Exception e) { // InterruptedException
            e.printStackTrace();
        }
    }

    private void downloadHackerNewsContent(int id) throws InterruptedException {
        try {
            var content = HackerNewsClient.getContent(id);
            var mapper = new ObjectMapper();
            var root = mapper.readTree(content);
            var text = root.get("text");
            if (text != null) {
                var sentence = text.asText();
                if (sentence != null && sentence.length() > 100) {
                    sentenceQueue.add(sentence);
                    System.out.printf("Put sentence in the queue: \"%s\"\n", sentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void scoreSentenceIntent(String sentence) throws InterruptedException {
        System.out.printf("Scoring sentence: \"%s\"\n", sentence);
        try {
            var sentiments = SentimentsClient.sentiments(sentence);
            sentence = sentence.toLowerCase();

            for (var pair : stats.entrySet()) {
                if (sentence.contains(pair.getKey())) {
                    var stat = pair.getValue();
                    stat.mentions += 1;

                    var scoring = Map.of(
                            "Verynegative", -3,
                            "Negative", -1,
                            "Positive", 1,
                            "Verypositive", 3
                    );

                    for (var scoreRule : scoring.entrySet()) {
                        if (sentiments.contains(scoreRule.getKey())) {
                            stat.score += scoreRule.getValue();
                        }
                    }
                    System.out.printf("Update score of %s. %s\n", pair.getKey(), stat.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Thread.sleep(sentimentTimeout);
        }
    }

    public Map<String, Stats> getStats() {
        return stats;
    }
}
