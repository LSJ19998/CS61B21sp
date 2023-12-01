package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;
        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!

    private static final double DEFAULTFACTOR = 0.75;
    private static final int DEFAULTSIZE = 16;

    private double loadFactor;

    private int initialSize;

    private int size = 0;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULTSIZE, DEFAULTFACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULTFACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.initialSize = initialSize;
        this.loadFactor = maxLoad;
        this.buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedHashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i += 1) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int hashCode(K key) {
        return hashCode(key, buckets);
    }


    private int hashCode(K key, Collection<Node>[] table) {
        int keyHashcode = key.hashCode();
        return Math.floorMod(keyHashcode, table.length);
    }

    private Node getNode(K key) {
        int index = hashCode(key);
        for (Node node: buckets[index]) {
            if (node.key == key) {
                return node;
            }
        }
        return null;
    }

    private boolean outFactor() {
        return size / initialSize > loadFactor;
    }

    private void resize(int capacity) {
        Collection<Node>[] newBuckets = createTable(capacity);
        Iterator iterator = new NodeIterator();

        while (iterator.hasNext()) {
            Node node = (Node) iterator.next();
            int index = hashCode(node.key, newBuckets);
            newBuckets[index].add(node);
        }
        initialSize = capacity;
        buckets = newBuckets;
    }

    @Override
    public Iterator<K> iterator() {
        return new KeyIterator<>();
    }


    private class NodeIterator<Node> implements Iterator {
        private int nth = 0;
        private Iterator iterator = buckets[nth].iterator();

        @Override
        public boolean hasNext() {
            if (iterator != null && iterator.hasNext()) {
                return true;
            }
            nth += 1;
            if (nth == buckets.length) {
                return false;
            }
            iterator  = buckets[nth].iterator();
            // 递归
            return hasNext();
        }

        @Override
        public Node next() {
            return (Node) iterator.next();
        }
    }


    private class KeyIterator<K> implements  Iterator {
        private final Iterator iterator = new NodeIterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public K next() {
            return  (K)  ((Node) iterator.next()).key;
        }
    }


    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<>();
        for (K k: this) {
            set.add(k);
        }
        return set;
    }

    @Override
    public void clear() {
        size = 0;
        this.buckets = createTable(DEFAULTSIZE);
    }

    @Override
    public void put(K key, V value) {
        int keyhashCode = hashCode(key);
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }
        node = createNode(key, value);
        buckets[keyhashCode].add(node);
        size += 1;

        if (outFactor()) {
            resize(buckets.length * 2);
        }
    }


    @Override
    public boolean containsKey(K key) {
        int ith = hashCode(key);
        for (Node node: buckets[ith]) {
            if (node.key.equals(key)) {
                return  true;
            }
        }
        return false;
    }


    @Override
    public V get(K key) {
        int ith = hashCode(key);
        for (Node node: buckets[ith]) {
            K nk = node.key;
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }


    @Override
    public int size() {
        return size;
    }



    @Override
    public V remove(K key) {
        int index = hashCode(key);
        Node node = getNode(key);
        if (node != null) {
            size -= 1;
            buckets[index].remove(node);
            return node.value;
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        int index = hashCode(key);
        Node node = getNode(key);
        if (node == null || !node.value.equals(value)) {
            return null;
        }
        size -= 1;
        buckets[index].remove(node);
        return node.value;
    }
}
