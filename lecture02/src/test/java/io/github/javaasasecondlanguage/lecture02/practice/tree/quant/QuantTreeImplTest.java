package io.github.javaasasecondlanguage.lecture02.practice.tree.quant;

import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNode;
import io.github.javaasasecondlanguage.lecture02.practice.tree.TreeNodeImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuantTreeImplTest {

    @Test
    void emptyTreeHasZeroMaxHeight() {
        QuantTree tree = new QuantTreeImpl(null);
        assertEquals(0, tree.getMaxHeight());
    }

    @Test
    void singleElementTreeHasZeroMaxHeight() {
        QuantTree tree = new QuantTreeImpl(new TreeNodeImpl<>(42));
        assertEquals(1, tree.getMaxHeight());
    }

    @Test
    void sample1() {
        QuantTree tree = new QuantTreeImpl(
                node(1,
                        node(10),
                        node(11, node(110)),
                        node(12)
                )
        );

        assertEquals(3, tree.getMaxHeight());
    }

    @Test
    void sample2() {
        QuantTree tree = new QuantTreeImpl(
                node(1,
                        node(10),
                        node(11, node(110)),
                        node(12, genFlatTreeNode(20))
                )
        );

        assertEquals(22, tree.getMaxHeight());
    }

    @Test
    void maxTest500() {
        var tree = new QuantTreeImpl(genFlatTreeNode(500));
        assertEquals(500, tree.getMaxHeight());
    }

    private static TreeNode<Integer> node(Integer e, TreeNode<Integer>... children) {
        return new TreeNodeImpl<>(e, children);
    }

    private static TreeNode<Integer> genFlatTreeNode(int size) {
        if (size == 0) {
            return null;
        }
        return node(size, genFlatTreeNode(size - 1));
    }
}