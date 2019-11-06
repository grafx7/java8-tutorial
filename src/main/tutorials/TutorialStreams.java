package tutorials;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.InputStream;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.*;

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
        for (int i = 2; i <= 8; i += 2) {
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
        Stream.of(2, 3, 0, 1, 3)
                .flatMapToInt(x -> IntStream.range(0, x))
                .forEach(System.out::println);              // 0, 1, 0, 1, 2, 0, 0, 1, 2

        System.out.println("-//-");
        Stream.of(1, 2, 3, 4, 5, 6)
                .flatMap(x -> {
                    switch (x % 3) {
                        case 0:
                            return Stream.of(x, x * x, x * x * 2);
                        case 1:
                            return Stream.of(x);
                        case 2:
                        default:
                            return Stream.empty();
                    }
                })
                .forEach(System.out::println);              // 1, 3, 9, 18, 4, 6, 36, 72

        System.out.println(1 % 3);  //1
        System.out.println(2 % 3);  //2
        System.out.println(3 % 3);  //0
        System.out.println(4 % 3);  //1
        System.out.println(5 % 3);  //2
        System.out.println(6 % 3);  //0

        //limit​(long maxSize)
        Stream.of(120, 410, 85, 32, 314, 12)
                .limit(4)
                .forEach(System.out::println);              // 120, 410, 85, 32

        Stream.of(120, 410, 85, 32, 314, 12)
                .limit(2)
                .limit(5)
                .forEach(System.out::println);              // 120, 410

        Stream.of(19)
                .limit(0)
                .forEach(System.out::println);              // Вывода нет

        //skip​(long n)
        Stream.of(5, 10)
                .skip(40)
                .forEach(System.out::println);              //Вывода нет

        Stream.of(120, 410, 85, 32, 314, 12)
                .skip(2)
                .forEach(System.out::println);              // 85, 32, 314, 12

        IntStream.range(0, 10)
                .limit(5)
                .skip(3)
                .forEach(System.out::println);              // 3, 4

        IntStream.range(0, 10)
                .skip(5)
                .limit(3)
                .skip(1)
                .forEach(System.out::println);

        //sorted​()
        //sorted​(Comparator comparator)
        IntStream.range(0, 100000000)
                .sorted()
                .limit(3)
                .forEach(System.out::println);          //0, 1, 2

        /*IntStream.concat(
                IntStream.range(0, 100000000),
                IntStream.of(-1, -2))
                .sorted()
                .forEach(System.out::println);*/        // Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

        Stream.of(120, 410, 85, 32, 314, 12)
                .sorted(Comparator.naturalOrder())      //naturalOrder
                .forEach(System.out::println);          // 12, 32, 85, 120, 314, 410

        Stream.of(120, 410, 85, 32, 314, 12)
                .sorted(Comparator.reverseOrder())      //reverseOrder
                .forEach(System.out::println);          // 12, 32, 85, 120, 314, 410

        //distinct​()
        IntStream.concat(
                IntStream.range(2, 5),
                IntStream.range(0, 4))
                .distinct()
                .forEach(System.out::println);          //2, 3, 4, 0, 1

        //peek​(Consumer action)
        Stream.of(0, 3, 0, 0, 5)
                .peek(x -> System.out.format("before distinct: %d%n", x))
                .distinct()
                .peek(x -> System.out.format("after distinct: %d%n", x))
                .map(x -> x * x)
                .forEach(x -> System.out.format("after map: %d%n", x));
        // before distinct: 0
        // after distinct: 0
        // after map: 0
        // before distinct: 3
        // after distinct: 3
        // after map: 9
        // before distinct: 1
        // after distinct: 1
        // after map: 1
        // before distinct: 5
        // before distinct: 0
        // before distinct: 5
        // after distinct: 5
        // after map: 25

        //takeWhile​(Predicate predicate)       ---java 9
        /*Stream.of(1, 2, 3, 4, 2, 5)
                .takeWhile(x -> x < 3)              //Это как limit, только не с числом, а с условием.
                .forEach(System.out::println);*/    // 1, 2

        /*IntStream.range(2, 7)
                .takeWhile(x -> x != 5)             //Оператор подобен skip, только работает по условию.
                .forEach(System.out::println);*/    // 2, 3, 4

        //dropWhile​(Predicate predicate)       ---java 9
        /*Stream.of(1, 2, 3, 4, 2, 5)
                .dropWhile(x -> x >= 3)             //Оператор подобен skip, только работает по условию.
                .forEach(System.out::println);      // 1, 2, 3, 4, 2, 5

        Stream.of(1, 2, 3, 4, 2, 5)
                .dropWhile(x -> x < 3)              //Оператор подобен skip, только работает по условию.
                .forEach(System.out::println);*/    // 3, 4, 2, 5

        //boxed() - Converts a primitive stream to an object stream.
        DoubleStream.of(0.1, Math.PI)
                .boxed()
                .map(Object::getClass)
                .forEach(System.out::println);         // class java.lang.Double, class java.lang.Double

        //void forEach​(Consumer action)
        Stream.of(120, 410, 85, 32, 314, 12)
                .forEach(x -> System.out.format("%s, ", x));        // 120, 410, 85, 32, 314, 12

        //void forEachOrdered​(Consumer action)
        IntStream.range(0, 100000)
                .parallel()
                .filter(x -> x % 10000 == 0)
                .map(x -> x / 10000)
                .forEach(System.out::print);              // 5, 6, 7, 3, 4, 8, 0, 9, 1, 2

        IntStream.range(0, 100000)
                .parallel()
                .filter(x -> x % 10000 == 0)
                .map(x -> x / 10000)
                .forEachOrdered(System.out::print);       // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9

        //long count​()
        long count = IntStream.range(0, 10)
                .flatMap(x -> IntStream.range(0, x))
                .count();
        System.out.println(count);                         // 45

        System.out.println(
                IntStream.rangeClosed(-3, 6)
                        .count()                            //10
        );

        //R collect​(Collector collector)
        List<Integer> list3 = Stream.of(1, 2, 3)
                .collect(Collectors.toList());                      // list3: [1, 2, 3]

        String s = Stream.of(1, 2, 3)
                .map(String::valueOf)
                .collect(Collectors.joining("-", "<", ">"));        //s: <1-2-3>

        //R collect​(Supplier supplier, BiConsumer accumulator, BiConsumer combiner)
        List<String> list4 = Stream.of("a", "b", "c", "d")
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);               // list : ["a", "b", "c", "d"]

        //Object[] toArray​()
        //A[] toArray​(IntFunction<A[]> generator)
        String[] elements = Stream.of("a", "b", "c", "d")
                .toArray(String[]::new);                        // elements: ["a", "b", "c", "d"]

        //T reduce​(T identity, BinaryOperator accumulator)
        //U reduce​(U identity, BiFunction accumulator, BinaryOperator combiner)
        int sum1 = Stream.of(1, 2, 3, 4, 5)
                .reduce(10, (acc, x) -> acc + x);

        //Optional reduce​(BinaryOperator accumulator)
        Optional<Integer> result = Stream.<Integer>empty()
                .reduce((acc, x) -> acc + x);
        System.out.println(result.isPresent());             // false

        Optional<Integer> sum2 = Stream.of(1, 2, 3, 4, 5)
                .reduce((acc, x) -> acc + x);
        System.out.println(sum2.get());             // 15

        int product = IntStream.range(1, 10)
                .filter(x -> x % 4 == 0)
                .reduce((acc, x) -> acc * x)
                .getAsInt();
        System.out.println(product);                        //32

        //Optional min​(Comparator comparator)
        //Optional max​(Comparator comparator)
        int min = Stream.of(20, 11, 45, 78, 13)
                .min(Integer::compare).get();               // min: 11

        int max = Stream.of(20, 11, 45, 78, 13)
                .max(Integer::compare).get();               // max: 78

        //Optional findAny​()   - Возвращает первый попавшийся элемент стрима. В параллельных стримах это может быть действительно любой элемент.
        //Optional findFirst​() - Гарантированно возвращает первый элемент стрима, даже если стрим параллельный.
        int anySeq = IntStream.range(4, 65536)
                .findAny()
                .getAsInt();                                // 4

        int firstSeq = IntStream.range(4, 65536)
                .findFirst()
                .getAsInt();                                // 4

        int anyparallel = IntStream.range(4, 65536)
                .parallel()
                .findAny()
                .getAsInt();                                // 32770

        int firstParallel = IntStream.range(4, 65536)
                .parallel()
                .findFirst()
                .getAsInt();                                // 4

        //boolean allMatch​(Predicate predicate)
        boolean result1 = Stream.of(1, 2, 3, 4, 5)
                .allMatch(x -> x <= 7);                     // result: true

        boolean result2 = Stream.of(1, 2, 3, 4, 5)
                .allMatch(x -> x < 3);                      // result: false

        //boolean anyMatch​(Predicate predicate)
        boolean result3 = Stream.of(1, 2, 3, 4, 5)
                .anyMatch(x -> x == 3);                     // result: true

        boolean result4 = Stream.of(1, 2, 3, 4, 5)
                .anyMatch(x -> x == 8);

        // boolean noneMatch​(Predicate predicate)
        boolean result5 = Stream.of(1, 2, 3, 4, 5)
                .noneMatch(x -> x == 9);                    // result: true

        boolean result6 = Stream.of(1, 2, 3, 4, 5)
                .noneMatch(x -> x == 3);                    // result: false

        //OptionalDouble average​()
        double result7 = IntStream.range(2, 16)
                .average()
                .getAsDouble();                             //8.5

        // sum()
        long result8 = LongStream.range(2, 16)
                .sum();                                     // result: 119

        //IntSummaryStatistics summaryStatistics()
        LongSummaryStatistics stats = LongStream.range(2, 16)
                .summaryStatistics();
        System.out.format("  count: %d%n", stats.getCount());
        System.out.format("    sum: %d%n", stats.getSum());
        System.out.format("average: %.1f%n", stats.getAverage());
        System.out.format("    min: %d%n", stats.getMin());
        System.out.format("    max: %d%n", stats.getMax());
        //   count: 14
        //     sum: 119
        // average: 8,5
        //     min: 2
        //     max: 15

        //Методы Collectors

        //toCollection​(Supplier collectionFactory)
        Deque<Integer> deque = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Set<Integer> set = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        //toMap​(Function keyMapper, Function valueMapper)
        Map<Integer, Integer> map1 = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toMap(
                        Function.identity(),
                        Function.identity()
                ));                                                     // {1=1, 2=2, 3=3, 4=4, 5=5}

        Map<Integer, String> map2 = Stream.of(1, 2, 3)
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> String.format("%d * 2 = %d", i, i * 2)
                ));                                                     // {1="1 * 2 = 2", 2="2 * 2 = 4", 3="3 * 2 = 6"}

        Map<Character, String> map3 = Stream.of(50, 54, 55)
                .collect(Collectors.toMap(
                        i -> (char) i.intValue(),
                        i -> String.format("<%d>", i)
                ));                                                     //{'2'="<50>", '6'="<54>", '7'="<55>"}

        //toMap​(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction)
        Map<Integer, String> map4 = Stream.of(50, 55, 69, 20, 19, 52)
                .collect(Collectors.toMap(
                        i -> i % 5,
                        i -> String.format("<%d>", i),
                        (a, b) -> String.join(", ", a, b)
                ));                                                     // {0="<50>, <55>, <20>", 2="<52>", 4="<64>, <19>"}

        //toMap​(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction, Supplier mapFactory)
        Map<Integer, String> map5 = Stream.of(50, 55, 69, 20, 19, 52)
                .collect(Collectors.toMap(
                        i -> i % 5,
                        i -> String.format("<%d>", i),
                        (a, b) -> String.join(", ", a, b),
                        LinkedHashMap::new
                ));                                                     // {0=<50>, <55>, <20>, 4=<69>, <19>, 2=<52>}


        //Всё то же самое, что и toMap, только работаем с ConcurrentMap.
        //toConcurrentMap​(Function keyMapper, Function valueMapper)
        //toConcurrentMap​(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction)
        //toConcurrentMap​(Function keyMapper, Function valueMapper, BinaryOperator mergeFunction, Supplier mapFactory)

        //collectingAndThen​(Collector downstream, Function finisher)
        List<Integer> list5 = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
        System.out.println(list5.getClass());                           // class java.util.Collections$UnmodifiableRandomAccessList

        //collectingAndThen​(Collector downstream, Function finisher)
        //Собирает элементы с помощью указанного коллектора, а потом применяет к полученному результату функцию.
        List<String> list6 = Stream.of("a", "b", "c", "d")
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Function.identity(),
                                s4 -> s4 + s4),
                        map -> map.entrySet().stream()))
                .map(e -> e.toString())
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList));
        System.out.println(list6);                          // [a=aa, b=bb, c=cc, d=dd]

        //joining​()
        //joining​(CharSequence delimiter)
        //joining​(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
        String s1 = Stream.of("a", "b", "c", "d")
                .collect(Collectors.joining());
        System.out.println(s1);                             // abcd

        String s2 = Stream.of("a", "b", "c", "d")
                .collect(Collectors.joining("-"));
        System.out.println(s2);                             // a-b-c-d

        String s3 = Stream.of("a", "b", "c", "d")
                .collect(Collectors.joining(" -> ", "[ ", " ]"));
        System.out.println(s3);                             // [ a -> b -> c -> d ]

        //summingInt​(ToIntFunction mapper)
        //summingLong​(ToLongFunction mapper)
        //summingDouble​(ToDoubleFunction mapper)
        //Коллектор, который преобразовывает объекты в int/long/double и подсчитывает сумму.
        //
        //averagingInt​(ToIntFunction mapper)
        //averagingLong​(ToLongFunction mapper)
        //averagingDouble​(ToDoubleFunction mapper)
        //Аналогично, но со средним значением.
        //
        //summarizingInt​(ToIntFunction mapper)
        //summarizingLong​(ToLongFunction mapper)
        //summarizingDouble​(ToDoubleFunction mapper)
        //Аналогично, но с полной статистикой.
        Integer sum3 = Stream.of("1", "2", "3", "4")
                .collect(Collectors.summingInt(Integer::parseInt));
        System.out.println(sum3);                                       // 10

        Double average = Stream.of("1", "2", "3", "4")
                .collect(Collectors.averagingInt(Integer::parseInt));
        System.out.println(average);                                    // 2.5

        DoubleSummaryStatistics stats1 = Stream.of("1.1", "2.34", "3.14", "4.04")
                .collect(Collectors.summarizingDouble(Double::parseDouble));
        System.out.println(stats1);                                     // DoubleSummaryStatistics{count=4, sum=10.620000, min=1.100000, average=2.655000, max=4.040000}

        //counting​()
        Long count1 = Stream.of("1", "2", "3", "4")
                .collect(Collectors.counting());
        System.out.println(count1);                                     // 4

        //filtering​(Predicate predicate, Collector downstream)
        //mapping​(Function mapper, Collector downstream)
        //flatMapping​(Function downstream)
        //reducing​(BinaryOperator op)
        //reducing​(T identity, BinaryOperator op)
        //reducing​(U identity, Function mapper, BinaryOperator op)
        //Специальная группа коллекторов, которая применяет операции filter, map, flatMap и reduce. filtering​ и flatMapping​ появились в Java 9.
        /*List<Integer> ints = Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.filtering(
                        x -> x % 2 == 0,
                        Collectors.toList()));                      // 2, 4, 6

        String s1 = Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.filtering(
                        x -> x % 2 == 0,
                        Collectors.mapping(
                                x -> Integer.toString(x),
                                Collectors.joining("-")
                        )
                ));                                                 // 2-4-6

        String s2 = Stream.of(2, 0, 1, 3, 2)
                .collect(Collectors.flatMapping(
                        x -> IntStream.range(0, x).mapToObj(Integer::toString),
                        Collectors.joining(", ")
                ));         */                                        // 0, 1, 0, 0, 1, 2, 0, 1

        int value = Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.reducing(
                        0, (a, b) -> a + b
                ));
        //or
        int value1 = Stream.of(1, 2, 3, 4, 5, 6).reduce(0, (a, b) -> a + b);
        System.out.println(value);                                  //21

        String s4 = Stream.of(1, 2, 3, 4, 5, 6)
                .collect(Collectors.reducing(
                        "", x -> Integer.toString(x), (a, b) -> a + b
                ));
        //or
        String s5 = Stream.of(1, 2, 3, 4, 5, 6).map(x -> Integer.toString(x)).reduce("", (a, b) -> a + b);
        System.out.println(s4);                                      // 123456

        //minBy​(Comparator comparator)
        //maxBy​(Comparator comparator)
        Optional<String> min1 = Stream.of("ab", "c", "defgh", "ijk", "l")
                .collect(Collectors.minBy(Comparator.comparing(String::length)));
        min1.ifPresent(System.out::println);                             // c

        Optional<String> max1 = Stream.of("ab", "c", "defgh", "ijk", "l")
                .collect(Collectors.maxBy(Comparator.comparing(String::length)));
        max1.ifPresent(System.out::println);                             // defgh

        //groupingBy​(Function classifier)
        //groupingBy​(Function classifier, Collector downstream)
        //groupingBy​(Function classifier, Supplier mapFactory, Collector downstream)
        //Группирует элементы по критерию, сохраняя результат в Map. Вместе с представленными выше агрегирующими коллекторами, позволяет гибко собирать данные. Подробнее о комбинировании в разделе Примеры.
        //
        //groupingByConcurrent​(Function classifier)
        //groupingByConcurrent​(Function classifier, Collector downstream)
        //groupingByConcurrent​(Function classifier, Supplier mapFactory, Collector downstream)
        //Аналогичный набор методов, только сохраняет в ConcurrentMap.

        Map<Integer, List<String>> map = Stream.of("ab", "c", "def", "gh", "ijk", "l", "mnop")
                .collect(Collectors.groupingBy(String::length));
        map.entrySet().forEach(System.out::println);
        //1=[c, l]
        //2=[ab, gh]
        //3=[def, ijk]
        //4=[mnop]

        Map<Integer, String> map6 = Stream.of("ab", "c", "def", "gh", "ijk", "l", "mnop")
                .collect(Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(
                                String::toUpperCase,
                                Collectors.joining()
                        )
                ));
        map6.entrySet().forEach(System.out::println);
//        1=CL
//        2=ABGH
//        3=DEFIJK
//        4=MNOP

        Map<Integer, List<String>> map7 = Stream.of("ab", "c", "def", "gh", "ijk", "l", "mnop")
                .collect(Collectors.groupingBy(
                        String::length,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                String::toUpperCase,
                                Collectors.toList())
                ));
        map7.entrySet().forEach(System.out::println);
//        2=[AB, GH]
//        1=[C, L]
//        3=[DEF, IJK]
//        4=[MNOP]

        //partitioningBy​(Predicate predicate)
        //partitioningBy​(Predicate predicate, Collector downstream)
        Map<Boolean, List<String>> map8 = Stream.of("ab", "c", "def", "gh", "ijk", "l", "mnop")
                .collect(Collectors.partitioningBy(s6 -> s6.length() <= 2));
        map8.entrySet().forEach(System.out::println);
        //false=[def, ijk, mnop]
        //true=[ab, c, gh, l]

        Map<Boolean, String> map9 = Stream.of(
                "ab", "c", "def", "gh", "ijk", "l", "mnop")
                .collect(Collectors.partitioningBy(
                        s7 -> s7.length() <= 2,
                        Collectors.mapping(
                                String::toUpperCase,
                                Collectors.joining())
                ));
        map9.entrySet().forEach(System.out::println);
        //false=DEFIJKMNOP
        //true=ABCGHL


        //Collector
        Stream.of(1, 2, 3, 1, 9, 2, 5, 3, 4, 8, 2)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(LinkedHashSet::new),
                        ArrayList::new));                           // 1 2 3 9 5 4 8


        Stream.of(1, 2, 3, 1, 9, 2, 5, 3, 4, 8, 2)
                .map(String::valueOf)
                .collect(partitioningByUniqueness(Collectors.joining("-")))
                .forEach((isUnique, str) -> System.out.format("%s: %s%n", isUnique ? "unique" : "repetitive", str));
        // repetitive: 1-2-3-2
        // unique: 1-2-3-9-5-4-8

        //Spliterator


        System.out.println();


    }

    /**
     * Создание собственного коллектора
     * Статическая функция, а внутри используется Collector.of.
     */
    public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningByUniqueness() {
        return Collector.<T, Map.Entry<List<T>, Set<T>>, Map<Boolean, List<T>>>of(
                // supplier
                () -> new AbstractMap.SimpleImmutableEntry<>(
                        new ArrayList<T>(), new LinkedHashSet<>()),
                // accumulator
                (c, e) -> {
                    if (!c.getValue().add(e)) {
                        c.getKey().add(e);
                    }
                },
                // combiner
                (c1, c2) -> {
                    c1.getKey().addAll(c2.getKey());
                    for (T e : c2.getValue()) {
                        if (!c1.getValue().add(e)) {
                            c1.getKey().add(e);
                        }
                    }
                    return c1;
                },
                // finisher
                c -> {
                    Map<Boolean, List<T>> result = new HashMap<>(2);
                    result.put(Boolean.FALSE, c.getKey());
                    result.put(Boolean.TRUE, new ArrayList<>(c.getValue()));
                    return result;
                });
    }

    /**
     * Первую реализацию метода partitioningByUniqueness без аргументов можно заменить на:
     */
    public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningByUniqueness1() {
        return partitioningByUniqueness(Collectors.toList());
    }

    /**
    * Коллектор, который принимают ещё один коллектор и зависят от него.
    * Например, можно будет складывать элементы не только в List,
    * но и в любую другую коллекцию (Collectors.toCollection), либо в строку (Collectors.joining).
    */
    public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningByUniqueness(
            Collector<? super T, A, D> downstream) {
        class Holder<A, B> {
            final A unique, repetitive;
            final B set;

            Holder(A unique, A repetitive, B set) {
                this.unique = unique;
                this.repetitive = repetitive;
                this.set = set;
            }
        }
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BinaryOperator<A> downstreamCombiner = downstream.combiner();
        BiConsumer<Holder<A, Set<T>>, T> accumulator = (t, element) -> {
            A container = t.set.add(element) ? t.unique : t.repetitive;
            downstreamAccumulator.accept(container, element);
        };
        return Collector.<T, Holder<A, Set<T>>, Map<Boolean, D>>of(
                () -> new Holder<>(
                        downstream.supplier().get(),
                        downstream.supplier().get(),
                        new HashSet<>()),
                accumulator,
                (t1, t2) -> {
                    downstreamCombiner.apply(t1.repetitive, t2.repetitive);
                    t2.set.forEach(e -> accumulator.accept(t1, e));
                    return t1;
                },
                t -> {
                    Map<Boolean, D> result = new HashMap<>(2);
                    result.put(Boolean.FALSE, downstream.finisher().apply(t.repetitive));
                    result.put(Boolean.TRUE, downstream.finisher().apply(t.unique));
                    t.set.clear();
                    return result;
                });
    }
}