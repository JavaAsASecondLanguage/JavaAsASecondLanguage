package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Analytics {
    private static final int MAX_DEEP_AI_REQUESTS = 10000;
    private static final int HACKER_NEWS_TIMEOUT = 10;
    private static final int DEEP_AI_TIMEOUT = 10;
    private static final int MAX_DEEP_AI_ERROR_COUNT = 10000;
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Queue<Integer> contentIdsQueue = new ConcurrentLinkedQueue<>();
    private final Queue<String> sentenceQueue = new ConcurrentLinkedQueue<>();
    private final AtomicInteger deepAiRequestCount = new AtomicInteger();
    private final Map<String, Stats> stats = new ConcurrentHashMap<>();

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms) {
        for (String term : terms) {
            stats.put(term, new Stats(0, 0));
        }
        try {
            int maxContentId = Integer.parseInt(HackerNewsClient.getLatestContentId());
            logger.info(String.format("Max content id is %d. Loading last %d contents", maxContentId, MAX_DEEP_AI_REQUESTS));
            contentIdsQueue.addAll(IntStream
                    .range(maxContentId - MAX_DEEP_AI_REQUESTS + 1, maxContentId + 1)
                    .boxed()
                    .collect(Collectors.toSet()));
            int nThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService =
                    Executors.newFixedThreadPool(nThreads);

            executorService.submit(this::loadContent);
            for (int i = 0; i < nThreads; i++) {
                executorService.submit(this::loadScores);
            }

            while (deepAiRequestCount.get() < MAX_DEEP_AI_REQUESTS) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error on hacker news analyzing", e);
        }
    }

    private void loadContent() {
        while (!contentIdsQueue.isEmpty()
                && sentenceQueue.size() < MAX_DEEP_AI_REQUESTS
                && !Thread.interrupted()) {
            try {
                Integer id = contentIdsQueue.remove();
                HackerNewsContent content = getContent(HackerNewsClient.getContent(id));
                if (content == null || content.text == null) {
                    continue;
                }
                String[] sentences = content.text.split("\\.");
                for (String sentence : sentences) {
                    if (sentence.length() > 1) {
                        sentenceQueue.add(sentence);
                    }
                }
                logger.info(String.format("Loaded content #%d", id));
                Thread.sleep(HACKER_NEWS_TIMEOUT);
            } catch (NoSuchElementException e) {
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error on content loading", e);
            }
        }
        logger.info("Finished loading content");
    }

    private void loadScores() {
        int errorCount = 0;
        while (deepAiRequestCount.get() < MAX_DEEP_AI_REQUESTS && !Thread.interrupted()) {
            try {
                String sentence = sentenceQueue.remove();
                String deepAiAnswerJson = SentimentsClient.sentiments(sentence);
                int score = getScore(deepAiAnswerJson);
                for (String word : tokenize(sentence)) {
                    stats.compute(word, (w, s) -> {
                        if (s != null) {
                            s.score.addAndGet(score);
                            s.mentions.incrementAndGet();
                        }
                        return s;
                    });
                }
                deepAiRequestCount.incrementAndGet();
                logger.info(String.format("Got scores for sentence \"%s\" - %s", sentence, score));
                Thread.sleep(DEEP_AI_TIMEOUT);
            } catch (NoSuchElementException e) {

            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error on sentence scoring", e);
                errorCount++;
                if (errorCount > MAX_DEEP_AI_ERROR_COUNT) {
                    logger.severe("Too many errors");
                    return;
                }
            }
        }
    }

    private List<String> tokenize(String sentence) {
        List<String> result = new ArrayList<>();
        String alphaNumericSentence = sentence.replaceAll("[^A-Za-z0-9]", " ");
        StringTokenizer stringTokenizer = new StringTokenizer(alphaNumericSentence);
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (!token.isBlank()) {
                result.add(token);
            }
        }
        logger.info(String.format("Tokenized \"%s\" to %s", sentence, result));
        return result;
    }

    private HackerNewsContent getContent(String hackerNewsAnswer) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.readValue(hackerNewsAnswer, HackerNewsContent.class);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error on parsing answer from hacker news", e);
        }
        return null;
    }

    private int getScore(String deepAiAnswerJson) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            DeepAiAnswer deepAiAnswer = objectMapper.readValue(deepAiAnswerJson, DeepAiAnswer.class);
            return deepAiAnswer.output.get(0).score;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error on parsing answer from deepai", e);
        }
        return 0;
    }

    private ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    public Map<String, Stats> getStats() {
        return Collections.unmodifiableMap(stats);
    }

    private static class HackerNewsContent {
        public String by;
        public long id;
        public long parent;
        public String text;
        public long time;
        public String type;
    }

    private enum Attitude {
        Verynegative(-3),
        Negative(-1),
        Neutral(0),
        Positive(1),
        Verypositive(3);

        public int score;

        Attitude(int score) {
            this.score = score;
        }
    }

    private static class DeepAiAnswer {
        public String id;
        public List<Attitude> output;
    }

    public static class Stats {
        private final AtomicInteger mentions;
        private final AtomicInteger score;

        @Override
        public String toString() {
            int s = score.get();
            int m = mentions.get();
            return "Stats{"
                    + "mentions=" + m
                    + ", score=" + s
                    + ", rating=" + s / (m + 1.)
                    + '}';
        }

        public Stats(int mentions, int score) {
            this.mentions = new AtomicInteger(mentions);
            this.score = new AtomicInteger(score);
        }
    }
}
