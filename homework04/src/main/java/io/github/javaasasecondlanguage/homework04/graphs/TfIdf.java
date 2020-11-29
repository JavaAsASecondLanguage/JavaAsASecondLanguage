package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import java.util.List;

import static java.util.List.of;

public class TfIdf {

    public final static String ID = "Id";
    public final static String INPUT_TEXT = "Text";
    public final static List<String> RETAINED_KEYS = of(ID, INPUT_TEXT);
    public final static String TOKEN = "Word";
    public final static String TF = "Tf";
    public final static String IDF = "Idf";
    public final static String TFIDF = "TfIdf";
    public final static String COUNT_DOCS = "totalDocs";
    public final static String COUNT_WORD_IN_DOCS = "countWords";

    public static ProcGraph createGraph() {

        GraphPartBuilder lowerCaseGraph = GraphPartBuilder.init()
                .map(new LowerCaseMapper(INPUT_TEXT))
                .map(new RetainColumnsMapper(RETAINED_KEYS));

        GraphPartBuilder tfCalc = lowerCaseGraph
                .branch()
                .map(new TokenizerMapper(INPUT_TEXT, TOKEN))
                .sortThenReduceBy(of(ID), new WordFrequencyReducer(TOKEN, TF));

        GraphPartBuilder NumOfDocuments = lowerCaseGraph
                .branch()
                .reduceBy(of(), new CountReducer(COUNT_DOCS));

        GraphPartBuilder idfCalc = lowerCaseGraph
                .branch()
                .map(new TokenizerMapper(INPUT_TEXT, TOKEN))
                .sortThenReduceBy(of(ID, TOKEN), new CountReducer(""))
                .map(new AddColumnMapper(COUNT_WORD_IN_DOCS, (record) -> 1))
                .sortThenReduceBy(of(TOKEN), new CountReducer(COUNT_WORD_IN_DOCS))
                .join(NumOfDocuments, of())
                .map(new AddColumnMapper(IDF,
                        (record) -> Math.log(record.getDouble(COUNT_DOCS) / record.getDouble(COUNT_WORD_IN_DOCS)) + 1));

        GraphPartBuilder tfIdfGraph = tfCalc
                .join(idfCalc, of(TOKEN))
                .map(new AddColumnMapper(TFIDF,
                        (record) -> record.getDouble(TF) * (Double) record.get(IDF)))
                .map(new RetainColumnsMapper(of(ID, TOKEN, TFIDF)));

        return new ProcGraph(
                of(tfIdfGraph.getStartNode()),
                of(tfIdfGraph.getEndNode())
        );
    }
}
