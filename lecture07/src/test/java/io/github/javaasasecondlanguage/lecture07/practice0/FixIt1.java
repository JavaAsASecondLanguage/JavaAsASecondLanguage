package io.github.javaasasecondlanguage.lecture07.practice0;

import org.junit.jupiter.api.Test;

public class FixIt1 {
    boolean isT2Running = true;

    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int counter = 0;
            while (isT2Running) {
                counter++;
            }
            System.out.println("Thread 1 finished. Counted up to " + counter);
        });
        t1.start();

        Thread.sleep(100); // Sleep for a bit so that thread 1 has a chance to start

        Thread t2 = new Thread(() -> {
            isT2Running = false;
            System.out.println("Thread 2 finished");
        });
        t2.start();
        t1.join();
        t2.join();
    }
}