[![Java CI with Gradle](https://github.com/alxkm/cache/actions/workflows/gradle.yml/badge.svg)](https://github.com/alxkm/cache/actions/workflows/gradle.yml)[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

# Cache Implementations

This repository contains implementations of both Least Recently Used (LRU) and Least Frequently Used (LFU) Cache in Java using various data structures:

## LRU Cache Implementations

1. **LRULinkedHashMapCache**: Implementation using `LinkedHashMap` to maintain insertion order.
2. **LRUDoublyLinkedListCache**: Implementation using a custom doubly linked list and `HashMap`.
3. **LRUHashMapQueueCache**: Implementation using `HashMap` and `Deque` (double-ended queue) for managing access order.

### Overview

LRU Cache is a caching technique where the least recently used items are removed from the cache when it exceeds its predefined capacity. This ensures that the cache doesn't grow indefinitely and optimizes access time by retaining frequently used items.

### CacheService Interface

#### Description
`CacheService` defines the interface for the LRU Cache implementations.

#### Methods
- `put(T id, V value)`: Inserts a key-value pair into the cache. If the key already exists, updates the value and adjusts its position based on access.
- `get(T id)`: Retrieves the value associated with the key from the cache. If the key exists, marks it as recently used.
- `evict(T id)`: Removes the key-value pair from the cache.

### Implementations

#### 1. LRULinkedHashMapCache

**Description**: LRU Cache implemented using `LinkedHashMap`, which maintains elements in the order of their access. When the capacity is exceeded, the least recently accessed entry is removed.

**Constructor**
```java
LRULinkedHashMapCache(int capacity): Initializes the cache with a specified capacity.
```

**Time Complexity**
- `put`: O(1)
- `get`: O(1)
- `evict`: O(1)

#### 2. LRUDoublyLinkedListCache

**Description**: LRU Cache implemented using a custom doubly linked list (`Node` class) and `HashMap`. This implementation manages the access order explicitly by moving nodes within the linked list.

**Constructor**
```java
LRUDoublyLinkedListCache(int capacity): Initializes the cache with a specified capacity.
```

**Time Complexity**
- `put`: O(1)
- `get`: O(1)
- `evict`: O(1)

#### 3. LRUHashMapQueueCache

**Description**: LRU Cache implemented using `HashMap` and `Deque` (specifically `LinkedList`), where `Deque` is used to maintain the order of keys based on their access time. The most recently accessed keys are moved to the front of the `Deque`.

**Constructor**
```java
LRUHashMapQueueCache(int capacity): Initializes the cache with a specified capacity.
```

**Time Complexity**
- `put`: O(1)
- `get`: O(1)
- `evict`: O(1)

### Usage

Each implementation provides the same interface (`CacheService`) for inserting, retrieving, and evicting elements from the cache. Here's an example of usage for all three classes:

```java
public class Main {
    public static void main(String[] args) {
        // Using LRULinkedHashMapCache
        CacheService<Integer, String> linkedHashMapCache = new LRULinkedHashMapCache<>(3);
        linkedHashMapCache.put(1, "one");
        linkedHashMapCache.put(2, "two");
        linkedHashMapCache.put(3, "three");
        System.out.println(linkedHashMapCache.get(1)); // Output: one
        linkedHashMapCache.put(4, "four");
        System.out.println(linkedHashMapCache.get(2)); // Output: null (evicted)
        linkedHashMapCache.put(5, "five");
        System.out.println(linkedHashMapCache.get(3)); // Output: null (evicted)
        System.out.println(linkedHashMapCache.get(4)); // Output: four
        System.out.println(linkedHashMapCache.get(5)); // Output: five

        // Using LRUDoublyLinkedListCache
        CacheService<Integer, String> doublyLinkedListCache = new LRUDoublyLinkedListCache<>(3);
        doublyLinkedListCache.put(1, "one");
        doublyLinkedListCache.put(2, "two");
        doublyLinkedListCache.put(3, "three");
        System.out.println(doublyLinkedListCache.get(1)); // Output: one
        doublyLinkedListCache.put(4, "four");
        System.out.println(doublyLinkedListCache.get(2)); // Output: null (evicted)
        doublyLinkedListCache.put(5, "five");
        System.out.println(doublyLinkedListCache.get(3)); // Output: null (evicted)
        System.out.println(doublyLinkedListCache.get(4)); // Output: four
        System.out.println(doublyLinkedListCache.get(5)); // Output: five

        // Using LRUHashMapQueueCache
        CacheService<Integer, String> hashMapQueueCache = new LRUHashMapQueueCache<>(3);
        hashMapQueueCache.put(1, "one");
        hashMapQueueCache.put(2, "two");
        hashMapQueueCache.put(3, "three");
        System.out.println(hashMapQueueCache.get(1)); // Output: one
        hashMapQueueCache.put(4, "four");
        System.out.println(hashMapQueueCache.get(2)); // Output: null (evicted)
        hashMapQueueCache.put(5, "five");
        System.out.println(hashMapQueueCache.get(3)); // Output: null (evicted)
        System.out.println(hashMapQueueCache.get(4)); // Output: four
        System.out.println(hashMapQueueCache.get(5)); // Output: five
    }
}
```


## LFU Cache Implementations

### Overview

LFU Cache is a caching technique where the least frequently used items are removed from the cache when it exceeds its predefined capacity. This helps in retaining the most frequently accessed items in the cache.

### LFUDoublyLinkedListCache Class

#### Description
`LFUDoublyLinkedListCache` is a Java implementation of an LFU Cache that uses a `HashMap` for quick access to cache entries and a custom doubly linked list to maintain frequency counts. This implementation ensures efficient retrieval, insertion, and eviction of cache entries based on their access frequency.

#### Constructor
```java
/**
 * Constructs an LFU Cache with the specified capacity.
 *
 * @param capacity the capacity of the cache
 */
public LFUDoublyLinkedListCache(int capacity);
```

#### Methods
```java
/**
 * Adds an item to the cache. If the cache is full, evicts the least frequently used item.
 * If an item with the same key already exists, updates its value and frequency.
 *
 * @param id    the key with which the specified value is to be associated
 * @param value the value to be associated with the specified key
 */
@Override
public void put(T id, V value);

/**
 * Retrieves the value associated with the specified key. If the key is found,
 * increases its frequency.
 *
 * @param id the key whose associated value is to be returned
 * @return the value to which the specified key is mapped, or null if this cache contains no mapping for the key
 */
@Override
public V get(T id);

/**
 * Evicts the item with the specified key from the cache.
 *
 * @param id the key whose mapping is to be removed from the cache
 */
@Override
public void evict(T id);
```

**Time Complexity**
- `put`: O(1)
- `get`: O(1)
- `evict`: O(1)

#### CacheNode Class

Represents a key-value pair with a frequency counter.

```java
private static class CacheNode<T, V> {
    T key;
    V value;
    int frequency;

    /**
     * Constructs a new node with the specified key and value. Initializes the frequency to 1.
     *
     * @param key   the key of the node
     * @param value the value of the node
     */
    CacheNode(T key, V value);
}
```

### LFUTreeMapCache Class

#### Description
`LFUTreeMapCache` is a Java implementation of an LFU Cache that uses a `HashMap` for quick access to cache entries and a `TreeMap` to maintain frequency counts in sorted order. This implementation ensures efficient retrieval, insertion, and eviction of cache entries based on their access frequency.

#### Constructor
```java
/**
 * Constructs an LFU Cache with the specified capacity.
 *
 * @param capacity the capacity of the cache
 */
public LFUTreeMapCache(int capacity);
```

#### Methods
```java
/**
 * Adds an item to the cache. If the cache is full, evicts the least frequently used item.
 * If an item with the same key already exists, updates its value and frequency.
 *
 * @param id    the key with which the specified value is to be associated
 * @param value the value to be associated with the specified key
 */
@Override
public void put(T id, V value);

/**
 * Retrieves the value associated with the specified key. If the key is found,
 * increases its frequency.
 *
 * @param id the key whose associated value is to be returned
 * @return the value to which the specified key is mapped, or null if this cache contains no mapping for the key
 */
@Override
public V get(T id);

/**
 * Evicts the item with the specified key from the cache.
 *
 * @param id the key whose mapping is to be removed from the cache
 */
@Override
public void evict(T id);
```

**Time Complexity**
- `put`: O(log n)
- `get`: O(log n)
- `evict`: O(log n)

#### CacheNode Class

Represents a key-value pair with a frequency counter.

```java
private static class CacheNode<T, V> {
    T key;
    V value;
    int frequency;

    /**
     * Constructs a new node with the specified key and value. Initializes the frequency to 1.
     *
     * @param key   the key of the node
     * @param value the value of the node
     */
    CacheNode(T key, V value);
}
```

### Example

```java
public class Main {
    public static void main(String[] args) {
        LFUTreeMapCache<Integer, String> treeMapCache = new LFUTreeMapCache<>(3);
        LFUDoublyLinkedListCache<Integer, String> linkedListCache = new LFUDoublyLinkedListCache<>(3);
        
        // Example usage for LFUTreeMapCache
        treeMapCache.put(1, "one");
        treeMapCache.put(2, "two");
        treeMapCache.put(3, "three");
        
        System.out.println(treeMapCache.get(1)); // Outputs: one
        
        treeMapCache.put(4, "four"); // Evicts key 2, which is the least frequently used
        
        System.out.println(treeMapCache.get(2)); // Outputs: null (since key 2 has been evicted)
        
        treeMapCache.evict(3); // Manually evicts key 3
        
        System.out.println(treeMapCache.get(3)); // Outputs: null (since key 3 has been evicted)

        // Example usage for LFUDoublyLinkedListCache
        linkedListCache.put(1, "one");
        linkedListCache.put(2, "two");
        linkedListCache.put(3, "three");
        
        System.out.println(linkedListCache.get(1)); // Outputs: one
        
        linkedListCache.put(4, "four"); // Evicts key 2, which is the least frequently used
        
        System.out.println(linkedListCache.get(2)); // Outputs: null (since key 2 has been evicted)
        
        linkedListCache.evict(3); // Manually evicts key 3
        
        System.out.println(linkedListCache.get(3)); // Outputs: null (since key 3 has been evicted)
    }
}
```

### Testing

The repository includes JUnit tests that validate the functionality of each cache implementation. These tests cover insertion, retrieval, eviction, and edge cases such as updating existing entries.

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.

Feel free to fork and modify these implementations for your own use cases or contribute to enhance them further. If you have any questions or suggestions, please feel free to reach out or open an issue!

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Acknowledgments

This implementation was inspired by various LFU and LRU cache algorithms and adapted for educational purposes. Special thanks to the open-source community for their contributions and ideas.

## Contact

For any questions or suggestions, please feel free to reach out or open an issue!
