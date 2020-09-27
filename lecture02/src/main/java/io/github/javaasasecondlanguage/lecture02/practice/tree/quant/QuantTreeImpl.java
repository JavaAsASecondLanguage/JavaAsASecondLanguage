package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import java.util.*;

import io.github.javaasasecondlanguage.lecture02.practice.tree.AbstractTree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

public class QuantTreeImpl extends AbstractTree<Integer> implements QuantTree {
    public QuantTreeImpl(TreeNode<Integer> root) {
        super(root);
    }

    @Override
    public int getMaxHeight() {
        if (super.getRoot() == null) {
            return 0;
        }
        if (super.getRoot().getChildren().isEmpty()) {
            return 1;
        }
        return super.getRoot().getChildren().stream()
                .mapToInt(node -> new QuantTreeImpl(node).getMaxHeight())
                .max().getAsInt() + 1;
    }
}
