package io.github.javaasasecondlanguage.homework02.di.utils;

public final class FinalObject implements TestInterface {
    private Object field;

    public Object getField() {
        return field;
    }

    public FinalObject setField(Object field) {
        this.field = field;
        return this;
    }
}
