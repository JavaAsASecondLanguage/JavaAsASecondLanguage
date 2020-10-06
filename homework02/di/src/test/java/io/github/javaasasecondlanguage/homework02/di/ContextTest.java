package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {
    private Context context = new Context();

    @BeforeEach
    void setup() {
        context.clear();
    }

    @Test
    void registerAndFindWithQualifier() {
        var str = "Some value";
        context.register(() -> str, "key1");
        context.register(() -> 8080, "key2");
        context.register(() -> 22, "key3");
        context.register(() -> 653, "key3");
        context.resolve();

        assertEquals(str, context.findByQualifier("key1"));
        assertEquals(8080, context.findByQualifier("key2"));
        assertEquals(653, context.findByQualifier("key3"));

        assertThrows(Context.UnresolvableDepsError.class, () -> context.findByQualifier("key99"));
    }

    @Test
    void registerAndFindByClass() {
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

        assertEquals(map, context.findByClass(map.getClass()));
        assertEquals(map, context.findByClass(HashMap.class));
        assertEquals(map, context.findByClass(AbstractMap.class));
        assertEquals(map, context.findByClass(Map.class));

        assertEquals(list, context.findByClass(list.getClass()));
        assertEquals(list, context.findByClass(ArrayList.class));
        assertEquals(list, context.findByClass(AbstractList.class));
        assertEquals(list, context.findByClass(List.class));
        assertEquals(list, context.findByClass(Collection.class));

        assertThrows(Context.UnresolvableDepsError.class, () -> context.findByClass(LinkedList.class));
    }
}
