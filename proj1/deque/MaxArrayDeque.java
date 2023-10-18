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

    @Override
    // 可以使用继承的对比方法, 也可以自己建立一个方法, 而覆盖上层的方法.
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this || (o instanceof MaxArrayDeque && ((MaxArrayDeque) o).max() == max() )) {
            return true;
        }
        return false;
    }



}
