package org.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a Least Frequently Used (LFU) Cache using a doubly linked list.
 *
 * @param <T> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public class LFUDoublyLinkedListCache<T, V> implements CacheService<T, V> {

    private final int capacity;
    private int size;
    private final Map<T, Node<T, V>> cache;
    private final Map<Integer, DoublyLinkedList<T, V>> frequencyMap;
    private int minFrequency;

    /**
     * Constructs an LFU Cache with the specified capacity.
     *
     * @param capacity the capacity of the cache
     */
    public LFUDoublyLinkedListCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.minFrequency = 0;
    }

    /**
     * Adds an item to the cache. If the cache is full, evicts the least frequently used item.
     * If an item with the same key already exists, updates its value and frequency.
     *
     * @param id    the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    @Override
    public void put(T id, V value) {
        if (capacity <= 0) return;

        if (cache.containsKey(id)) {
            Node<T, V> node = cache.get(id);
            node.value = value;
            get(id); // Increase frequency
        } else {
            if (size == capacity) {
                // Evict the least frequently used item
                DoublyLinkedList<T, V> list = frequencyMap.get(minFrequency);
                Node<T, V> nodeToEvict = list.tail.prev;
                list.remove(nodeToEvict);
                cache.remove(nodeToEvict.key);
                size--;
            }
            // Add new item
            Node<T, V> newNode = new Node<>(id, value);
            cache.put(id, newNode);
            frequencyMap.computeIfAbsent(1, k -> new DoublyLinkedList<>()).add(newNode);
            minFrequency = 1;
            size++;
        }
    }

    /**
     * Retrieves the value associated with the specified key. If the key is found,
     * increases its frequency.
     *
     * @param id the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this cache contains no mapping for the key
     */
    @Override
    public V get(T id) {
        if (!cache.containsKey(id)) return null;

        Node<T, V> node = cache.get(id);
        int currentFreq = node.frequency;
        DoublyLinkedList<T, V> list = frequencyMap.get(currentFreq);
        list.remove(node);

        if (currentFreq == minFrequency && list.size == 0) {
            minFrequency++;
        }

        node.frequency++;
        frequencyMap.computeIfAbsent(node.frequency, k -> new DoublyLinkedList<>()).add(node);
        return node.value;
    }

    /**
     * Evicts the item with the specified key from the cache.
     *
     * @param id the key whose mapping is to be removed from the cache
     */
    @Override
    public void evict(T id) {
        if (!cache.containsKey(id)) return;

        Node<T, V> node = cache.get(id);
        int currentFreq = node.frequency;
        DoublyLinkedList<T, V> list = frequencyMap.get(currentFreq);
        list.remove(node);
        cache.remove(id);

        if (currentFreq == minFrequency && list.size == 0) {
            minFrequency++;
        }

        size--;
    }

    /**
     * Node class representing a key-value pair with a frequency counter.
     *
     * @param <T> the type of key
     * @param <V> the type of value
     */
    private static class Node<T, V> {
        T key;
        V value;
        int frequency;
        Node<T, V> prev;
        Node<T, V> next;

        /**
         * Constructs a new node with the specified key and value. Initializes the frequency to 1.
         *
         * @param key   the key of the node
         * @param value the value of the node
         */
        Node(T key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }

    /**
     * DoublyLinkedList class representing a list of nodes. Provides methods to add and remove nodes.
     *
     * @param <T> the type of key
     * @param <V> the type of value
     */
    private static class DoublyLinkedList<T, V> {
        Node<T, V> head;
        Node<T, V> tail;
        int size;

        /**
         * Constructs an empty doubly linked list with dummy head and tail nodes.
         */
        DoublyLinkedList() {
            this.head = new Node<>(null, null);
            this.tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        /**
         * Adds a node to the front of the list.
         *
         * @param node the node to be added
         */
        void add(Node<T, V> node) {
            Node<T, V> next = head.next;
            head.next = node;
            node.prev = head;
            node.next = next;
            next.prev = node;
            size++;
        }

        /**
         * Removes a node from the list.
         *
         * @param node the node to be removed
         */
        void remove(Node<T, V> node) {
            Node<T, V> prev = node.prev;
            Node<T, V> next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }
    }
}

