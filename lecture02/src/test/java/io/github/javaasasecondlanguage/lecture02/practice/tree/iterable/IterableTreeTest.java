package io.github.javaasasecondlanguage.lecture02.practice.tree.iterable;

import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNodeImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.StreamSupport;

class IterableTreeTest {
    IterableTree tree = new IterableTree(
            node(1,
                    node(20,
                            node(201),
                            node(202),
                            node(203)
                    ),
                    node(30,
                            node(301),
                            node(302)
                    ),
                    node(40)
            )
    );

    @Test
    void forEachInLoop() {
        // any of:
        //   1 -> 20 -> 20x -> 20y -> 20z -> 30 -> 30x -> 30y -> 40
        //   1 -> 40 -> 30 -> 30x -> 30y -> 20 -> 20x -> 20y -> 20z


        for (var node : tree) {
            System.out.println(node);
        }
    }

    @Test
    void forEachMethod() {
        // any of:
        //   1 -> 20 -> 20x -> 20y -> 20z -> 30 -> 30x -> 30y -> 40
        //   1 -> 40 -> 30 -> 30x -> 30y -> 20 -> 20x -> 20y -> 20z

        tree.forEach(System.out::println);
    }

    @Test
    void dataStream() {
        // any of:
        //   1 -> 20 -> 20x -> 20y -> 20z -> 30 -> 30x -> 30y -> 40
        //   1 -> 40 -> 30 -> 30x -> 30y -> 20 -> 20x -> 20y -> 20z

        StreamSupport.stream(tree.spliterator(), false).forEach(
                System.out::println
        );
    }

    private static TreeNode<Integer> node(Integer e, TreeNode<Integer>... children) {
        return new TreeNodeImpl<>(e, children);
    }
}