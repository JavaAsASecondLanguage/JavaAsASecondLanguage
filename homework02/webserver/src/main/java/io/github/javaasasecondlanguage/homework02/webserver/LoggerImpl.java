package io.github.javaasasecondlanguage.homework02.webserver;

import io.github.javaasasecondlanguage.homework02.di.Injector;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

public class LoggerImpl implements Logger {

    private final java.util.logging.Logger logger = Injector.inject(java.util.logging.Logger.class);

    public LoggerImpl() {
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new MessageFormatter());
        logger.addHandler(consoleHandler);
    }

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void warning(String msg) {
        logger.warning(msg);
    }

}
