package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.LinkedList;
import java.util.List;

public class JoinerNode implements ProcNode {
    private final RoutingCollector collector = new RoutingCollector();
    private final LinkedList<Record> leftRecords = new LinkedList<>();
    private final LinkedList<Record> rightRecords = new LinkedList<>();
    private final List<String> keyColumns;
    private boolean leftRecordsCollected = false;
    private boolean rightRecordsCollected = false;

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
                this.leftRecordsCollected = true;
            } else {
                this.leftRecords.add(inputRecord);
            }
        } else if (gateNumber == 1) {
            if (inputRecord.isTerminal()) {
                this.rightRecordsCollected = true;
            } else {
                this.rightRecords.add(inputRecord);
            }
        } else {
            throw new IllegalArgumentException();
        }

        if (leftRecordsCollected && rightRecordsCollected) {
            for (Record l : leftRecords) {
                Record lJoinColumns = l.copyColumns(keyColumns);
                for (Record r : rightRecords) {
                    Record rJoinColumns = r.copyColumns(keyColumns);
                    if (lJoinColumns.equals(rJoinColumns)) {
                        this.collector.collect(l.copy().setAll(r.getData()));
                    }
                }
            }
            this.collector.collect(Record.terminalRecord());
        }
    }
}
