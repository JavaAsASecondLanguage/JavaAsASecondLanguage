package io.github.javaasasecondlanguage.homework02.di;

import java.util.Objects;

public class Bean<T> {
    private final T origin;
    private final String qualName;

    public Bean(T object, String qualName) {
        this.origin = object;
        this.qualName = qualName;
    }

    public T getObject() {
        return origin;
    }

    public String getQualName() {
        return qualName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bean<?> bean = (Bean<?>) o;
        return Objects.equals(origin, bean.origin)
                && Objects.equals(qualName, bean.qualName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, qualName);
    }

    public <E> boolean isApplicable(Class<E> clazz) {
        return clazz.isInstance(origin);
    }
}
