package tutorials;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Runner {
    public static void main(String[] args) {
        List<List<MyObject>> list = new ArrayList<List<MyObject>>();

        List<MyObject> myObjects1 = new ArrayList<MyObject>();
        myObjects1.add(new MyObject("a", 10));
        //myObjects1.add(new MyObject(null, 0));
        myObjects1.add(null);
        myObjects1.add(new MyObject("c", 30));

        List<MyObject> myObjects2 = new ArrayList<MyObject>();
        myObjects2.add(new MyObject("a", 10));
        myObjects2.add(new MyObject("b", 20));
        myObjects2.add(new MyObject("c", 30));

        list.add(myObjects1);
        list.add(myObjects2);

        List<MyObject> stringList = list
                .stream()
                .flatMap(
                        innerList -> innerList
                                .stream())
                                //.filter(a -> a.getAge() != 0)
                                .collect(Collectors.toList());
        stringList.forEach(System.out::println);

        List<List<MyObject>> newList = list.stream()
                //.filter(x -> x.stream().noneMatch(y -> y.getAge() == 0))
                .collect(Collectors.toList());

        System.out.println();

        List<List<MyObject>> newList1 = asStream(list.iterator())
                .filter(x -> asStream(x.iterator()).noneMatch(y -> y == null))
                .collect(Collectors.toList());

        System.out.println();

    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}
