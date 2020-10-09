package io.github.javaasasecondlanguage.homework02.webserver;

public class MyLogger implements Logger {
    @Override
    public void info(String msg) {
        System.out.println(msg);
    }

    public void error(String msg) {
        System.err.println(msg);
    }
}