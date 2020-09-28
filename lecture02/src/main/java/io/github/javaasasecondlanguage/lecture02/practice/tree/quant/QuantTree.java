package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import io.github.javaasasecondlanguage.lecture02.practice.tree.Tree;

public interface QuantTree extends Tree<Integer> {
    /**
     * https://en.wikipedia.org/wiki/Tree_(data_structure)
     *
     * @return max height of a tree
     */
    int getMaxHeight();
}
