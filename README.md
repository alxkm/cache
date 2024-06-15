# LRU Cache Implementations

This repository contains implementations of a Least Recently Used (LRU) Cache in Java using different data structures:

1. **LRUCacheServiceLinkedHashMapBased**: Implementation using `LinkedHashMap` to maintain insertion order.
2. **LRUCacheNodeBased**: Implementation using a custom doubly linked list and `HashMap`.
3. **LRUCacheServiceQueueBased**: Implementation using `HashMap` and `Deque` (double-ended queue) for managing access order.

## Overview

LRU Cache is a caching technique where the least recently used items are removed from the cache when the cache exceeds its predefined capacity. This ensures that the cache doesn't grow indefinitely and optimizes access time by retaining frequently used items.

## CacheService Interface

### Description

`CacheService` defines the interface for the LRU Cache implementations.

### Methods

- `put(T id, V value)`: Inserts a key-value pair into the cache. If the key already exists, updates the value and adjusts its position based on access.
- `get(T id)`: Retrieves the value associated with the key from the cache. If the key exists, marks it as recently used.
- `evict(T id)`: Removes the key-value pair from the cache.

## Implementations

### 1. LRUCacheServiceLinkedHashMapBased

#### Description

LRU Cache implemented using `LinkedHashMap`, which maintains elements in the order of their access. When the capacity is exceeded, the least recently accessed entry is removed.

#### Constructor

- `LRUCacheServiceLinkedHashMapBased(int capacity)`: Initializes the cache with a specified capacity.

### 2. LRUCacheNodeBased

#### Description

LRU Cache implemented using a custom doubly linked list (`Node` class) and `HashMap`. This implementation manages the access order explicitly by moving nodes within the linked list.

#### Constructor

- `LRUCacheNodeBased(int capacity)`: Initializes the cache with a specified capacity.

### 3. LRUCacheServiceQueueBased

#### Description

LRU Cache implemented using `HashMap` and `Deque` (specifically `LinkedList`), where `Deque` is used to maintain the order of keys based on their access time. The most recently accessed keys are moved to the front of the `Deque`.

#### Constructor

- `LRUCacheServiceQueueBased(int capacity)`: Initializes the cache with a specified capacity.

## Usage

Each implementation provides the same interface (`CacheService`) for inserting, retrieving, and evicting elements from the cache. Here's an example of usage:

```java
public static void main(String[] args) {
    CacheService<Integer, String> cache = new LRUCacheServiceQueueBased<>(3);
    cache.put(1, "one");
    cache.put(2, "two");
    cache.put(3, "three");
    System.out.println(cache.get(1)); // Output: one
    cache.put(4, "four");
    System.out.println(cache.get(2)); // Output: null (evicted)
    cache.put(5, "five");
    System.out.println(cache.get(3)); // Output: null (evicted)
    System.out.println(cache.get(4)); // Output: four
    System.out.println(cache.get(5)); // Output: five
}
```

## Testing

The repository includes JUnit tests (`CacheServiceTest.java`) that validate the functionality of each cache implementation. These tests cover insertion, retrieval, eviction, and edge cases such as updating existing entries.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.

---

Feel free to fork and modify these implementations for your own use cases or contribute to enhance them further. If you have any questions or suggestions, please feel free to reach out or open an issue!