package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.DistinctReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import static java.util.List.of;

public class TfIdf {
    public static ProcGraph createGraph() {
        var input = GraphPartBuilder.init();

        var docsNumber = input.branch()
                .reduceBy(of(), new CountReducer("TotalDocs"));

        var tokens = input.branch()
                .map(new LowerCaseMapper("Text"))
                .map(new TokenizerMapper("Text", "Token"));

        var tf = tokens.branch()
                .sortThenReduceBy(of("Id"), new WordFrequencyReducer("Token", "TF"));



        var idf = tokens.branch()
                .sortThenReduceBy(of("Token"), new DistinctReducer("Id"))
                .sortThenReduceBy(of("Token"), new CountReducer("DF"))
                .join(docsNumber, of())
                .map(new AddColumnMapper("IDF",
                        record -> Math.log((record.getDouble("TotalDocs")) /
                                (record.getDouble("DF")))));



        var tfIdf = tf.branch()
                .join(idf, of("Token"))
                .map(new AddColumnMapper("TF/IDF",
                        record -> record.getDouble("TF") * record.getDouble("IDF")))
                .map(new RetainColumnsMapper(of("Id", "Token", "TF/IDF")));

        return new ProcGraph(
                of(tfIdf.getStartNode()),
                of(tfIdf.getEndNode())
        );
    }
}