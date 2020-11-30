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
            new String[]{"Id", "Text"},
            new Object[][]{
                    {1, "I am a man who walks alone"},
                    {2, "And when I'm walking a dark road"},
                    {3, "At night or strolling through the park"},
                    {4, "Fear of the dark, fear of the dark"},
                    {5, "I have a constant fear that something's always near"},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Word",  "TfIdf"},
            new Object[][]{
                    {1, "a", 0.216},
                    {1, "alone", 0.373},
                    {1, "am", 0.373},
                    {1, "i", 0.216},
                    {1, "man", 0.373},
                    {1, "walks", 0.373},
                    {1, "who", 0.373},
                    {2, "a", 0.189},
                    {2, "and", 0.326},
                    {2, "dark", 0.240},
                    {2, "i", 0.189},
                    {2, "m", 0.326},
                    {2, "road", 0.326},
                    {2, "walking", 0.326},
                    {2, "when", 0.326},
                    {3, "at", 0.373},
                    {3, "night", 0.373},
                    {3, "or", 0.373},
                    {3, "park", 0.373},
                    {3, "strolling", 0.373},
                    {3, "the", 0.274},
                    {3, "through", 0.373},
                    {4, "dark", 0.479},
                    {4, "fear", 0.479},
                    {4, "of", 0.652},
                    {4, "the", 0.479},
                    {5, "a", 0.151},
                    {5, "always", 0.261},
                    {5, "constant", 0.261},
                    {5, "fear", 0.192},
                    {5, "have", 0.261},
                    {5, "i", 0.151},
                    {5, "near", 0.261},
                    {5, "s", 0.261},
                    {5, "something", 0.261},
                    {5, "that", 0.261}
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
