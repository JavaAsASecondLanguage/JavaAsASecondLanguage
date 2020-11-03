package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Analytics {
    private final int downloadThreadsCount = 1;
    private final int scoredThreadsCount = 5;

    private volatile Integer deepAiRequestRejected = 0;
    private volatile Integer maxScoredCounter = 5;
    private volatile Integer maxContentId = 0;
    private final AtomicInteger maxDownloadedId = new AtomicInteger(0);
    private final AtomicInteger minDownloadedId = new AtomicInteger(0);
    private final AtomicInteger enqueuedCounter = new AtomicInteger(0);
    private final AtomicInteger sentimentsScoredCounter = new AtomicInteger(0);

    private final BlockingQueue<Article> queue = new ArrayBlockingQueue<>(5);

    private final ConcurrentHashMap<String, Stats> stats = new ConcurrentHashMap<>();

    private final Logger logger = Logger.getLogger("HackerNews-Analytics");

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms) {
        setupLoggerFileHandler();

        StringBuilder sb = new StringBuilder();
        for (String term : terms) {
            stats.put(term.toLowerCase(), new Stats());
            sb.append("\n" + term);
        }
        logger.info("Analytics terms: " + sb.toString());

        try {
            maxContentId = Integer.valueOf(HackerNewsClient.getLatestContentId());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("Analytics failed: Resource https://hacker-news.firebaseio.com unavailable");
            return;
        }
        logger.info("Analytics started from maxContentId = " + maxContentId);

        try {
            SentimentsClient.sentiments("Lets some Analytics");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Analytics failed: Resource https://deepai.org unavailable");
            return;
        }

        maxDownloadedId.set(maxContentId);
        minDownloadedId.set(maxContentId);


        int availableProcessors = Runtime.getRuntime().availableProcessors();
        logger.info("Available Processors = " + availableProcessors);
        ExecutorService es = Executors
                .newFixedThreadPool(downloadThreadsCount + scoredThreadsCount + 1);

        final long startTimeMillis = System.currentTimeMillis();

        es.submit(this::updateLatestContentId);
        for (int i = 0; i < downloadThreadsCount; ++i) {
            es.submit(this::downloadHackerNewsContent);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < scoredThreadsCount; ++i) {
            es.submit(this::getSentiments);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Analytics stopped then deepai begin to reject requests
        while (deepAiRequestRejected == 0) {
            int counter = sentimentsScoredCounter.get();
            if (counter > maxScoredCounter) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double executionTime = (double) (System.currentTimeMillis() - startTimeMillis) / 1000.;

        es.shutdown();

        int maxArticleId = maxDownloadedId.get();
        int minArticleId = minDownloadedId.get();
        int articlesCounter = (maxArticleId - minArticleId + 1);
        int scoresCounter = sentimentsScoredCounter.get();

        sb = new StringBuilder();
        sb.append("Analytics done for ")
                .append(articlesCounter)
                .append(" articles from ")
                .append(scoresCounter)
                .append(" scores\n")
                .append("Used articles with Ids from ")
                .append(minDownloadedId.get())
                .append(" till ")
                .append(maxDownloadedId.get())
                .append(" (enqueued ")
                .append(enqueuedCounter.get())
                .append(")\nExecution time: ")
                .append(executionTime)
                .append(" sec\n");

        for (Map.Entry<String, Stats> entry : stats.entrySet()) {
            sb.append(entry.getKey() + ": " + entry.getValue().toString() + "\n");
        }
        logger.info(sb.toString());
    }

    void downloadHackerNewsContent() {
        while (true) {

            // Downloading is going in two directions from the initial MaxContentId.
            // Then MaxContentId been increased latest articles are to be downloaded
            // else early articles are to be downloaded

            String direction = "Latest";
            int currentId = maxDownloadedId.getAndIncrement();
            if (currentId > maxContentId) {
                maxDownloadedId.decrementAndGet();

                direction = "Early";
                currentId = minDownloadedId.getAndDecrement();
            }

            if (currentId <= maxContentId) {
                try {
                    String content = HackerNewsClient.getContent(currentId);

                    boolean containsEntry = false;

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(content);
                    JsonNode textNode = root.get("text");
                    if (textNode != null) {
                        String text = textNode.asText();
                        if (text != null && !text.isEmpty()) {
                            text = text.toLowerCase();
                            for (Map.Entry<String, Stats> s : stats.entrySet()) {
                                if (text.contains(s.getKey())) {
                                    containsEntry = true;
                                    break;
                                }
                            }

                            if (containsEntry) {
                                try {
                                    queue.put(new Article(currentId, text));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    if (containsEntry) {
                        var sb = new StringBuilder();
                        sb.append("[")
                                .append(Thread.currentThread().getName())
                                .append("] ")
                                .append(direction)
                                .append(" Id: ")
                                .append(currentId)
                                .append(", Status : ")
                                .append(containsEntry ? "ENQUEUED" : "SKIPPED")
                                .append(", Queue size: ")
                                .append(queue.size());
                        logger.info(sb.toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void updateLatestContentId() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

            int lastMaxId = maxContentId;
            try {
                maxContentId = Integer.valueOf(HackerNewsClient.getLatestContentId());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (lastMaxId != maxContentId) {
                var sb = new StringBuilder();
                sb.append("[")
                        .append(Thread.currentThread().getName())
                        .append("] ")
                        .append("Updated maxContentId = ")
                        .append(maxContentId);
                logger.info(sb.toString());
            } else if (lastMaxId > maxContentId) {
                break;
            }
        }
    }

    Object getSentiments() throws InterruptedException {
        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Article article = queue.poll(10, TimeUnit.MILLISECONDS);
            if (article == null) {
                continue;
            }

            String sentiments = "";
            try {
                sentiments = SentimentsClient.sentiments(article.text);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }


            sentimentsScoredCounter.incrementAndGet();

            // Text include one or more sentences
            // Each sentence is scored by Sentiments API
            // Total score is calculated as sum of sentence scores
            int textScore = 0;

            JsonNode root = null;
            JsonNode sentimentsNode = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                root = objectMapper.readTree(sentiments);
                sentimentsNode = root.get("output");
                if (sentimentsNode == null) {
                    deepAiRequestRejected = 1;
                    logger.warning("DeepAi rejects response:\n" + sentiments);
                } else if (sentimentsNode.isArray()) {
                    for (JsonNode node : sentimentsNode) {
                        if (node.asText().compareTo("Verynegative") == 0) {
                            textScore -= 3;
                        }
                        if (node.asText().compareTo("Negative") == 0) {
                            textScore -= 1;
                        }
                        if (node.asText().compareTo("Positive") == 0) {
                            textScore += 1;
                        }
                        if (node.asText().compareTo("Verypositive") == 0) {
                            textScore += 3;
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            for (Map.Entry<String, Stats> s : stats.entrySet()) {
                Boolean statsChanged = false;
                if (article.text.contains(s.getKey())) {
                    statsChanged = true;
                    s.getValue().mentions.incrementAndGet();
                    s.getValue().score.add(textScore);
                }
                if (statsChanged) {
                    logger.info(s.getKey() + "(" + textScore + ") :" + article.toString());
                }
            }
        }
    }

    void setupLoggerFileHandler() {
        try {
            FileHandler fh = new FileHandler(logger.getName() + ".log");
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Stats> getStats() {
        return stats;
    }

    public static class Stats {
        volatile AtomicInteger mentions = new AtomicInteger(0);
        volatile LongAdder score = new LongAdder();

        @Override
        public String toString() {
            return "Stats {"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + score.intValue() / (mentions.get() + 1.)
                    + '}';
        }
    }

    public static class Article {
        Integer id;
        String text;

        public Article(int id, String text) {
            this.id = id;
            this.text = text;
        }

        @Override
        public String toString() {
            return "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        }
    }
}
