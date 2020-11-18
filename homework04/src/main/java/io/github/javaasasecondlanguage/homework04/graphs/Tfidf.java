package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import static io.github.javaasasecondlanguage.homework04.nodes.SortOrder.DESCENDING;
import static java.util.List.of;

public class Tfidf {
    public static ProcGraph createGraph() {
        var inputGraph = GraphPartBuilder
                .init()
                .map(new RetainColumnsMapper(of("Id", "Text")));

        var docCountGraph = GraphPartBuilder.startFrom(inputGraph.getEndNode())
                .reduceBy(of(), new CountReducer("docsCount"));

        var tfGraph = inputGraph.map(new LowerCaseMapper("Text"))
                .map(new TokenizerMapper("Text", "Word"));

        var idfGraph = GraphPartBuilder.startFrom(tfGraph.getEndNode())
                .sortThenReduceBy(of("Id", "Word"), new FirstNReducer(1))
                .sortThenReduceBy(of("Word"), new CountReducer("idfWordsCount"))
                .join(docCountGraph, of())
                .map((inputRecord, collector) -> {
                    var newRecord = inputRecord.copy();
                    var docsCount = newRecord.getDouble("docsCount");
                    var idfWordsCount = newRecord.getDouble("idfWordsCount");
                    var idf = Math.log(docsCount / idfWordsCount);
                    newRecord.set("IDF", idf);
                    collector.collect(newRecord);
                });

        tfGraph = tfGraph.sortThenReduceBy(
                of("Id"),
                new WordFrequencyReducer("Word", "TF")
        );

        var tfidfGraph = GraphPartBuilder.startFrom(tfGraph.getEndNode())
            .join(idfGraph, of("Word"))
            .map((inputRecord, collector) -> {
                var newRecord = inputRecord.copy();
                var tf = newRecord.getDouble("TF");
                var idf = newRecord.getDouble("IDF");
                newRecord.set("TFIDF", tf * idf);
                collector.collect(newRecord);
            }).map(new RetainColumnsMapper(of("Id", "Word", "TFIDF")))
            .sortBy(of("Id", "TFIDF"), DESCENDING);

        return new ProcGraph(
                of(inputGraph.getStartNode()),
                of(tfidfGraph.getEndNode())
        );
    }
}
