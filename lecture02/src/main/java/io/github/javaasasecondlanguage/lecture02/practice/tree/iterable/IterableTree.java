package io.github.javaasasecondlanguage.lecture02.practice.tree.iterable;

import io.github.javaasasecondlanguage.lecture02.practice.tree.Tree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

import java.util.Iterator;
import java.util.ArrayList;

public class IterableTree
        implements Tree<Integer>, Iterable<Integer> {

    protected TreeNode<Integer> root;

    public IterableTree(TreeNode<Integer> root) {
        this.root = root;
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
        var list = new ArrayList<Integer>();
        list.add(root.element());
        for (var child : root.getChildren()) {
            var childTree = new IterableTree(child);
            var childIter = childTree.iterator();
            while (childIter.hasNext()) {
                list.add(childIter.next());
            }
        }
        return list.iterator();
    }

    @Override
    public TreeNode<Integer> getRoot() {
        return root;
    }
}


