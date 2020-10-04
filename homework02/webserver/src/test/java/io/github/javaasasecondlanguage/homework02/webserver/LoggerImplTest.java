package io.github.javaasasecondlanguage.homework02.webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class LoggerImplTest {

    private LoggerImpl logger;

    @BeforeEach
    void setUp() {
        logger = new LoggerImpl();
    }

    @Test
    void info() {
        assertDoesNotThrow(() -> logger.info("info"));
    }

    @Test
    void warning() {
        assertDoesNotThrow(() -> logger.warning("warning"));
    }

    @Test
    void config() {
        assertDoesNotThrow(() -> logger.config("config"));
    }

    @Test
    void fine() {
        assertDoesNotThrow(() -> logger.fine("fine"));
    }

    @Test
    void severe() {
        assertDoesNotThrow(() -> logger.severe("severe"));
    }

    @Test
    void testSevereWithThrowable() {
        assertDoesNotThrow(() -> logger.severe("severe", new Throwable()));
    }

    @Test
    void log() {
        assertDoesNotThrow(() -> logger.log(Level.FINEST, "log"));
    }
}