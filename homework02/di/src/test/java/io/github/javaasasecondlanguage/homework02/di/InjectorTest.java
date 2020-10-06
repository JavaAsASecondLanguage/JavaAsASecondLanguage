package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InjectorTest {
    private Context context = new Context();

    @BeforeEach
    void setup() {
        context.clear();
    }

    @Test
    void testInjectWithQualifier() {
        var str = "Some value";
        context.register(() -> str, "key1");
        context.register(() -> 8080, "key2");
        context.register(() -> 22, "key3");
        context.register(() -> 653, "key3");
        context.resolve();

        assertEquals(str, Injector.inject(String.class, "key1"));
        assertEquals((int)8080, (int)Injector.inject(Integer.class, "key2"));
        assertEquals((int)653, (int)Injector.inject(Integer.class, "key3"));

        assertThrows(
                Context.UnresolvableDepsError.class,
                () -> Injector.inject(String.class, "key99")
        );
    }

    @Test
    void testInjectWithClass() {
        var map = new HashMap<Integer, String>();
        map.put(1, "1");
        map.put(20, "20");

        var list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);

        context.register(() -> map);
        context.register(() -> list);
        context.resolve();

        assertEquals(map, Injector.inject(map.getClass()));
        assertEquals(map, Injector.inject(HashMap.class));
        assertEquals(map, Injector.inject(AbstractMap.class));
        assertEquals(map, Injector.inject(Map.class));

        assertEquals(list, Injector.inject(list.getClass()));
        assertEquals(list, Injector.inject(ArrayList.class));
        assertEquals(list, Injector.inject(AbstractList.class));
        assertEquals(list, Injector.inject(List.class));
        assertEquals(list, Injector.inject(Collection.class));

        assertThrows(
                Context.UnresolvableDepsError.class,
                () -> Injector.inject(LinkedList.class)
        );
    }
}
