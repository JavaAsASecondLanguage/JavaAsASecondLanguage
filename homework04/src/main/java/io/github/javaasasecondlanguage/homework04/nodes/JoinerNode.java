package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.List;

public class JoinerNode implements ProcNode {

    private final RoutingCollector collector = new RoutingCollector();
    private final List<String> keyColumns;
    private final List<Record> leftInputRecords = new ArrayList<>();
    private final List<Record> rightInputRecords = new ArrayList<>();
    private Boolean leftInputTerminated = false;
    private Boolean rightInputTerminated = false;

    public JoinerNode(List<String> keyColumns) { this.keyColumns = keyColumns; }

    @Override
    public RoutingCollector getCollector()  { return collector; }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber != 0 && gateNumber != 1) {
            throw new IllegalArgumentException("Gate does not exist: "+gateNumber);
        }

        if (!inputRecord.isTerminal()) {
            if (gateNumber == 0) {
                leftInputRecords.add(inputRecord);
            } else {
                rightInputRecords.add(inputRecord);
            }
            return;
        }

        leftInputTerminated = leftInputTerminated || (gateNumber == 0);
        rightInputTerminated = rightInputTerminated || (gateNumber == 1);

        if (leftInputTerminated && rightInputTerminated) {
            leftInputRecords.stream()
                    .flatMap(left -> rightInputRecords.stream()
                            .filter(right -> left.copyColumns(keyColumns)
                                    .equals(right.copyColumns(keyColumns)))
                            .map(right -> {
                                var record = new Record(left.getData());
                                record.setAll(right.getData());
                                return record;
                            }))
                    .forEach(rec -> collector.collect(rec));

            leftInputRecords.clear();
            rightInputRecords.clear();
            leftInputTerminated = false;
            rightInputTerminated = false;

            collector.collect(Record.terminalRecord());
        }
    }
}
