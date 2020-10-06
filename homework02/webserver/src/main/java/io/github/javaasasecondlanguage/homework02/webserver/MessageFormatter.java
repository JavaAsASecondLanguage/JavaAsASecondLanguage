package io.github.javaasasecondlanguage.homework02.webserver;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MessageFormatter extends Formatter {

    private static final String SEPARATOR = "::";
    private static final String NEW_LINE = "\n";
    private static final String FORMAT_MSG = "%s%s%s%s%s%s%s%s";

    @Override
    public String format(LogRecord record) {

        return String.format(FORMAT_MSG,
                record.getThreadID(),
                SEPARATOR,
                      record.getLoggerName(),
                      SEPARATOR,
                      new Date(record.getMillis()),
                      SEPARATOR,
                      record.getMessage(),
                      NEW_LINE);
    }
}
