package tutorials;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorTask {
    public static void main(String[] args) {
        Iterator<Integer> iterator = Arrays.asList(1, 3, 5, 7, 9, 12, 17).iterator();
        for (Iterator<Integer> it = iterator; it.hasNext(); ) {
            Integer i = it.next();


        }
        print(m(iterator, 10));
    }

    private static <T extends Number & Comparable<T>> Iterator<T> m(Iterator<T> iter1, T num) {
        Iterator<T> iter2 = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                if (!iter1.hasNext()) {
                    return false;
                }

                // TODO here
                return false;
            }

            @Override
            public T next() {
                T next = iter1.next();
                if (next.compareTo(num) > 0) {
                    throw new NoSuchElementException();
                }

                return next;
            }
        };
        return iter2;
    }

    private static <T extends Number & Comparable<T>> void print(Iterator<T> iterator) {
        while (iterator.hasNext()) {
            System.out.print(iterator.next().toString() + "  ");
        }
        System.out.println("=====END=====");
    }
}
