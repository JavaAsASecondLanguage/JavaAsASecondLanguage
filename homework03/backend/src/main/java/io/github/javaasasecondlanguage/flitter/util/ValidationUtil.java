package io.github.javaasasecondlanguage.flitter.util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFound(T object, String msg) {
        if (object == null) {
            throw new NotFoundException(msg);
        }
        return object;
    }

    public static <T> T checkExists(T object, String msg) {
        if (object != null) {
            throw new AlreadyExistsException(msg);
        }
        return object;
    }

}
