package io.github.javaasasecondlanguage.lecture02.practice.tree.iterable;

import io.github.javaasasecondlanguage.lecture02.practice.tree.Tree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

import java.util.Iterator;

public class IterableTree
        implements Tree<Integer>, Iterable<Integer> {

    public IterableTree(TreeNode<Integer> root) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Pre-order iterator
     * https://en.wikipedia.org/wiki/Tree_traversal#Pre-order_(NLR)
     * <p>
     * iterate(node), where node = [data, children] ->
     * 1. data
     * 2. for child in children:
     * iterate(child)
     *
     * @return
     */
    @Override
    public Iterator<Integer> iterator() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public TreeNode<Integer> getRoot() {
        throw new RuntimeException("Not implemented");
    }
}


