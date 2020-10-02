package io.github.javaasasecondlanguage.lecture03.practice1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;

/**
 * Classic LIFO stack
 *
 * @param <E> the type of elements in stack
 */
public class Stack<E> {

    private ArrayList<E> stack = new ArrayList<>();

    public void push(E e) {
        stack.add(e);
    }

    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }

    public void pushAll(Iterable<? extends E> src) {
        for (E elem : src) {
            push(elem);
        }
    }
}
