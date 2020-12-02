package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.graphs.TfIdf.ID_COL;
import static io.github.javaasasecondlanguage.homework04.graphs.TfIdf.TEXT_COL;
import static io.github.javaasasecondlanguage.homework04.graphs.TfIdf.TFIDF_COL;
import static io.github.javaasasecondlanguage.homework04.graphs.TfIdf.TOKEN_COL;
import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TfIdfTest {

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
      new String[]{ID_COL, TEXT_COL},
      new Object[][]{
          {1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus quam."},
          {2, "a b c c"},
          {3, "a b c c a"},
      }
  );

  private static final List<Record> expectedRecords = convertToRecords(
      new String[]{ID_COL, TOKEN_COL, TFIDF_COL},
      new Object[][]{
          {1, "adipiscing", 0.210},
          {1, "amet", 0.210},
          {1, "consectetur", 0.210},
          {1, "dolor", 0.210},
          {1, "elit", 0.210},
          {1, "ipsum", 0.210},
          {1, "lorem", 0.210},
          {1, "quam", 0.210},
          {1, "sit", 0.210},
          {1, "vivamus", 0.210},
          {2, "a", 0.351},
          {2, "b", 0.351},
          {2, "c", 0.703},
          {3, "a", 0.562},
          {3, "b", 0.281},
          {3, "c", 0.562},
      }
  );
}
