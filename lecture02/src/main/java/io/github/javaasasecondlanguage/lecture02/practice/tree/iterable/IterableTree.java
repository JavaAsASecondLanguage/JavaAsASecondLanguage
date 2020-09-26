package io.github.javaasasecondlanguage.lecture02.practice.tree.iterable;

import io.github.javaasasecondlanguage.lecture02.practice.tree.Tree;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class IterableTree implements Tree<Integer>, Iterable<Integer> {
    public TreeNode<Integer> root;

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
     */
    @Override
    public Iterator<Integer> iterator() {
        return new NodeIterator(getRoot());
    }

    public static class NodeIterator implements Iterator<Integer> {
        TreeNode<Integer> root;
        public Stack<TreeNode<Integer>> nodeStack;

        NodeIterator(TreeNode<Integer> root) {
            this.root = root;
            nodeStack = new Stack<>();
            // fill values with dfs approach:
            if (root != null) {
                nodeStack.push(root);
            }
        }

        @Override
        public boolean hasNext() {
            return nodeStack.empty();
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TreeNode<Integer> currentNode = nodeStack.pop();
            // dfs approach further:
            List<TreeNode<Integer>> currentNodeChildren = currentNode.getChildren();
            if (currentNodeChildren.size() > 0) {
                Collections.reverse(currentNodeChildren);
                for (TreeNode<Integer> child : currentNodeChildren) {
                    nodeStack.push(child);
                }
            }
            return currentNode.element();
        }
    }

    @Override
    public TreeNode<Integer> getRoot() {
        return root;
    }
}
