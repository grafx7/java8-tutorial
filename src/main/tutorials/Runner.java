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
        int [] arr1 = new int[] {0,4,5,3,8,0,2,8,3,0,0,4,4,0};
        int[] arr2 = sort1(arr1);
        for (int a: arr2){
            System.out.print(a);
        }

    }

    public static  int[] sort(int[] arr1){
        int n = arr1.length;
        int arr2[] = new int[n];

        int indexLeft = 0;
        int indexRight = n - 1;
        for (int elem : arr1){
            if (elem == 0){
                arr2[indexRight--] = 0;
            } else {
                arr2[indexLeft++] = elem;
            }
        }
        assert indexLeft > indexRight;
        return arr2;
   }

   public static int[] sort1(int[] arr) {
       int lastIndex = arr.length - 1;
       for (int i = arr.length -1; i >= 0; i--) {
           if (arr[i] == 0) {
               if (i == lastIndex) {
                   lastIndex--;
               } else {
                   arr[i] = arr[lastIndex];
                   arr[lastIndex--] = 0;
               }
           }
       }
       return arr;
   }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }
}
