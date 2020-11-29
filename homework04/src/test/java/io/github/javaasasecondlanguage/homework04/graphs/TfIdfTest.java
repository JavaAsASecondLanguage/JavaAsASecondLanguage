package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.graphs.TfIdf.*;
import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static org.junit.jupiter.api.Assertions.*;

class TfIdfTest {

    private ProcNode inputNode;
    private ProcNode outputNode;

    @BeforeEach
    void setUp() {
        var graph = TfIdf.createGraph();

        assertNotNull(graph.getInputNodes());
        assertNotNull(graph.getInputNodes());

        assertEquals(1, graph.getInputNodes().size());
        assertEquals(1, graph.getOutputNodes().size());

        inputNode = graph.getInputNodes().get(0);
        outputNode = graph.getOutputNodes().get(0);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{ID, INPUT_TEXT},
            new Object[][]{
                    {1, "A fire burned brightly in the fireplace"},
                    {2, "They were sitting around the fire and trying to keep themselves warm"},
                    {3, "The fire leaped into life the flames encircled me so that in a moment my clothes were blazing"}
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{ID, TOKEN,  TFIDF},
            new Object[][]{
                    {1, "a", 0.201},
                    {1, "brightly", 0.300},
                    {1, "burned", 0.300},
                    {1, "fire", 0.143},
                    {1, "fireplace", 0.300},
                    {1, "in", 0.201},
                    {1, "the", 0.143},
                    {2, "and", 0.175},
                    {2, "around", 0.175},
                    {2, "fire", 0.083},
                    {2, "keep", 0.175},
                    {2, "sitting", 0.175},
                    {2, "the", 0.083},
                    {2, "themselves", 0.175},
                    {2, "they", 0.175},
                    {2, "to", 0.175},
                    {2, "trying", 0.175},
                    {2, "warm", 0.175},
                    {2, "were", 0.117},
                    {3, "a", 0.078},
                    {3, "blazing", 0.117},
                    {3, "clothes", 0.117},
                    {3, "encircled", 0.117},
                    {3, "fire", 0.056},
                    {3, "flames", 0.117},
                    {3, "in", 0.078},
                    {3, "into", 0.117},
                    {3, "leaped", 0.117},
                    {3, "life", 0.117},
                    {3, "me", 0.117},
                    {3, "moment", 0.117},
                    {3, "my", 0.117},
                    {3, "so", 0.117},
                    {3, "that", 0.117},
                    {3, "the", 0.111},
                    {3, "were", 0.078}
            }
    );

    @Test
    void generalTest() {
        var listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(outputNode)
                .map(listDumper);
        for (Record record : inputRecords) {
            inputNode.push(record, 0);
        }
        inputNode.push(Record.terminalRecord(), 0);
        List<Record> actualRecords = listDumper.getRecords();
        assertRecordsEqual(expectedRecords, actualRecords);
    }
}