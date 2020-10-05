package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Injector;

public class MyLogger implements Logger {
    LogLevel logLevel = Injector.inject(LogLevel.class, "currentLogLevel");

    @Override
    public void info(String msg) {
        info(LogLevel.INFO, msg);
    }

    @Override
    public void info(LogLevel logLevel, String msg) {
        if (this.logLevel.less(logLevel)) {
            return;
        }

        System.out.println(msg);
    }
}
