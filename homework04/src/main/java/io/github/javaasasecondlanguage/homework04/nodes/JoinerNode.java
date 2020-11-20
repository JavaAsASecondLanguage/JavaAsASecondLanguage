package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.*;

public class JoinerNode implements ProcNode {

    private final Set<String> keyColumns;
    private final RoutingCollector collector = new RoutingCollector();
    private final List<Record> left = new ArrayList<>();
    private final List<Record> right = new ArrayList<>();
    private boolean rightEnd = false;
    private boolean leftEnd = false;

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = new HashSet<>(keyColumns);
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (inputRecord.isTerminal()) {
            if (gateNumber == 0) {
                leftEnd = true;
            } else if (gateNumber == 1) {
                rightEnd = true;
            }
        } else if (!leftEnd && gateNumber == 0) {
            left.add(inputRecord);
        } else if (!rightEnd && gateNumber == 1) {
            right.add(inputRecord);
        } else {
            throw new IllegalArgumentException("Gate does not exist: " + gateNumber);
        }

        if (leftEnd && rightEnd) {
            left.forEach( item -> {
                final Record key = item.copyColumns(keyColumns);
                right.stream()
                    .filter( predicat -> key.equals(predicat.copyColumns(keyColumns)))
                    .forEach( r -> collector.collect(item.copy().setAll(r.getData())));
            });
            collector.collect(Record.terminalRecord());
        }
    }
}
