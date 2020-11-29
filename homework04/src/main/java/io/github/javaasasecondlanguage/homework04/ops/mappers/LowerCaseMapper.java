package io.github.javaasasecondlanguage.homework04.ops.mappers;

import io.github.javaasasecondlanguage.homework04.Collector;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;
import org.bouncycastle.util.Strings;
import org.jfree.util.StringUtils;

/**
 * Shifts selected column to lowercase.
 */
public class LowerCaseMapper implements Mapper {

    private String column;

    public LowerCaseMapper(String column) {
       this.column = column;
    }

    @Override
    public void apply(Record inputRecord, Collector collector) {
        collector.collect(inputRecord.set(column, Strings.toLowerCase(inputRecord.getString(column))));
    }
}
