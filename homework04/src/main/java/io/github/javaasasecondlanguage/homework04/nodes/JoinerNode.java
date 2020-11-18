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
    private List<Record> rightRecords = new ArrayList<>();
    private boolean leftTerminated = false;
    private boolean rightTerminated = false;

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
            if (inputRecord.isTerminal()) {
                this.leftTerminated = true;
            } else {
                this.leftRecords.add(inputRecord.copy());
            }
        } else {
            if (inputRecord.isTerminal()) {
                this.rightTerminated = true;
            } else {
                this.rightRecords.add(inputRecord);
            }
        }

        if (this.leftTerminated && this.rightTerminated) {
            joinAndCollect();
        }
    }

    private void joinAndCollect() {
        for (var leftRecord : this.leftRecords) {
            for (var rightRecord : this.rightRecords) {

                if (isMatched(leftRecord, rightRecord)) {
                    for (var column : rightRecord.getData().keySet()) {
                        leftRecord.set(column, rightRecord.get(column));
                    }
                }
            }

            this.collector.collect(leftRecord);
        }

        this.collector.collect(Record.terminalRecord());

        this.leftTerminated = false;
        this.rightTerminated = false;
        this.leftRecords.clear();
        this.rightRecords.clear();
    }

    private boolean isMatched(Record record1, Record record2) {
        for (var column : this.keyColumns) {
            if (!record1.get(column).equals(record2.get(column))) {
                return false;
            }
        }

        return true;
    }
}
