import java.util.HashMap;
import java.util.NoSuchElementException;

public class LRU {

    private HashMap<Integer, Object> map = new HashMap<>();

    public LRU(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }
    }

    public int put(Object value){
        int hash = value.hashCode();
        map.put(hash, value);
        return hash;
    }

    public String get(int hash) {
        if (!map.containsKey(hash)) throw new NoSuchElementException();
            return map.get(hash).toString();
    }
}
