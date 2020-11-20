package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.SumReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import static java.util.List.of;

public class TfIdf {

    public static ProcGraph createGraph() {

        GraphPartBuilder inputGraph = GraphPartBuilder
                .init()
                .map(new RetainColumnsMapper(of("Id", "Text")))
                .map(new LowerCaseMapper("Text"));

        GraphPartBuilder totalDocuments = inputGraph
                .branch()
                .reduceBy(of("Id"), new CountReducer("Count"))
                .reduceBy(of("Count"), new SumReducer("Count", "Documents"))
                .map(new RetainColumnsMapper(of("Documents")));

        GraphPartBuilder textGraph = inputGraph
                .branch()
                .map(new TokenizerMapper("Text", "Word"))
                .sortThenReduceBy(of("Id"), new WordFrequencyReducer("Word", "Tf"));

        GraphPartBuilder idf = textGraph
                .branch()
                .sortThenReduceBy(of("Word"), new WordFrequencyReducer("Id", "IdFreq"))
                .join(totalDocuments, of())
                .map(new AddColumnMapper("Idf",
                        r -> Math.log(r.getDouble("Documents") * r.getDouble("IdFreq"))))
                .map(new RetainColumnsMapper(of("Word", "Idf")))
                .reduceBy(of("Word"), new FirstNReducer(1));

        GraphPartBuilder tfIdf = textGraph
                .branch()
                .join(idf, of("Word"))
                .map(new AddColumnMapper("TfIdf",
                        r -> r.getDouble("Tf") * r.getDouble("Idf")))
                .map(new RetainColumnsMapper(of("Id", "Word", "TfIdf")));


        return new ProcGraph(
                of(tfIdf.getStartNode()),
                of(tfIdf.getEndNode())

        );
    }

}
