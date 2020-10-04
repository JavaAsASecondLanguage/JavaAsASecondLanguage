package io.github.javaasasecondlanguage.homework02.webserver;

import java.util.logging.Level;

/**
 * Обработчик сообщений журнала.
 */
public interface Logger {
    /**
     * Обработать сообщение уровня INFO
     *
     * @param msg сообщение
     */
    void info(String msg);

    /**
     * Обработать сообщение уровня WARNING
     *
     * @param msg сообщение
     */
    void warning(String msg);

    /**
     * Обработать сообщение уровня CONFIG
     *
     * @param msg сообщение
     */
    void config(String msg);

    /**
     * Обработать сообщение уровня FINE
     *
     * @param msg сообщение
     */
    void fine(String msg);

    /**
     * Обработать сообщение уровня SEVERE
     *
     * @param msg сообщение
     */
    void severe(String msg);

    /**
     * Обработать сообщение уровня SEVERE с указанием источника ошибки.
     *
     * @param msg       сообщение
     * @param throwable источник ошибки
     */
    void severe(String msg, Throwable throwable);

    /**
     * Обработать сообщение в соответствии с указанном уровнем важности.
     *
     * @param level уровень важности сообщения
     * @param msg   сообщение
     */
    void log(Level level, String msg);
}
