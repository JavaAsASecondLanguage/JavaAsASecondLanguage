package io.github.javaasasecondlanguage.homework04.nodes;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.RoutingCollector;
import io.github.javaasasecondlanguage.homework04.ops.Mapper;

public class MapperNode  implements ProcNode {

    private final RoutingCollector collector = new RoutingCollector();
    private final Mapper mapper;

    public MapperNode(Mapper mapper) {
        this.mapper = mapper;
    }

    public Mapper getMapper() {
        return mapper;
    }

    @Override
    public RoutingCollector getCollector() { return collector; }

    @Override
    public void push(Record inputRecord, int gateNumber) {
        if (gateNumber != 0) {
            throw new IllegalArgumentException("Gate does not exist: "+gateNumber);
        }

        if (!inputRecord.isTerminal()) {
            mapper.apply(inputRecord, collector);
        } else {
            collector.collect(inputRecord);
        }
    }
}
