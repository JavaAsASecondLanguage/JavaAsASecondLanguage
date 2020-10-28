package io.github.javaasasecondlanguage.lecture07.practice0;

import org.junit.jupiter.api.Test;

public class FixIt2 {
    boolean first = false;
    boolean second = false;

    void setValues() {
        first = true;
        second = true;
    }

    void checkValues() {
        while (!second) {} ;
        assert first;
    }

    /**
     * It can possibly fail
     */
    @Test
    public void test() throws InterruptedException {
        FixIt2 obj = new FixIt2();
        Thread thread1 = new Thread(() -> obj.setValues());
        Thread thread2 = new Thread(() -> obj.checkValues());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}