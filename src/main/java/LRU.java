import java.util.HashMap;
import java.util.NoSuchElementException;

public class LRU {

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

    public LRU(int capacity) {
        if (capacity <= 0){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;

    }

    public int put(Object value){
        int hash = value.hashCode();

        if (map.containsKey(hash)){
            Node node = (Node) map.get(hash);
            moveToHead(node);
            return hash;
        }

        Node newNode = new Node(value);
        addToHead(newNode);
        map.put(hash, newNode);

        if (map.size() > capacity){
            map.remove(tail.value.hashCode());
            removeTail();
        }

        return hash;
    }

    private void removeTail() {
        if (tail == null) return;
        if (tail.prev != null){
            tail.prev.next = null;
        }else{
            head = null;
        }
        tail = tail.prev;
    }

    private void addToHead(Node node) {
        node.prev = null;
        node.next = head;
        if (head != null) head.prev = node;
        head = node;
        if (tail == null) tail = node;
    }

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

    public String get(int hash) {
        Node node = (Node) map.get(hash);
        if (node == null) throw new NoSuchElementException();
        moveToHead(node);
        return node.value.toString();
    }
}
