package com.maxmind.db;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.IllegalAccessException;
import java.lang.InstantiationException;
import java.lang.reflect.InvocationTargetException;

/**
 * A simplistic cache using a {@link ConcurrentHashMap}. There's no eviction
 * policy, it just fills up until reaching the specified capacity <small>(or
 * close enough at least, bounds check is not atomic :)</small>
 */
public class CHMCache implements NodeCache {

    private static final int DEFAULT_CAPACITY = 4096;

    private final int capacity;
    private final ConcurrentHashMap<CacheKey, Object> cache;
    private boolean cacheFull = false;

    public CHMCache() {
        this(DEFAULT_CAPACITY);
    }

    public CHMCache(int capacity) {
        this.capacity = capacity;
        this.cache = new ConcurrentHashMap<>(capacity);
    }

    @Override
    public Object get(CacheKey key, Loader loader)
            throws IOException,
                   InstantiationException,
                   IllegalAccessException,
                   InvocationTargetException,
                   NoSuchMethodException {
        Object value = cache.get(key);
        if (value == null) {
            value = loader.load(key);
            if (!cacheFull) {
                if (cache.size() < capacity) {
                    cache.put(key, value);
                } else {
                    cacheFull = true;
                }
            }
        }
        return value;
    }

}
