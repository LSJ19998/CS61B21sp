package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private Node sentinel;

    private static class Node<T> {
        private Node prev;

        private Node next;

        private T item;
         Node(Node p, T i, Node n) {
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



    // 思考一下问题所在
    public  void addFirst(T item) {
        // 后指的是第一个元素
        Node addNode = new Node(sentinel, item, sentinel.next);
        sentinel.next = addNode;
        addNode.next.prev = addNode;
        size += 1;
    }


    public void addLast(T item) {
        // 前指的是第一个元素
        Node newNode = new Node(sentinel.prev, item, sentinel);
        sentinel.prev = newNode;
        newNode.prev.next = newNode;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node removedNode = sentinel.next;
        removedNode.next.prev = sentinel;
        sentinel.next = removedNode.next;
        size -= 1;
        return (T) removedNode.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node removedNode = sentinel.prev;
        removedNode.prev.next = removedNode.next;
        removedNode.next.prev = removedNode.prev;
        size -= 1;
        return (T) removedNode.item;
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
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node node) {
        if (index  == 0) {
            return (T) node.item;
        }
        return getRecursiveHelper(index - 1, (Node) node.next);
    }


    public void printDeque() {
        if (size == 0) {
            return;
        }
        StringBuilder res = new StringBuilder("");
        for (T e: this) {
            res.append(e + " ");
        }
        System.out.println(res);
        System.out.println("");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof deque.LinkedListDeque)) {
            return false;
        }

        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }

        LinkedListDeque<?> lld = (LinkedListDeque<?>) o;
        if (lld.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (lld.get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator<>();
    }

    private class LinkedListDequeIterator<T> implements Iterator<T> {
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
