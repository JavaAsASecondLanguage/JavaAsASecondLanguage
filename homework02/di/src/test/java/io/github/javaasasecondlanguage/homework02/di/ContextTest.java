package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {

    @BeforeEach
    void begin() {
        Context.clear();
    }

    @Test
    void registerStrings() {
        new Context()
            .register("test string 1", "test1")
            .register("test string 2", "test2");

        assertEquals("test string 1", Context.getContext(String.class, "test1"));
        assertEquals("test string 2", Context.getContext(String.class, "test2"));
        assertThrows(RuntimeException.class, () -> Context.getContext(String.class, "test"));
        assertThrows(RuntimeException.class, () -> Context.getContext(Integer.class, "test1"));
    }

    @Test
    void registerInteger() {
        new Context()
                .register(123, "num123")
                .register(Integer.valueOf(1234), "num1234");

        assertEquals(Integer.valueOf(123), Context.getContext(Integer.class, "num123"));
        assertEquals(Integer.valueOf(1234), Context.getContext(Integer.class, "num1234"));
        assertThrows(RuntimeException.class, () -> Context.getContext(Integer.class, "test"));
        assertThrows(RuntimeException.class, () -> Context.getContext(String.class, "num123"));
        assertDoesNotThrow(() -> new Context().register(Integer.class));
    }

    @Test
    void registerCollection() {
        new Context()
                .register(new HashMap<String, String>())
                .register(new ArrayList<String>());

        assertEquals(HashMap.class, Context.getContext(Map.class).getClass());
        assertEquals(ArrayList.class, Context.getContext(List.class).getClass());
    }

    @Test
    void registerComplex() {
        new Context()
                .register(123, "num123")
                .register(Integer.valueOf(1234), "num1234")
                .register("test string 1", "test1")
                .register("test string 2", "test2")
                .register(new HashMap<String, String>())
                .register(new ArrayList<String>());

        assertEquals("test string 2", Context.getContext(String.class, "test2"));
        assertEquals(Integer.valueOf(1234), Context.getContext(Integer.class, "num1234"));
        assertEquals(HashMap.class, Context.getContext(Map.class).getClass());
        assertEquals(ArrayList.class, Context.getContext(List.class).getClass());
    }

}
