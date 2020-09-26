package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import io.github.javaasasecondlanguage.lecture02.practice.tree.AbstractTree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuantTreeImpl extends AbstractTree<Integer> implements QuantTree {

    public QuantTreeImpl(TreeNode<Integer> root) {
        super(root);
    }

    public int getNodeHeight(TreeNode<Integer> node) {
        // corner case
        if (node == null) {
            return 0;
        }
        // leaf node
        List<TreeNode<Integer>> children = node.getChildren();
        if (children.size() == 0) {
            return 1;
        }
        List<Integer> childHeights = new ArrayList<>();
        for (TreeNode<Integer> child : children) {
            childHeights.add(getNodeHeight(child));
        }
        return 1 + Collections.max(childHeights);
    }

    @Override
    public int getMaxHeight() {
        return getNodeHeight(root);
    }
}
