package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.*;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import java.util.List;

import static java.util.List.of;

public class Tfidf {

    public static ProcGraph createGraph() {
        var inputGraph = GraphPartBuilder
                .init();

        var countBranch = inputGraph
                .branch()
                .sortThenReduceBy(List.of(), new CountReducer("DocsTotal"));

        var tokenizeBranch = inputGraph
                .join(countBranch, List.of())
                .map(new LowerCaseMapper("Text"))
                .map(new TokenizerMapper("Text", "Word"))
                .map(new RetainColumnsMapper(List.of("Id", "DocsTotal", "Word")));

        var tfBranch = tokenizeBranch
                .branch()
                .reduceBy(List.of("Id", "DocsTotal"), new WordFrequencyReducer("Word", "Tf"))
                .map(new RetainColumnsMapper(List.of("Id", "Word", "Tf")));

        var idfBranch = tokenizeBranch
                .branch()
                .sortThenReduceBy(List.of("Id", "DocsTotal", "Word"), new FirstNReducer(1))
                .sortThenReduceBy(List.of("DocsTotal", "Word"), new CountReducer("DocsCount"))
                .map(new AddColumnMapper("Idf",
                        rec -> Math.log(rec.getDouble("DocsTotal") /
                                rec.getDouble("DocsCount"))))
                .map(new RetainColumnsMapper(List.of("Word", "Idf")));

        var tfidfBranch = tfBranch
                .join(idfBranch, List.of("Word"))
                .map(new AddColumnMapper("TfIdf",
                        rec -> rec.getDouble("Tf") * rec.getDouble("Idf")))
                .map(new RetainColumnsMapper(List.of("Id", "Word", "TfIdf")));

        return new ProcGraph(
                of(inputGraph.getStartNode()),
                of(tfidfBranch.getEndNode())
        );
    }
}
