package io.github.javaasasecondlanguage.lecture06.practice1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ExecutorServices {
    @Test
    void executorService() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Future<Boolean> success = executor.submit(() -> {
            System.out.println("Hello from [" + Thread.currentThread().getName() + "]");
            Thread.sleep(10000);
            return true;
        });
        Boolean result = success.get();
        System.out.println("Main thread [" + Thread.currentThread().getName() + "] received result " + result);
    }
}
