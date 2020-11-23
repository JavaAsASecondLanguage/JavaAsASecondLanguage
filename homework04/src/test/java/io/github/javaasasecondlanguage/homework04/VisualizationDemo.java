package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.graphs.Tfidf;
import io.github.javaasasecondlanguage.homework04.graphs.WordCount;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.javaasasecondlanguage.homework04.ui.GraphVisualizer.visualizeGraph;

/**
 * Only launched manually - remove "Disabled" first
 */
public class VisualizationDemo {

    @Test
    void launch() throws InterruptedException {
        var graph = Tfidf.createGraph();
        visualizeGraph(graph);
        //Thread.sleep(Long.MAX_VALUE);
        Thread.sleep(10000);
    }
}
