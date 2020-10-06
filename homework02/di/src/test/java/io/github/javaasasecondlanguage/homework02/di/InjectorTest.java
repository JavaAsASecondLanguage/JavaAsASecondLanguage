package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InjectorTest {

    @BeforeEach
    void begin() {
        Context.clear();
    }

    @Test
    void injectClasses() {
        new Context()
                .register(123, "num123")
                .register(Integer.valueOf(1234), "num1234")
                .register("test string 1", "test1")
                .register("test string 2", "test2");

        assertEquals(Integer.valueOf(123), Context.getContext(Integer.class, "num123"));
        assertEquals(Integer.valueOf(1234), Context.getContext(Integer.class, "num1234"));
        assertEquals("test string 1", Injector.inject(String.class, "test1"));
        assertEquals("test string 2", Injector.inject(String.class, "test2"));
        assertThrows(RuntimeException.class, () -> Injector.inject(String.class, "test"));
        assertThrows(RuntimeException.class, () -> Injector.inject(Integer.class, "test1"));
        assertThrows(RuntimeException.class, () -> Injector.inject(String.class, "num123"));
    }

    @Test
    void injectCollection() {
        new Context()
                .register(new HashMap<String, String>())
                .register(new ArrayList<Integer>());

        assertDoesNotThrow(() -> {
            var map = Injector.inject(Map.class);
            map.put("1", "10");
            map.put("2", "20");
            map.put("3", "30");
            assertEquals("20", map.get("2"));
        });

        assertDoesNotThrow(() -> {
            var list = Injector.inject(List.class);
            list.add(10);
            list.add(20);
            list.add(30);
            list.add(40);
            assertEquals(30, list.get(2));
        });

    }


}
