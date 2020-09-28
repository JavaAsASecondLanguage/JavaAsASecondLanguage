package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import io.github.javaasasecondlanguage.lecture02.practice.tree.AbstractTree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

public class QuantTreeImpl extends AbstractTree<Integer> implements QuantTree {
    public QuantTreeImpl(TreeNode<Integer> root) {
        super(root);
    }

    @Override
    public int getMaxHeight() {
        return getHeight(root);
    }

    public static int getHeight(TreeNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        return 1 + node.getChildren().stream()
                .map(QuantTreeImpl::getHeight)
                .max(Integer::compareTo).orElse(0); // why error ???
    }
}
