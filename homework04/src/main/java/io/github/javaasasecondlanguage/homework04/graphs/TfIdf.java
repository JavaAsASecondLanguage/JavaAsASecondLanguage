package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;

import static java.util.List.of;

public class TfIdf {
    public static final String TEXT = "Text";
    public static final String WORD = "Word";
    public static final String ID = "Id";
    public static final String TF_IDF = "TF/IDF";

    private static final String DOCUMENT_SIZE = "DocumentSize";
    private static final String COUNT = "Count";
    private static final String TF = "TF";
    private static final String DOCUMENT_NUMBER = "DocumentNumber";
    private static final String DOCUMENT_COUNTER = "DocumentCounter";
    private static final String IDF = "IDF";

    public static ProcGraph createGraph() {
        var base = GraphPartBuilder.init();
        var tokenizer = base.branch()
                .map(new LowerCaseMapper(TEXT))
                .map(new TokenizerMapper(TEXT, WORD));
        var documentSizeCounter = tokenizer.branch()
                .sortThenReduceBy(of(ID), new CountReducer(DOCUMENT_SIZE));
        var tfGraph = tokenizer.branch()
                .sortThenReduceBy(of(ID, WORD), new CountReducer(COUNT))
                .join(documentSizeCounter, of(ID))
                .map(new AddColumnMapper(TF,
                        record -> record.getDouble(COUNT)
                                / record.getDouble(DOCUMENT_SIZE)));
        var inputSizeCounter = base.branch()
                .reduceBy(of(), new CountReducer(DOCUMENT_NUMBER));
        var dfGraph = tfGraph.branch()
                .sortThenReduceBy(of(WORD), new CountReducer(DOCUMENT_COUNTER))
                .join(inputSizeCounter, of())
                .map(new AddColumnMapper(IDF,
                        record -> record.getDouble(DOCUMENT_NUMBER)
                                / record.getDouble(DOCUMENT_COUNTER)));
        var tfIdfGraph = tfGraph.branch()
                .join(dfGraph, of(WORD))
                .map(new AddColumnMapper(TF_IDF,
                        record -> record.getDouble(TF)
                                * record.getDouble(IDF)))
                .map(new RetainColumnsMapper(of(ID, WORD, TF_IDF)));
        return new ProcGraph(
                of(tfIdfGraph.getStartNode()),
                of(tfIdfGraph.getEndNode())
        );
    }
}
