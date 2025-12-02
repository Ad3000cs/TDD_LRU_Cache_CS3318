import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.LruCache;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link LRUCache} class.
 *
 * <p>
 *     These tests follow a Test Driven Development (TDD) approach.
 *     Each test describes a single behavior of the LRU cache that must be
 *     externally observable through state testing.
 * </p>
 */
public class LruTest {

    /**
     * Ensures that the cache cannot be created with an invalid capacity.
     * this verifies constructor input validation.
     */

    @Test
    void cannotCreateCacheInInvalidSize(){
        assertThrows(IllegalArgumentException.class, () -> new LRUCache(0));
    }

    /**
     * Verifies that inserting a new value stores it in the cache and allows
     * retrieval by hash code.
     */
    @Test
    void putInsertsNewValue() {
        LRUCache cache = new LRUCache(2);
        int hash = cache.put("A");

        assertEquals("A", cache.get(hash));
    }

    /**
     * Ensures that put(value) returns the hash code of the object
     * being inserted or updated.
     */

    @Test
    void putReturnsHashCode(){
        LRUCache cache = new LRUCache(2);
        Object value = "Hello";
        assertEquals(value.hashCode(), cache.put(value));
    }

    /**
     * Tests that inserting the same object again updates its recentness,
     * and verifies that the least recently used value is evicted when
     * capacity is exceeded.
     */

    @Test
    void putUpdateRecentness() {
        LRUCache cache = new LRUCache(2);

        cache.put("A");
        cache.put("B");
        cache.put("A");
        cache.put("C");

        assertThrows(NoSuchElementException.class, () -> cache.get("B".hashCode()));
    }

    /**
     * Ensures that attempting to retrieve a non-existent hash code results
     * in a NoSuchElementException being thrown
     */

    @Test
    void getThrowsIfNotFound(){
        LRUCache cache = new LRUCache(2);
        assertThrows(NoSuchElementException.class, () -> cache.get(99));
    }

    /**
     * Verifies that a successful get(hash) call updates the recentness
     * of the item, ensuring it is not evicted prematurely when the cache
     * reaches maximum capacity.
     */

    @Test
    void getUpdatesRecentness() {
        LRUCache cache = new LRUCache(2);

        int makeHeadA = cache.put("A");
        int makeHeadB = cache.put("B");

        cache.get(makeHeadA);

        cache.put("C");

        assertThrows(NoSuchElementException.class, () -> cache.get(makeHeadB));
    }

}
