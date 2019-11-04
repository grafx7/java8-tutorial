package tutorials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/*
* https://annimon.com/article/2778
*/
public class TutorialStreams {
    public static void main(String[] args) {
        String[] arr = {"Geeks", "for", "Geeks"};
        List<String> list = Arrays.stream(arr)
                .filter(s -> s.length() <= 2)
                .collect(Collectors.toList());

        IntStream.of(120, 410, 85, 32, 314, 12)
                .filter(x -> x < 300)
                .map(x -> x + 11)
                .limit(3)
                .forEach(System.out::println);


        Stream<String> stream = list.stream();
        stream.forEach(System.out::println);
        //stream.filter(s -> s.contains("Stream API"));     //Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        //stream.forEach(System.out::println);

        List<String> list1 = Arrays.asList("Geeks", "for", "Geeks");
        List list2 = list1.parallelStream()
                .filter(x -> x.length() > 3)
                .map(x -> x + "1")
                .collect(Collectors.toList());
        System.out.println(list2);          //[Geeks1, Geeks1]

        int sum = IntStream.range(0, 10)
                .parallel()
                .map(x -> x * 10)
                .sum();
        System.out.println(sum);            //450

        /*
        Это код Шрёдингера. Он может нормально выполниться и показать 1000000, может выполниться и показать 869877,
        а может и упасть с ошибкой Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 332 at java.util.ArrayList.add(ArrayList.java:459).
        */
        /*final List<Integer> ints = new ArrayList<>();
        IntStream.range(0, 1000000)
                .parallel()
                .forEach(i -> ints.add(i));
        System.out.println(ints.size());*/

        /*

        */
        List<String> l1 = new ArrayList(Arrays.asList("one", "two"));
        Stream<String> sl = l1.stream();
        l1.add("three");
        String str1 = sl.collect(joining(" "));
        System.out.println(str1);

        //Источники
        //empty()
        Stream.empty().forEach(System.out::println);            //вывода нет

        //of(T value)
        //of(T... values)
        Arrays.asList(1, 2, 3).stream().forEach(System.out::println);
        //or
        Stream.of(1, 2, 3).forEach(System.out::println);

        //ofNullable(T t)       --- java 9
        /*String str = Math.random() > 0.5 ? "I'm feeling lucky" : null;
        Stream.ofNullable(str)
                .forEach(System.out::println);*/

        //generate(Supplier s)
        Stream.generate(() -> 6)
                .limit(6)
                .forEach(System.out::println);      //6, 6, 6, 6, 6, 6

        //iterate​(T seed, UnaryOperator f)
        Stream.iterate(2, x -> x + 6)
                .limit(6)
                .forEach(System.out::println);      // // 2, 8, 14, 20, 26, 32

        Stream.iterate(1, x -> x * 2)
                .limit(6)
                .forEach(System.out::println);      // 1, 2, 4, 8, 16, 32

        //iterate​(T seed, Predicate hasNext, UnaryOperator f)      --- java 9
        /*
        Stream.iterate(2, x -> x < 25, x -> x + 6)
                .forEach(System.out::println);      // 2, 8, 14, 20
        */

        //concat(Stream a, Stream b)
        Stream.concat(
                Stream.of(1, 2, 3),
                Stream.of(4, 5, 6))
                .forEach(System.out::println);      // 1, 2, 3, 4, 5, 6


        //builder()
        Stream.Builder<Integer> sIntegerBuilder = Stream.<Integer>builder()
                .add(0)
                .add(1);
        for (int i = 2; i <= 8; i+=2){
            sIntegerBuilder.accept(i);
        }
        sIntegerBuilder
                .add(9)
                .add(10)
                .build()
                .forEach(System.out::println);          // 0, 1, 2, 4, 6, 8, 9, 10

        //IntStream.range​(int startInclusive, int endExclusive)
        //LongStream.range​(long startInclusive, long endExclusive)
        IntStream.range(0, 10).forEach(System.out::println);        // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        LongStream.range(-10L, -5L).forEach(System.out::println);   // -10, -9, -8, -7, -6

        //IntStream.rangeClosed​(int startInclusive, int endInclusive)
        //LongStream.range​Closed(long startInclusive, long endInclusive)
        IntStream.rangeClosed(0, 5).forEach(System.out::println);   // 0, 1, 2, 3, 4, 5
        LongStream.range(-8L, -5L).forEach(System.out::println);    // -8, -7, -6, -5

        // filter​(Predicate predicate)
        Stream.of(1, 2, 3)
                .filter(x -> x == 10)
                .forEach(System.out::print);                    // Вывода нет, так как после фильтрации стрим станет пустым

        Stream.of(120, 410, 85, 32, 314, 12)
                .filter(x -> x > 100)
                .forEach(System.out::println);                  // 120, 410, 314

        IntStream.range(2, 9)
                .filter(x -> x % 3 == 0)
                .forEach(System.out::println);                  // 3, 6

        System.out.println(2 % 3);
        System.out.println(9 % 3);

        //map​(Function mapper)
        Stream.of("3", "4", "5")
                .map(Integer::parseInt)
                .map(x -> x + 10)
                .forEach(System.out::println);              // 13, 14, 15

        Stream.of(120, 410, 85, 32, 314, 12)
                .map(x -> x + 11)
                .forEach(System.out::println);              // 131, 421, 96, 43, 325, 23


        Stream.of("10", "11", "32")
                        .map(x -> Integer.parseInt(x, 16))
                        .forEach(System.out::println);      //16, 17, 50
        System.out.println(Integer.parseInt("32", 16));

        //flatMap​(Function<T, Stream<R>> mapper)







    }
}
