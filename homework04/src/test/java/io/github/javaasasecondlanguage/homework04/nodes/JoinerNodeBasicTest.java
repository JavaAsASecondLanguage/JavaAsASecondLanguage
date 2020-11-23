package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static java.util.List.of;

class JoinerNodeBasicTest {

    @Test
    void general() {
        var joinerNode = new JoinerNode(of("AuthorId"));
        var actualRecords = pushRecordsIntoJoinerNodeThenGetOutput(joinerNode, leftInputRecords, rightInputRecords);

        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static List<Record> pushRecordsIntoJoinerNodeThenGetOutput(JoinerNode joinerNode,
                                                                       List<Record> leftRecords,
                                                                       List<Record> rightRecords) {
        var listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(joinerNode)
                .map(listDumper);

        for (var record : leftRecords) {
            joinerNode.push(record, 0);
        }
        joinerNode.push(Record.terminalRecord(), 0);

        for (var record : rightRecords) {
            joinerNode.push(record, 1);
        }
        joinerNode.push(Record.terminalRecord(), 1);

        var outputRecords = listDumper.getRecords();
        return outputRecords;
    }

    private static final List<Record> leftInputRecords = convertToRecords(
            new String[]{"DocId", "Text", "AuthorId"},
            new Object[][]{
                    {1, "The Grey Knights have come on behalf of the Holy Inquisition.", 100},
                    {4, "The Heretics will suffer the ultimate punishment!", 100},
                    {3, "The warriors of the Inquisition are yours to command", 100},
                    {2, "The enemies of the Emperor shall be destroyed!", 200},
                    {5, "The fallen shall be forever remembered as the Emperor's finest.", 200}
            }
    );

    private static final List<Record> rightInputRecords = convertToRecords(
            new String[]{"AuthorId", "AuthorName"},
            new Object[][]{
                    {100, "Grey Knights"},
                    {200, "Apothecary"}
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"DocId", "Text", "AuthorId", "AuthorName"},
            new Object[][]{
                    {1, "The Grey Knights have come on behalf of the Holy Inquisition.", 100, "Grey Knights"},
                    {4, "The Heretics will suffer the ultimate punishment!", 100, "Grey Knights"},
                    {3, "The warriors of the Inquisition are yours to command", 100, "Grey Knights"},
                    {2, "The enemies of the Emperor shall be destroyed!", 200, "Apothecary"},
                    {5, "The fallen shall be forever remembered as the Emperor's finest.", 200, "Apothecary"}
            }
    );
}