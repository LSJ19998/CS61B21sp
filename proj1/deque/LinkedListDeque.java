package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private Node sentinel;

    private static class Node<T> {
        private Node prev;

        private Node next;

        private T item;
        public Node(Node p, T i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 思考一下问题所在
    public  void addFirst(T item) {
        // 后指的是第一个元素
        Node add_node = new Node(sentinel, item, sentinel.next);
        sentinel.next = add_node;
        add_node.next.prev = add_node;
        size += 1;
    }


    public void addLast(T item) {
        // 前指的是第一个元素
        Node new_node = new Node(sentinel.prev, item, sentinel);
        sentinel.prev = new_node;
        new_node.prev.next = new_node;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node removed_node = sentinel.next;
        removed_node.next.prev = sentinel;
        sentinel.next = removed_node.next;
        size -= 1;
        return (T) removed_node.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node removed_node = sentinel.prev;
        removed_node.prev.next = removed_node.next;
        removed_node.next.prev = removed_node.prev;
        size -= 1;
        return (T) removed_node.item;
    }


    public T get(int i) {
        if (i < 0 || i >= size) {
            return null;
        }
        Node node = this.sentinel;
        for (int j = 0; j <= i; j += 1) {
            node = node.next;
        }
        return (T) node.item;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive_helper(index, sentinel.next);
    }

    public T getRecursive_helper(int index, Node node) {
        if (index  == 0) {
            return (T) node.item;
        }
        return getRecursive_helper(index - 1, (Node) node.next);
    }


    public void printDeque() {
        if (size == 0) {
            return;
        }
        StringBuilder res = new StringBuilder("");
        Node node = sentinel.next;
        for (T e: this) {
            res.append(e + " ");
            node = node.next;
        }
        System.out.println(res);
        System.out.println("");
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass() ) {
            return false;
        }
        if (o == this) {
            return true;
        }

        // 一个新的问题
        LinkedListDeque linko = (LinkedListDeque) o;
        if (linko.size() != size) {
            return false;
        }

        for(int i = 0; i < size; i += 1) {
            if (!get(i).equals(linko.get(i))) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator<>();
    }

    public class LinkedListDequeIterator<T> implements Iterator<T> {
        private int index;
        private Node node;

        public LinkedListDequeIterator() {
            index = 0;
            node = sentinel.next;
        }
        public boolean hasNext() {
            return index < size;
        }

        public T next() {
            T item = (T) node.item;
            node = node.next;
            index += 1;
            return item;
        }
    }

}
