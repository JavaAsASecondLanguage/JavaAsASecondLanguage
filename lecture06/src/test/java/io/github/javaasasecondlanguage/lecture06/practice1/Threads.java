package io.github.javaasasecondlanguage.lecture06.practice1;

import org.junit.jupiter.api.Test;

public class Threads {
    @Test
    void start() throws InterruptedException {
        var t = new Thread(() ->
        {
            System.out.println("Hello from [" + Thread.currentThread().getName() + "]");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        System.out.println("Main thread [" + Thread.currentThread().getName() + "] before join");
        t.join();
        System.out.println("Main thread [" + Thread.currentThread().getName() + "] after join");
    }

    @Test()
    void stop() throws InterruptedException {
        var t = new Thread(() ->
        {
            System.out.println("Hello from [" + Thread.currentThread().getName() + "]");
            someValuableJob();
            anotherValuableJob();
        });
        t.start();

        //TERMINATE t HERE
        //????????????????

        System.out.println("Main thread [" + Thread.currentThread().getName() + "] after termination");
        t.join();
    }

    private void someValuableJob() {
        System.out.println("Start someValuableJob");
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void anotherValuableJob() {
        System.out.println("Start anotherValuableJob");
        while(true){ }
    }
}
