package io.github.javaasasecondlanguage.homework04;

import io.github.javaasasecondlanguage.homework04.graphs.TfIdf;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.javaasasecondlanguage.homework04.ui.GraphVisualizer.visualizeGraph;

/**
 * Only launched manually - remove "Disabled" first
 */
public class VisualizationDemo {

    @Disabled
    @Test
    void launch() throws InterruptedException {
        var graph = TfIdf.createGraph();
        visualizeGraph(graph);
        Thread.sleep(Long.MAX_VALUE);
    }
}
