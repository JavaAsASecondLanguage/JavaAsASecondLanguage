package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import static java.util.List.of;

public class TfIdf {
    public static ProcGraph createGraph() {
        GraphPartBuilder lowerCaseGraph = GraphPartBuilder.init()
                .map(new LowerCaseMapper("Text"))
                .map(new RetainColumnsMapper(of("Id", "Text")));

        GraphPartBuilder tfGraph = lowerCaseGraph
                .branch()
                .map(new TokenizerMapper("Text", "Word"))
                .sortThenReduceBy(of("Id"), new WordFrequencyReducer("Word", "Tf"));

        GraphPartBuilder docsNumGraph = lowerCaseGraph
                .branch()
                .reduceBy(of(), new CountReducer("totalDocs"));

        GraphPartBuilder ifdGraph = lowerCaseGraph
                .branch()
                .map(new TokenizerMapper("Text", "Word"))
                .sortThenReduceBy(of("Id", "Word"), new CountReducer(""))
                .map(new AddColumnMapper("numWords", (record) -> 1))
                .sortThenReduceBy(of("Word"), new CountReducer("numWords"))
                .join(docsNumGraph, of())
                .map(new AddColumnMapper("Idf",
                        (record) -> Math.log(record.getDouble("totalDocs") / record.getDouble("numWords")) + 1));

        GraphPartBuilder tfIdfGraph = tfGraph
                .join(ifdGraph, of("Word"))
                .map(new AddColumnMapper("TfIdf",
                        (record) -> record.getDouble("Tf") * (Double) record.get("Idf")))
                .map(new RetainColumnsMapper(of("Id", "Word", "TfIdf")));

        return new ProcGraph(
                of(tfIdfGraph.getStartNode()),
                of(tfIdfGraph.getEndNode())
        );
    }
}