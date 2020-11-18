package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;
import io.github.javaasasecondlanguage.homework04.ops.Reducer;

import java.util.ArrayList;
import java.util.List;

public class JoinerNode implements ProcNode {
    private List<String> keyColumns;
    private final RoutingCollector collector = new RoutingCollector();
    private List<Record> leftRecords = new ArrayList<>();

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public RoutingCollector getCollector() {
        return this.collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber == 0) {
            if (!inputRecord.isTerminal()) {
                leftRecords.add(inputRecord);
            }
        } else {
            if (inputRecord.isTerminal()) {
                for (var record : this.leftRecords) {
                    this.collector.collect(record);
                }
            } else {
                for (var record : this.leftRecords) {
                    if (isMatched(record, inputRecord)) {
                        for (var column : inputRecord.getData().keySet()) {
                            record.set(column, inputRecord.get(column));
                        }
                    }
                }
            }
        }
    }

//    private List<Record> removeFromLeftRecords(Record rightRecord) {
//        var res = new ArrayList<Record>();
//        var newLeftRecords = new ArrayList<Record>();
//
//        for (var leftRecord : this.leftRecords) {
//            if (isMatched(leftRecord, rightRecord)) {
//                res.add(leftRecord);
//            } else {
//                newLeftRecords.add(leftRecord);
//            }
//        }
//
//        this.leftRecords = newLeftRecords;
//        return res;
//    }

    private boolean isMatched(Record record1, Record record2) {
        for (var column : this.keyColumns) {
            if (!record1.get(column).equals(record2.get(column))) {
                return false;
            }
        }

        return true;
    }
}
