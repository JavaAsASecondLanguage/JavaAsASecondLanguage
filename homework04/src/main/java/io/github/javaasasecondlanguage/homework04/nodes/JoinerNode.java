package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.List;

public class JoinerNode implements ProcNode {

    private final RoutingCollector collector = new RoutingCollector();
    private final List<Record> leftRecords = new ArrayList<>();
    private final List<Record> rightRecords = new ArrayList<>();
    private final List<String> keyColumns;
    private boolean leftRecordsCollected = false;
    private boolean rightRecordsCollected = false;


    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber != 0 && gateNumber != 1) {
            throw new IllegalArgumentException("Gate does not exist: "+gateNumber);
        } else if (gateNumber == 0) {
            if (inputRecord.isTerminal()) {
                leftRecordsCollected = true;
            } else {
                leftRecords.add(inputRecord);
            }
        } else if (gateNumber == 1) {
            if (inputRecord.isTerminal()) {
                rightRecordsCollected = true;
            } else {
                rightRecords.add(inputRecord);
            }
        }

        if (leftRecordsCollected && rightRecordsCollected) {
            for (var l : leftRecords) {
                var lJoinColumns = l.copyColumns(keyColumns);
                for (var r : rightRecords) {
                    var rJoinColumns = r.copyColumns(keyColumns);
                    if (lJoinColumns.equals(rJoinColumns)) {
                        collector.collect(l.copy().setAll(r.getData()));
                    }
                }
            }
            collector.collect(Record.terminalRecord());
        }

    }
}
