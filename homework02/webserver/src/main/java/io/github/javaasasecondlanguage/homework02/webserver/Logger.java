package io.github.javaasasecondlanguage.homework02.webserver;

public interface Logger {

    public enum LogLevel {
        ERROR(1),
        INFO(2);

        Integer value;

        LogLevel(Integer value) {
            this.value = value;
        }

        boolean less(LogLevel other) {
            return this.value < other.value;
        }
    }

    void info(String msg);

    void info(LogLevel severity, String msg);

}
