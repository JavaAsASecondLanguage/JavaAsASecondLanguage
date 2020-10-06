package io.github.javaasasecondlanguage.homework02.webserver;

public class LoggerImpl implements Logger {

    private java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
            getClass().getName()
    );

    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    @Override
    public void warn(String msg) {
        logger.warning(msg);
    }

    @Override
    public void error(String msg) {
        logger.severe(msg);
    }
}
