import org.junit.jupiter.api.Test;

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
}
