package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.List;

public class JoinerNode implements ProcNode {

    private final RoutingCollector collector;
    private final List<String> keyColumns;
    private final List<Record> firstGateRecords;
    private boolean firstGateClosed = false;
    private final List<Record> secondGateRecords;
    private boolean secondGateClosed = false;

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
        collector = new RoutingCollector();
        firstGateRecords = new ArrayList<>();
        secondGateRecords = new ArrayList<>();
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber < 0 || gateNumber > 1) {
            throw new IllegalArgumentException("Gate does not exist: "+gateNumber);
        }
        if (inputRecord.isTerminal()) {
            firstGateClosed = firstGateClosed || gateNumber == 0;
            secondGateClosed = secondGateClosed || gateNumber == 1;
        } else {
            if (gateNumber == 0) {
                firstGateRecords.add(inputRecord);
            }
            if (gateNumber == 1) {
                secondGateRecords.add(inputRecord);
            }
        }
        if (firstGateClosed && secondGateClosed) {
            for(Record firstRecord : firstGateRecords) {
                for (Record secondRecord : secondGateRecords) {
                    if (firstRecord.copyColumns(keyColumns)
                            .equals(secondRecord.copyColumns(keyColumns))) {
                        Record joinedRecord = new Record(firstRecord.getData());
                        joinedRecord.setAll(secondRecord.getData());
                        getCollector().collect(joinedRecord);
                    }
                }
            }
            collector.collect(Record.terminalRecord());
            firstGateRecords.clear();
            firstGateClosed = false;
            secondGateRecords.clear();
            secondGateClosed = false;
        }
    }
}
