package io.github.javaasasecondlanguage.homework02.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContextTest {
    private Context context = Context.getRoot();
    private int testNum = 1;

    @BeforeEach
    void setup() {
        var testName = "test" + testNum;
        context = context.openScope(testName);
        Injector.setResolutionScope(testName);
    }

    @Test
    void injectPrimitives() {
        context.register("value", "str");
        context.register("other", "str1");
        assertEquals("value", Injector.inject(String.class, "str"));
        assertEquals("other", Injector.inject(String.class, "str1"));

        context.register(20, "int");
        context.register(21, "int1");
        assertTrue(20 == Injector.inject(Integer.class, "int"));
        assertTrue(21 == Injector.inject(Integer.class, "int1"));

        context.register("first");
        context.register(String.class, () -> "second");
        assertEquals("first", Injector.inject(String.class));
    }

    @Test
    void injectCollection() {

        var array1 = List.of(1, 2, 3);
        context.register(List.class, () -> array1, "array1");
        assertTrue(array1.get(0) == Injector.inject(List.class, "array1").get(0));
        assertTrue(array1.get(1) == Injector.inject(List.class, "array1").get(1));
        assertTrue(array1.get(1) == Injector.inject(List.class, "array1").get(1));

        var array2 = List.of(1, 2, 3).toArray();
        var array3 = Injector.inject(List.class, "array1").toArray();
        assertArrayEquals(array2, array2);
    }

    @Test
    void injectClasses() {

        context.register(BigInteger.TEN, "num");
        assertTrue(BigInteger.TEN == Injector.inject(BigInteger.class, "num"));
        assertTrue(BigInteger.TEN == Injector.inject(Number.class, "num"));
        assertTrue(BigInteger.TEN == Injector.inject(Serializable.class, "num"));
    }
}