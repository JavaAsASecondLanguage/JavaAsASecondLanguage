package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinerNode implements ProcNode {
    private static final int LEFT_GATE = 0;
    private static final int RIGHT_GATE = 1;
    private static final int GATES = 2;

    private final List<String> keyColumns;
    private final RoutingCollector collector = new RoutingCollector();
    private final Map<Integer, List<Record>> accumulator = Map.of(
        LEFT_GATE, new ArrayList<>(), RIGHT_GATE, new ArrayList<>()
    );
    private int terminatedNode = 0;

    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber != LEFT_GATE && gateNumber != RIGHT_GATE) {
            throw new IllegalArgumentException(String.format("Gate %d is unknown", gateNumber));
        }
        if (inputRecord.isTerminal()) {
            terminatedNode++;
        } else {
            accumulator.get(gateNumber).add(inputRecord);
        }
        if (terminatedNode == GATES) {
            for (var leftValues: accumulator.get(LEFT_GATE)) {
                var keyValues = leftValues.getAll(keyColumns);
                accumulator.get(RIGHT_GATE)
                    .stream()
                    .filter(rightValues -> rightValues.getAll(keyColumns).equals(keyValues))
                    .map(rightValues -> leftValues.copy().setAll(rightValues.copy().getData()))
                    .forEach(collector::collect);
            }
            collector.collect(Record.terminalRecord());
        }
    }
}
