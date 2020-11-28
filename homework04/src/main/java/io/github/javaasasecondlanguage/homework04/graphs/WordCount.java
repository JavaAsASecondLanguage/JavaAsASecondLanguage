package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;

import static java.util.List.of;

public class WordCount {

    public static ProcGraph createGraph() {
        var inputGraph = GraphPartBuilder
                .init()
                .map(new RetainColumnsMapper(of("Author", "Text")))
                .map(new LowerCaseMapper("Text"))
                .map(new TokenizerMapper("Text", "Word"))
                .sortThenReduceBy(of("Author"), new CountReducer("TotalWords"));

        return new ProcGraph(
                of(inputGraph.getStartNode()),
                of(inputGraph.getEndNode())
        );
    }
}
