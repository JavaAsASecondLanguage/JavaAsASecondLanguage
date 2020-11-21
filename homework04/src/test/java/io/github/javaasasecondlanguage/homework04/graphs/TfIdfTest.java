package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void general() {
        var listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(outputNode)
                .map(listDumper);

        for (var record : inputRecords) {
            inputNode.push(record, 0);
        }
        inputNode.push(Record.terminalRecord(), 0);

        List<Record> actualRecords = listDumper.getRecords();

        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{TfIdf.ID, "Author", TfIdf.TEXT},
            new Object[][]{
                    {1, "Garrus", "one two three"},
                    {2, "Illusive", "two three four"},
                    {3, "Tali", "three four five"}
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{TfIdf.ID, TfIdf.WORD, TfIdf.TF_IDF},
            new Object[][]{
                    {1, "one", 1},
                    {1, "three", 0.333},
                    {1, "two", 0.5},
                    {2, "four", 0.5},
                    {2, "three", 0.333},
                    {2, "two", 0.5},
                    {3, "five", 1},
                    {3, "four", 0.5},
                    {3, "three", 0.333},
            }
    );
}