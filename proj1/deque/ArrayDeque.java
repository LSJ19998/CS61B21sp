package deque;

public class ArrayDeque<T> {

    private int size;
    private int nextFirst;
    private int nextLast;

    private int next(int pos, int ele) {
        return (pos + items.length + ele) % items.length;
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

    public void resizing() {
        int new_size = (int)(size * (1 + FACTOR));
        T[] new_items = (T []) new Object[new_size];
        System.arraycopy(items, 0, new_items, 0, size);
        items = new_items;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resizing();
        }
        items[nextFirst] = item;
        nextFirst = next(nextFirst, -1);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resizing();
        }
        items[nextLast] = item;
        nextLast = next(nextLast, 1);
        size += 1;
    }

    public void removeFirst(T item) {
        if (size <= items.length * FACTOR) {
            resizing();
        }

    }
}
