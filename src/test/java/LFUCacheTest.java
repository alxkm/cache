import org.cache.CacheService;
import org.cache.LFUDoublyLinkedListCache;
import org.cache.LFUTreeMapCache;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for various LFU Cache implementations.
 */
public class LFUCacheTest {

    /**
     * Provides instances of LFU cache implementations to be tested.
     *
     * @return a stream of CacheService instances
     */
    private static Stream<CacheService<Integer, String>> cacheProvider() {
        return Stream.of(
                new LFUDoublyLinkedListCache<>(3),
                new LFUTreeMapCache<>(3)
        );
    }


    /**
     * Provides instances of LFU cache with negative capacity implementations to be tested.
     *
     * @return a stream of CacheService instances
     */
    private static Stream<CacheService<Integer, String>> negativeCapacityCacheProvider() {
        return Stream.of(
                new LFUDoublyLinkedListCache<>(-1),
                new LFUTreeMapCache<>(-1)
        );
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testPutAndGet(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testEviction(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // Access key 1 to increase its frequency
        assertEquals("one", cache.get(1));

        // Add a new item, which should evict the least frequently used item (key 2)
        cache.put(4, "four");

        assertNull(cache.get(2)); // key 2 should be evicted
        assertEquals("one", cache.get(1)); // key 1 should still be present
        assertEquals("three", cache.get(3)); // key 3 should still be present
        assertEquals("four", cache.get(4)); // key 4 should be added
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testUpdateValue(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(1, "uno"); // Update value

        assertEquals("uno", cache.get(1));
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testEvictManually(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        cache.evict(2); // Manually evict key 2

        assertNull(cache.get(2)); // key 2 should be evicted
        assertEquals("one", cache.get(1)); // key 1 should still be present
        assertEquals("three", cache.get(3)); // key 3 should still be present
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testFrequentAccess(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // Access key 1 and 3 multiple times
        cache.get(1);
        cache.get(3);
        cache.get(1);
        cache.get(3);

        // Add a new item, which should evict the least frequently used item (key 2)
        cache.put(4, "four");

        assertNull(cache.get(2)); // key 2 should be evicted
        assertEquals("one", cache.get(1)); // key 1 should still be present
        assertEquals("three", cache.get(3)); // key 3 should still be present
        assertEquals("four", cache.get(4)); // key 4 should be added
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testEdgeCaseCapacityZero(CacheService<Integer, String> cache) {
        CacheService<Integer, String> zeroCapacityCache = new LFUTreeMapCache<>(0);
        zeroCapacityCache.put(1, "one");

        assertNull(zeroCapacityCache.get(1)); // No item should be added
    }

    @ParameterizedTest
    @MethodSource("negativeCapacityCacheProvider")
    public void testEdgeCaseNegativeCapacity(CacheService<Integer, String> cache) {
        CacheService<Integer, String> negativeCapacityCache = new LFUTreeMapCache<>(-1);
        negativeCapacityCache.put(1, "one");

        assertNull(negativeCapacityCache.get(1)); // No item should be added
    }

    @ParameterizedTest
    @MethodSource("cacheProvider")
    public void testEdgeCaseUpdateFrequencyOnGet(CacheService<Integer, String> cache) {
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // Access key 1 to increase its frequency
        cache.get(1);

        // Add a new item, which should evict the least frequently used item (key 2 or 3)
        cache.put(4, "four");

        assertNotNull(cache.get(1)); // key 1 should still be present
        assertNotNull(cache.get(4)); // key 4 should be added
        assertTrue(cache.get(2) == null || cache.get(3) == null); // One of key 2 or 3 should be evicted
    }
}
