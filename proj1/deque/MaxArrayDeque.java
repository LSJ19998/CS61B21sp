package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for(T e: this) {
            if (c.compare(e, max) > 0) {
                max = e;
            }
        }
        return max;
    }

    public T max() {
        return max(comparator);
    }

}
