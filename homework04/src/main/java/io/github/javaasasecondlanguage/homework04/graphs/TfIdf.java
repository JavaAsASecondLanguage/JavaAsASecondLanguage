package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.ProcGraph;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.AddColumnMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.ops.mappers.TokenizerMapper;
import io.github.javaasasecondlanguage.homework04.ops.reducers.CountReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.FirstNReducer;
import io.github.javaasasecondlanguage.homework04.ops.reducers.WordFrequencyReducer;

import java.util.List;

import static java.util.List.of;

public class TfIdf {
  // VisibleForTesting
  final static String ID_COL = "id";
  final static String TEXT_COL = "text";
  final static String TFIDF_COL = "tfidf";
  final static String TOKEN_COL = "token";
  private final static String TF_COL = "tf";
  private final static String IDF_COL = "idf";
  private final static String WORD_COUNT_DOC_COL = "word_count";
  private final static String DOC_COUNT_COL = "doc_count";
  private final static List<String> NULL_COLS = of();

  public static ProcGraph createGraph() {
    var prepareDataset = GraphPartBuilder.init()
        .map(new RetainColumnsMapper(of(ID_COL, TEXT_COL)))
        .map(new LowerCaseMapper(TEXT_COL));

    var tokenizer = prepareDataset.branch()
        .map(new TokenizerMapper(TEXT_COL, TOKEN_COL));

    var tf = tokenizer.branch()
        .reduceBy(of(ID_COL), new WordFrequencyReducer(TOKEN_COL, TF_COL));

    var wordCountDoc = tokenizer.branch()
        .sortThenReduceBy(of(ID_COL, TOKEN_COL), new FirstNReducer(1))
        .sortThenReduceBy(of(TOKEN_COL), new CountReducer(WORD_COUNT_DOC_COL));
    var docCount = prepareDataset.branch()
        .reduceBy(NULL_COLS, new CountReducer(DOC_COUNT_COL));
    var idf = wordCountDoc
        .join(docCount, NULL_COLS)
        .map(new AddColumnMapper(IDF_COL, TfIdf::idf));

    var tfIdf = tf.join(idf, of(TOKEN_COL))
        .map(new AddColumnMapper(TFIDF_COL, TfIdf::tfidf))
        .map(new RetainColumnsMapper(of(ID_COL, TOKEN_COL, TFIDF_COL)));

    return new ProcGraph(
        of(tfIdf.getStartNode()),
        of(tfIdf.getEndNode())
    );
  }

  private static double idf(Record rec) {
    return Math.log(rec.getDouble(DOC_COUNT_COL) / rec.getDouble(WORD_COUNT_DOC_COL)) + 1;
  }

  private static double tfidf(Record rec) {
    return rec.getDouble(TF_COL) * rec.getDouble(IDF_COL);
  }

}
