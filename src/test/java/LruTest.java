import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.LruCache;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LruTest {
    @Test
    void cannotCreateCacheInInvalidSize(){
        assertThrows(IllegalArgumentException.class, () -> new LRU(0));
    }

    @Test
    void putInsertsNewValue() {
        LRU cache = new LRU(2);
        int hash = cache.put("A");

        assertEquals("A", cache.get(hash));
    }

    @Test
    void putReturnsHashCode(){
        LRU cache = new LRU(2);
        Object value = "Hello";
        assertEquals(value.hashCode(), cache.put(value));
    }

    @Test
    void putUpdateRecentness() {
        LRU cache = new LRU(2);

        cache.put("A");
        cache.put("B");
        cache.put("A");
        cache.put("C");

        assertThrows(NoSuchElementException.class, () -> cache.get("B".hashCode()));
    }

    @Test
    void getThrowsIfNotFound(){
        LRU cache = new LRU(2);
        assertThrows(NoSuchElementException.class, () -> cache.get(99));
    }


}
