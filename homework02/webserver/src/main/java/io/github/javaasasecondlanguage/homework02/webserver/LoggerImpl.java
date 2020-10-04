package io.github.javaasasecondlanguage.homework02.webserver;


import java.util.logging.Level;

public class LoggerImpl implements Logger {
    java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(getClass().getSimpleName());

    @Override
    public void info(String msg) {
        this.logger.info(msg);
    }

    @Override
    public void warning(String msg) {
        this.logger.warning(msg);
    }

    @Override
    public void config(String msg) {
        this.logger.config(msg);
    }

    @Override
    public void fine(String msg) {
        this.logger.fine(msg);
    }

    @Override
    public void severe(String msg) {
        this.logger.severe(msg);
    }

    @Override
    public void severe(String msg, Throwable throwable) {
        this.logger.log(Level.SEVERE, msg, throwable);
    }

    @Override
    public void log(Level level, String msg) {
        this.logger.log(level, msg);
    }

}
