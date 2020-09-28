package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import io.github.javaasasecondlanguage.lecture02.practice.tree.AbstractTree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

public class QuantTreeImpl extends AbstractTree<Integer> implements QuantTree {
    public QuantTreeImpl(TreeNode<Integer> root) {
        super(root);
    }

    @Override
    public int getMaxHeight() {
        throw new RuntimeException("Not Implemented");
    }
}
