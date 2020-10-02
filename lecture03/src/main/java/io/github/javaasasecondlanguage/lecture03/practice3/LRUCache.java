package io.github.javaasasecondlanguage.lecture03.practice3;

import java.util.HashMap;

/**
 * Cache that only stores 'capacity' values that were put last
 */
public class LRUCache<E> {

    private int capacity;
    HashMap<String, String> cache;

    public LRUCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        cache = new HashMap<>();
    }

    public String get(String key) {
        return cache.get(key);
    }

    public String put(String key, String value) {
        if (size() == capacity) {
            String firstKey = cache.keySet().stream().findFirst().get();
            cache.remove(firstKey);
        }
        return cache.put(key, value);
    }

    public int size() {
        return cache.size();
    }
}
