package org.cache;

/**
 * CacheService defines the interface for a cache that stores key-value pairs.
 *
 * @param <T> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface CacheService<T, V> {

    /**
     * Inserts the specified key-value pair into the cache.
     * If the cache previously contained a mapping for the key, the old value is replaced.
     * If inserting the new pair exceeds the cache's capacity, the least recently used entry is removed.
     *
     * @param id    the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    void put(T id, V value);

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this cache contains no mapping for the key.
     * Accessing the key marks it as recently used.
     *
     * @param id the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or {@code null} if this cache contains no mapping for the key
     */
    V get(T id);

    /**
     * Removes the mapping for a key from this cache if it is present.
     *
     * @param id the key whose mapping is to be removed from the cache
     */
    void evict(T id);
}