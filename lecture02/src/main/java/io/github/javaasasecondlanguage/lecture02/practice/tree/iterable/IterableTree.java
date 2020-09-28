package io.github.javaasasecondlanguage.lecture02.practice.tree.iterable;

import io.github.javaasasecondlanguage.lecture02.practice.tree.Tree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

import java.util.Iterator;
import java.util.Stack;

public class IterableTree
        implements Tree<Integer>, Iterable<Integer> {

    public TreeNode<Integer> root;

    // constructor creates iterable tree
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
        var stack = new Stack<Integer>();
        stack.push(root.element());
        for (var child : root.getChildren()) {
            IterableTree subtree = new IterableTree(child);
            Iterator<Integer> iterSubtree = subtree.iterator();
            while (iterSubtree.hasNext()) {
                stack.push(iterSubtree.next());
            }
        }
        return stack.iterator();
    }

    @Override
    public TreeNode<Integer> getRoot() {
        return root;
    }
}


