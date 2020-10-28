package io.github.javaasasecondlanguage.app;

import io.github.javaasasecondlanguage.annotation.Immutable;

import java.util.List;

@Immutable
public class MutableClass {
    private final List<String> strs = null;
    public final int anInt = 42;
    private final String string = "42";
}
