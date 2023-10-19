package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements  Iterable<T>, Deque<T> {

    private int size;
    private int nextFirst;
    private int nextLast;

    private int aNext(int pos, int ele) {
        return (pos + items.length + ele) % items.length;
    }

    private int addFirst() {
        return aNext(nextFirst, -1);
    }

    private int subFirst() {
        return aNext(nextFirst, 1);
    }

    private int subLast() {
        return aNext(nextLast, -1);
    }

    private int addLast() {
        return aNext(nextLast, 1);
    }


    private static final double FACTOR = 0.25;

    private T[] items;
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 0;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // 減少元素的時候的情況
    private void subResizing() {
        if (size <= 8) {
            return;
        }
        int newSize = (int) (items.length * FACTOR);
        T[] newItems = (T[]) new Object[newSize];
        // nf需要subFirst, addFirst在开头相减为特殊情况
        if (nextFirst == items.length - 1) {
            System.arraycopy(items, 0, newItems, 0, size);
        } else if (nextFirst > nextLast) {
            System.arraycopy(items, subFirst(), newItems, 0, items.length - nextFirst - 1);
            System.arraycopy(items, 0, newItems, items.length - nextFirst - 1, nextLast);
        } else if (nextFirst < nextLast) {
            System.arraycopy(items, subFirst(), newItems, 0, nextLast - nextFirst - 1);
        }
        items = newItems;
        nextLast = size;
        nextFirst = items.length - 1;
    }

    // 增加元素的情况
    private void addResizing() {
        int newSize = (int) (items.length * (1 + FACTOR));
        T[] newItems = (T[]) new Object[newSize];
        // 三种情况:
        // 1. 在开始
        if (nextLast == 0) {
            System.arraycopy(items, 1, newItems, 0, size);
        }
        // 2.在末尾
        if (nextLast == items.length - 1) {
            System.arraycopy(items, 0, newItems, 0, size);
        }
        // 3. 在中间
        else {
            System.arraycopy(items, addLast(), newItems, 0, items.length - addLast());
            System.arraycopy(items, 0, newItems, items.length -addLast(), nextFirst);
        }
        items = newItems;
        nextLast = size;
        nextFirst = items.length - 1;
    }

    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            addResizing();
        }
        items[nextFirst] = item;
        nextFirst = addFirst();
        size += 1;
    }

    public void addLast(T item) {
        if (nextFirst == nextLast) {
            addResizing();
        }

        items[nextLast] = item;
        nextLast = addLast();
        size += 1;
    }

    public T removeFirst() {
        // 減少的特殊情況
        if (size <= 0) {
            return null;
        }
        if (size <= items.length * FACTOR) {
            // 減少元素
            subResizing();
        }
        nextFirst = subFirst();
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return item;
    }

    public T removeLast() {
        // 減少的特殊情況
        if (size <= 0) {
            return null;
        }
        if (size <= items.length * FACTOR) {
            subResizing();
        }
        nextLast = subLast();
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return item;
    }

    public  T get(int index) {
        return items[aNext(subFirst(), index)];
    }

    public Iterator iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator<T> implements Iterator<T> {

        public int index;
        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public T next() {
            T item = (T) items[aNext(subFirst(), index)];
            index += 1;
            return item;
        }
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

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o instanceof ArrayDeque || o instanceof LinkedListDeque) {
            ArrayDeque ad = (ArrayDeque) o;
            if (ad.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (ad.get(i) != get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
