package io.github.javaasasecondlanguage.lecture02.practice.tree;

public abstract class AbstractTree<E> implements Tree<E> {
    protected TreeNode<E> root;

    public AbstractTree(TreeNode<E> root) {
        this.root = root;
    }

    @Override
    public TreeNode<E> getRoot() {
        return root;
    }
}
