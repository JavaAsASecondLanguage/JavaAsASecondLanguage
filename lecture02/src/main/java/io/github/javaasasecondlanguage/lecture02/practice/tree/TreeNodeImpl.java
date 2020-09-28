package io.github.javaasasecondlanguage.lecture02.practice.tree;

import java.util.Arrays;
import java.util.List;

public class TreeNodeImpl<E> implements TreeNode<E> {
    private E element;
    private List<TreeNode<E>> children;

    public TreeNodeImpl(E element, TreeNode<E> ... children) {
        this.element = element;
        this.children = Arrays.asList(children);
    }

    @Override
    public E element() {
        return element;
    }

    @Override
    public void addChild(TreeNode<E> e) {
        children.add(e);
    }

    @Override
    public List<TreeNode<E>> getChildren() {
        return children;
    }

}
