package io.github.javaasasecondlanguage.homework02.di.utils;

import java.util.Objects;

public class TestObject implements TestInterface {
    private Object field;

    public Object getField() {
        return field;
    }

    public TestObject setField(Object field) {
        this.field = field;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestObject that = (TestObject) o;
        return Objects.equals(field, that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
