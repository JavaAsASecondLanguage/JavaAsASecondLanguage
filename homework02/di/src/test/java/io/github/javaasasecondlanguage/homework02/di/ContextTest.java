package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContextTest {
    @Test
    void shouldFindIfThereIsExactlyOneMatchingObject() {
        var context = new Context();
        context.register(1);
        var found = context.find(Integer.class, Injector.DEFAULT_QUALIFIER);
        assertEquals(1, found);
    }

    @Test
    void shouldFailIfThereIsMoreThanOneMatchingObject() {
        var context = new Context();
        context.register(1, "test").register(2, "test");
        assertThrows(RuntimeException.class, () -> context.find(Integer.class, "test"));
    }

    @Test
    void shouldFailIfThereIsNoMatchingObject() {
        var context = new Context();
        context.register(1, "test");
        assertThrows(RuntimeException.class, () -> context.find(String.class, "test"));
    }

}
