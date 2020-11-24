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
        System.out.println(actualRecords);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Id", "Author", "Text"},
            new Object[][]{
                    {1, "Garrus", "a b c a"},
                    {2, "Illusive", "a f"},
                    {3, "Tali", "c c a"},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Token", "TF/IDF"},
            new Object[][]{
                    {1, "a", 0.000},
                    {1, "b", 1./4 * Math.log(3./1)},
                    {1, "c", 1./4 * Math.log(3./2)},
                    {2, "a", 0.000},
                    {2, "f", 0.5 * Math.log(3./1)},
                    {3, "a", 0.000},
                    {3, "c", 2./3 * Math.log(3./2)}
            }
    );
}
