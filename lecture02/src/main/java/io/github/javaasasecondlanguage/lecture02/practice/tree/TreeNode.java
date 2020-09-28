package io.github.javaasasecondlanguage.lecture02.practice.tree;

import java.util.List;

public interface TreeNode<E> {
    E element();

    void addChild(TreeNode<E> e);

    List<TreeNode<E>> getChildren();
}
