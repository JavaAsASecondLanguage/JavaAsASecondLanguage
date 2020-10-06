package io.github.javaasasecondlanguage.homework02.webserver;

import java.util.logging.Level;

public interface Logger {
    /**
     * Process messages of level INFO
     *
     * @param msg msg
     */
    void info(String msg);

    /**
     * Process messages of level WARNING
     *
     * @param msg msg
     */
    void warning(String msg);

    /**
     * Process messages of level CONFIG
     *
     * @param msg msg
     */
    void config(String msg);

    /**
     * Process messages of level FINE
     *
     * @param msg msg
     */
    void fine(String msg);

    /**
     * Process messages of level SEVERE
     *
     * @param msg msg
     */
    void severe(String msg);

    /**
     * Process messages of level SEVERE with source of error
     *
     * @param msg msg
     * @param throwable source of error
     */
    void severe(String msg, Throwable throwable);

    /**
     * Process messages according to their level of importance
     *
     * @param level level of importance
     * @param msg msg
     */
    void log(Level level, String msg);
}
