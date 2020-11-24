package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JoinerNode implements ProcNode {

    private final Set<String> keyColumns;
    private final RoutingCollector collector;
    private final List<Record> left;
    private final List<Record> right;
    private final boolean[] ends;


    public JoinerNode(List<String> keyColumns) {
        this.keyColumns = new HashSet<>(keyColumns);
        collector = new RoutingCollector();
        left = new ArrayList<>();
        right = new ArrayList<>();
        ends = new boolean[2];
    }

    @Override
    public RoutingCollector getCollector() {
        return collector;
    }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber != 0 && gateNumber != 1)
            throw new IllegalArgumentException("Only gates 0 and 1");
        if (inputRecord.isTerminal()) {
            ends[gateNumber] = true;
        } else if (!ends[0] && gateNumber == 0) {
            left.add(inputRecord);
        } else if (!ends[1] && gateNumber == 1) {
            right.add(inputRecord);
        }

        if (ends[0] && ends[1]) {
            for(Record firstRecord : left) {
                for (Record secondRecord : right) {
                    if (firstRecord.copyColumns(keyColumns).equals(secondRecord.copyColumns(keyColumns)))
                        getCollector().collect(new Record(firstRecord.getData()).setAll(secondRecord.getData()));
                }
            }
            collector.collect(Record.terminalRecord());
            left.clear();
            right.clear();
            ends[0] = false;
            ends[1] = false;
        }
    }
}
