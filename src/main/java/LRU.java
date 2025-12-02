import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * A cache implementing the Least Recently Used (LRU) replacement policy.
 *
 * <p>
 *     The cache holds a fixed number of Object values. Each stored object is
 *     indexed by its {@link Object#hashCode()} value, which is used as the lookup
 *     key. Both insertion and access operations update the "recentness" of an item
 *     so that the most recently used item is promoted to the front of the cache,
 *     while the least recently used item becomes a candidate for removal when the cache
 *     is full.
 * </p>
 * Internally the cache uses:
 * <ul>
 *     <li> A {@code HashMap<Integer, Node>} for O(1) lookup by hash code </li>
 *     <li> A doubly-linked list to maintain usage order </li>
 * </ul>
 * The head of the list represents the most recently used item, and the tail
 * represents the least recently used.
 */


public class LRU {

    /**
     * A node in the doubly-linked list representing a cached value.
     * Each node contains a reference to an Object and links to the
     * previous and next nodes in the usage order.
     */

    private static class Node {
        Object value;
        Node prev, next;

        Node(Object value) {
            this.value = value;
        }
    }

    private final int capacity;
    private HashMap<Integer, Object> map = new HashMap<>();
    private Node head, tail;

    /**
     * Creates an LRU cache with a specified positive capacity.
     *
     * @param capacity the maximum number of items the cache may hold
     * @throws IllegalArgumentException if {@code capacity <= 0}
     */

    public LRU(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;

    }

    /**
     * Inserts an object into the cache or updates its recentness if it already exists.
     * <p>
     *     If the object is already present (identified by its hash code), it is promoted
     *     to the most recently used position. If it is not present, it is added to the
     *     cache. If the insertion results in the cache exceeding its capacity, the
     *     least recently used item (the tail of the list) is removed.
     * </p>
     *
     * @param value the Object to insert or update
     * @return the hash code of the inserted or updated object
     */

    public int put(Object value){
        int hash = value.hashCode();

        // If already present, promote to most recent
        if (map.containsKey(hash)){
            Node node = (Node) map.get(hash);
            moveToHead(node);
            return hash;
        }

        // Insert new code
        Node newNode = new Node(value);
        addToHead(newNode);
        map.put(hash, newNode);

        // Enforce capacity: remove LRU if necessary
        if (map.size() > capacity){
            map.remove(tail.value.hashCode());
            removeTail();
        }

        return hash;
    }


    /**
     * Removes the least recently used node (the tail) from the list.
     * Pointer adjustments ensure the list remains consistent. If the
     * cache becomes empty, both head and tail are set to null.
     */

    private void removeTail() {
        if (tail == null) return;
        if (tail.prev != null){
            tail.prev.next = null;
        }else{
            head = null;
        }
        tail = tail.prev;
    }

    /**
     * Inserts a new node at the head of the list, making it the most recently
     * used item.
     *
     * @param node the node to add
     */

    private void addToHead(Node node) {
        node.prev = null;
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = node;
    }

    /**
     * Moves an existing node to the head of the doubly-linked list,
     * updating pointers to preserve the correct LRU ordering.
     *
     * @param node the node too promote to most recent
     */

    private void moveToHead(Node node) {
        if (node == head) return;

        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;

        if (node == tail) tail = node.prev;

        node.prev = null;
        node.next = head;

        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = node;
    }


    /**
     * Retrieves an object from the cache by its hash code.
     *
     * <p>
     *     If the object is found, it is promoted to the most recently used position.
     *     If no such object exists, a {@link NoSuchElementException} is thrown.
     * </p>
     *
     * @param hash the hash code of the object to retrieve
     * @return the object associated with the given hash code
     * @throws NoSuchElementException if no such object is present
     */

    public String get(int hash) {
        Node node = (Node) map.get(hash);
        if (node == null) throw new NoSuchElementException();
        moveToHead(node);
        return node.value.toString();
    }
}
