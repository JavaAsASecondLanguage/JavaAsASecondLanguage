package io.github.javaasasecondlanguage.lecture07.practice1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/* Copied from StackOverflow. Don't know how it works */
class Html2Text extends HTMLEditorKit.ParserCallback {
    StringBuffer strBuffer;

    public Html2Text() {
    }

    public Html2Text parse(Reader in) throws IOException {
        strBuffer = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        delegator.parse(in, this, Boolean.TRUE);
        return this;
    }

    public void handleText(char[] text, int pos) {
        strBuffer.append(text);
    }

    public String getText() {
        return strBuffer.toString();
    }
}

public class Analytics {
    private volatile int maxContentId = 2 * 1000 * 1000;
    private volatile int currentContentId = 2 * 1000 * 1000;
    private final Semaphore contentIdSem = new Semaphore(1);
    private final Semaphore sentimentsSem = new Semaphore(24);
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(128 * 1024);
    private final ConcurrentHashMap<String, Stats> stats = new ConcurrentHashMap<>();

    /**
     * Gather statistics for given terms from Hacker News
     * and fill the Map containing Stats data structure
     * <p>
     * Stats.mentions - number of comments/posts that mention this terms
     * Stats.score - measure for how negative or positive is the context
     * when given term is used in comment/post
     */
    public void analyzeHackerNews(List<String> terms, int timeout) {
        for (String term : terms) {
            stats.put(term.toLowerCase(), new Stats());
        }

        final int idUpdatersNum = 1;
        final int downloadersNum = 64;
        final int analyzersNum = 1024;

        ExecutorService es = Executors.newFixedThreadPool(
                idUpdatersNum + downloadersNum + analyzersNum);
        es.submit(this::updateLatestContentId);
        for (int i = 0; i < downloadersNum; i++) {
            es.submit(this::downloadHackerNews);
        }
        for (int i = 0; i < analyzersNum; i++) {
            es.submit(this::analyzeWithDeepAI);
        }

        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void updateLatestContentId() {
        while (true) {
            try {
                maxContentId = Integer.valueOf(HackerNewsClient.getLatestContentId());
                Thread.sleep(5000);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }

        }
    }

    void downloadHackerNews() {
        try {
            while (true) {
                contentIdSem.acquire();
                if (currentContentId < maxContentId) {
                    int id = currentContentId;
                    String content = null;
                    currentContentId++;
                    contentIdSem.release();

                    try {
                        content = HackerNewsClient.getContent(id);
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode root = objectMapper.readTree(content);
                        JsonNode textNode = root.get("text");
                        if (textNode != null) {
                            String text = textNode.asText();
                            text = new Html2Text().parse(new StringReader(text))
                                    .getText()
                                    .toLowerCase();
                            for (String sentence : text.split("[\\.\\?\\!\\(\\)]")) {
                                if (sentence != null && !sentence.isEmpty()) {
                                    queue.put(sentence);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    contentIdSem.release();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void analyzeWithDeepAI() {
        int sentimentsSemAcquired = 0;
        try {
            while (true) {
                String content = queue.take();
                for (Map.Entry<String, Stats> s : stats.entrySet()) {
                    if (content.contains(s.getKey())
                            && (s.getKey() != "java" || !content.contains("javascript"))) {
                        try {
                            sentimentsSem.acquire();
                            sentimentsSemAcquired = 1;
                            String sentiments = SentimentsClient.sentiments(content);
                            sentimentsSem.release();
                            sentimentsSemAcquired = 0;

                            if (sentiments.contains("Out of free credits")) {
                                System.out.println(
                                        "Out of free credits, queue size = " + queue.size());
                                continue;
                            }
                            s.getValue().mentions.incrementAndGet();
                            if (sentiments.contains("Verynegative")) {
                                s.getValue().score.add(-3);
                            }
                            if (sentiments.contains("Negative")) {
                                s.getValue().score.add(-1);
                            }
                            if (sentiments.contains("Positive")) {
                                s.getValue().score.add(1);
                            }
                            if (sentiments.contains("Verypositive")) {
                                s.getValue().score.add(3);
                            }
                            System.out.println(s.getKey() + ": " + content + " : " + sentiments);
                            System.out.println(stats);
                            System.out.println("queue size = " + queue.size());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Thread.sleep(4 * 60 * 1000);
                        }

                        if (sentimentsSemAcquired != 0) {
                            sentimentsSem.release();
                            sentimentsSemAcquired = 0;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
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
            return "Stats{"
                    + "mentions=" + mentions
                    + ", score=" + score
                    + ", rating=" + score.intValue() / (mentions.get() + 1.)
                    + '}';
        }
    }
}
