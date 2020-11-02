package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kotlin.Pair;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class Analytics {

    private final int queueSize = 5_000;
    private final int threadPerCpu = 32;
    private final int threadPerCpuForAi = 30;
    private final int retryCount = 50;
    private final int totalMessages = 300_000;

    private final AtomicInteger maxContentId = new AtomicInteger(Integer.MIN_VALUE);
    private final AtomicInteger currentContentId = new AtomicInteger(Integer.MIN_VALUE);
    private final AtomicInteger mention = new AtomicInteger(0);
    private final BlockingQueue<Pair<String, Integer>> queue = new ArrayBlockingQueue<>(queueSize);
    private final ConcurrentHashMap<String, Stats> stats = new ConcurrentHashMap<>();
    private final List<String> denylist = new ArrayList<>();

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this term
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms, List<String> denyList) {

        this.denylist.addAll(denyList);

        for (String term : terms) {
            stats.put(term.toLowerCase(), new Stats());
        }

        int id = getLatestContentId();
        if (id > 0) {

            printout("Current last id: " + id
                    + ", current datetime: " + dtf.format(LocalDateTime.now()));
            maxContentId.set(id);
            currentContentId.set(id - totalMessages);

            ExecutorService es = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors() * threadPerCpu);

            for (int i = 0;
                 i < Runtime.getRuntime().availableProcessors()
                         * (threadPerCpu - threadPerCpuForAi);
                 i++) {
                es.submit(this::downloadHackerNewsContent);
            }

            for (int i = 0;
                 i < Runtime.getRuntime().availableProcessors() * threadPerCpuForAi;
                 i++) {
                es.submit(this::getSentiments);
            }

            // Crawl messages
            try {
                while (true) {
                    Thread.sleep(5000);
                    if (currentContentId.get() >= maxContentId.get() && queue.isEmpty()) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printout("\nFinal stats: " + stats);
            printout("Total mentors: " + mention.get());

        }
    }

    public void downloadHackerNewsContent() {
        while (true) {
            int id = currentContentId.getAndIncrement();
            if (id <= maxContentId.get()) {
                if (id % 1000 == 0) {
                    printout("[" + Thread.currentThread().getName() + "] id: " + id
                            + ", queue size: " + queue.size()
                            + ", mentions: " + mention.get()
                            + ", current datetime: " + dtf.format(LocalDateTime.now()));
                }
                try {
                    String content = HackerNewsClient.getContent(id);
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode root = objectMapper.readTree(content);
                    JsonNode textNode = root.get("text");
                    if (textNode != null) {
                        String text = textNode.asText().toLowerCase();
                        if (!text.isBlank()) {
                            Iterator<String> s = stats.keys().asIterator();
                            while (s.hasNext()) {
                                String key = s.next();
                                if (text.contains(key)) {
                                    try {
                                        queue.put(new Pair<>(text, 0));
                                        break;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                printout("[" + Thread.currentThread().getName() + "] close download thread"
                        + ", current datetime: " + dtf.format(LocalDateTime.now()));
                break;
            }
        }
    }

    private synchronized int getLatestContentId() {
        int id = Integer.MIN_VALUE;
        try {
            id = Integer.parseInt(HackerNewsClient.getLatestContentId());
        } catch (IOException ignored) { /*ignored.printStackTrace();*/ }
        return id;
    }

    private void getSentiments() {
        while (true) {
            String text = "";
            Integer count = 0;
            String sentiments = "";
            try {
                var pair = queue.take();
                text = pair.getFirst();
                count = pair.getSecond();
                var strings = text.split("[.!?]");
                ObjectMapper objectMapper = new ObjectMapper();
                sentiments = SentimentsClient.sentiments(text);
                JsonNode root = objectMapper.readTree(sentiments);
                JsonNode textNode = root.get("output");
                if (textNode == null || root.get("err") != null) {
                    if (count < retryCount) {
                        queue.put(new Pair<>(text, count + 1));
                    } else {
                        printout("[" + Thread.currentThread().getName()
                                + "] error JSON\nText: " + text + "\nSent: "
                                + sentiments + "\ndelete this text");
                    }
                } else {
                    if (textNode.size() == strings.length) {
                        for (int i = 0; i < strings.length; i++) {
                            String string = strings[i];
                            for (Map.Entry<String, Stats> s : stats.entrySet()) {
                                if (string.contains(s.getKey())) {
                                    boolean denyFlag = false;
                                    for (String deny : denylist) {
                                        if (string.contains(deny)) {
                                            denyFlag = true;
                                            break;
                                        }
                                    }
                                    if (!denyFlag) {
                                        mention.getAndIncrement();
                                        s.getValue().mentions.incrementAndGet();
                                        String sentinent = textNode.get(i).asText("");
                                        if (sentinent.contains("Verynegative")) {
                                            s.getValue().score.add(-3);
                                        } else if (sentinent.contains("Negative")) {
                                            s.getValue().score.add(-1);
                                        } else if (sentinent.contains("Positive")) {
                                            s.getValue().score.add(1);
                                        } else if (sentinent.contains("Verypositive")) {
                                            s.getValue().score.add(3);
                                        } // else "Neutral"
                                        printout("[" + Thread.currentThread().getName()
                                                + "] stats: " + stats
                                                + ", queue size: " + queue.size()
                                                + ((currentContentId.get() >= maxContentId.get())
                                                    && (queue.size() % 100 == 0)
                                                    ? ", current datetime: "
                                                    + dtf.format(LocalDateTime.now()) : "")
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SocketTimeoutException e) {
                try {
                    if (count < retryCount) {
                        queue.put(new Pair<>(text, count + 1));
                    } else {
                        printout("[" + Thread.currentThread().getName()
                                + "] error Deep-AI: " + e.getMessage()
                                + ", current datetime: " + dtf.format(LocalDateTime.now()));
                    }
                } catch (Exception ignored) { /*ignored.printStackTrace();*/ }
            } catch (JacksonException e) {
                try {
                    if (count < retryCount) {
                        queue.put(new Pair<>(text, count + 1));
                    } else {
                        printout("[" + Thread.currentThread().getName()
                                + "] error JSON convert: " + e.getMessage()
                                + ", current datetime: " + dtf.format(LocalDateTime.now()));
                    }
                } catch (Exception ignored) { /*e.printStackTrace();*/ }
            } catch (Exception e) {
                printout("[" + Thread.currentThread().getName()
                        + "]  error on text: " + text
                        + "\nSentiments: " + sentiments
                        + ", current datetime: " + dtf.format(LocalDateTime.now()));
                e.printStackTrace();
            }
            if (currentContentId.get() >= maxContentId.get() && queue.isEmpty()) {
                printout("[" + Thread.currentThread().getName() + "] close sentinent thread"
                        + ", current datetime: " + dtf.format(LocalDateTime.now()));
                break;
            }

        }
    }

    public synchronized void printout(String string) {
        System.out.println(string);
    }

    public Map<String, Stats> getStats() {
        return stats;
    }

    public static class Stats {
        final AtomicInteger mentions = new AtomicInteger(0);
        final LongAdder score = new LongAdder();

        @Override
        public String toString() {
            return "Stats{"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + String.format("%.2f", score.intValue() / (mentions.get() + 1.))
                    + '}';
        }
    }
}
