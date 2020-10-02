package io.github.javaasasecondlanguage.lecture03.practice2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CollectionUtils {
    /**
     * Find common elements for specified sets. Null elements can not be common
     * Change signature
     *
     * @param s1 set
     * @param s2 set
     * @return common elements for given sets
     */
    public static <T> Set intersect(Set<? extends T> s1, Set<? extends T> s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return Collections.emptySet();
        }
        Set<T> result = new HashSet<>(s1);
        result.retainAll(s2);
        result.removeIf(Objects::isNull);
        return result;
    }
}
