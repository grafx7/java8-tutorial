package main;

import Interface.Converter;
import Interface.Formula;
import Interface.PersonFactory;

import java.lang.reflect.Field;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        //Default Methods for Interfaces
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(16));

        //Lambda expressions
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));

        //Functional Interfaces
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);

        //Method and Constructor References
        Converter<String, Integer> converter1 = Integer::valueOf;
        Integer converted1 = converter1.convert("123");
        System.out.println(converted1);

        Something something = new Something();
        Converter<String, String> converter2 = something::startWith;
        String converted2 = converter2.convert("Java");
        System.out.println(converted2);

        PersonFactory<Person> personPersonFactory = Person::new;
        Person person = personPersonFactory.create("Peter", "Parker");
        System.out.println(person.firstName);
        System.out.println(person.lastName);

        //Lambda Scopes
        final int num = 1;
        Converter<Integer, String> stringConverter = (from) -> String.valueOf(from + num);
        System.out.println(stringConverter.convert(2));

        //Accessing Default Interface Methods
        //The following code does not compile:
        //Formula formula1 = (a) -> sqrt(a * 100);

        //Predicates
        Predicate<String> predicate = (s) -> s.length() > 0;
        predicate.test("foo");
        predicate.negate().test("foo");

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        //Functions
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        System.out.println(toInteger.apply("1234"));
        System.out.println(backToString.apply("123"));

        //Suppliers
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();

        //Consumers
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName );
        greeter.accept(new Person("Luke", "Sky"));

        //Comparators
        Comparator<Person> comparator1 = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        Comparator<Person> comparator2 = Comparator.comparing(p -> p.firstName);

        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");

        comparator1.compare(p1, p2);
        comparator2.reversed().compare(p1, p2);

        //Optionals
        Optional<String> optional = Optional.of("bam");
        optional.isPresent();               //true
        optional.get();                     //"bam"
        optional.orElse("fallback"); //"bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0))); //"b"

        //Streams
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        //Filter
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);      // "aaa2", "aaa1"

        //Sorted
        stringCollection
                .stream()
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);     //"aaa1", "aaa2"

        //Map
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);      //"DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"

        //Match
        boolean anyStartsWithA =
                stringCollection
                .stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);         //true

        boolean allStartWithA =
                stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartWithA);          //false

        boolean noneStartWithA =
                stringCollection
                .stream()
                .noneMatch((s) -> s.startsWith("a"));
        System.out.println(noneStartWithA);         //true

        //Count
        long startWithB =
                stringCollection
                .stream()
                .filter((s) -> s.startsWith("b"))
                .count();
        System.out.println(startWithB);         //3

        //Reduce
        Optional<String> reduced =
                stringCollection
                .stream()
                .sorted()
                .reduce((s1, s2) -> s1 + "#" + s2);

        reduced.ifPresent(System.out::println);

        //Parallel Streams
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i< max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }

        //Sequential Sort
        long t0 = System.nanoTime();

        long count = values
                .stream()
                .sorted()
                .count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
        //sequential sort took: 542 ms

        //Parallel Sort
        long t2 = System.nanoTime();

        long count1 = values
                .parallelStream()
                .sorted()
                .count();
        System.out.println(count1);

        long t3 = System.nanoTime();

        long millis1 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
        System.out.println(String.format("parallel sort took: %d ms", millis1));
        // parallel sort took: 261 ms

        //Maps
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++){
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));

        map.computeIfPresent(3, (num1, val) -> val + num1);
        System.out.println(map.get(3)); //val33

        map.computeIfPresent(9, (num2, val) -> null);
        System.out.println(map.containsKey(9)); //false

        map.computeIfAbsent(23, num3 -> "val" + num3);
        System.out.println(map.containsKey(23)); //true
        map.computeIfAbsent(3, num4 -> "bam");
        System.out.println(map.get(3)); //val33

        map.remove(3, "val3");
        System.out.println(map.get(3)); //val33

        map.remove(3, "val33");
        System.out.println(map.get(3)); //null

        System.out.println(map.getOrDefault(42, "not found")); // not found

        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.get(9); // val9

        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9); //val9concat

        //Date API

        //Clock
        Clock clock = Clock.systemDefaultZone();
        long millis4 = clock.millis();
        System.out.println(millis4);

        Instant instant = clock.instant();          //legacy java.util.Date
        System.out.println(instant);

        //Timezones
        System.out.println(ZoneId.getAvailableZoneIds());
        // prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");

        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());
        // ZoneRules[currentStandardOffset=+01:00]
        // ZoneRules[currentStandardOffset=-03:00]

        //LocalTime
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));        //false

        long hourBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println(hourBetween);        // -2
        System.out.println(minutesBetween);     // -179

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late); // 23:59:59

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);

        //LocalDate
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);    // FRIDAY

        DateTimeFormatter germanFormatter1 =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter1);
        System.out.println(xmas);   // 2014-12-24

        //LocalDateTime
        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek1 = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek1);      // WEDNESDAY

        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439

        Instant instant1 = sylvester
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Date legacyDate = Date.from(instant1);
        System.out.println(legacyDate);     // Wed Dec 31 23:59:59 CET 2014

        DateTimeFormatter formatter =
                DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);     // Nov 03, 2014 - 07:13
    }

    static int getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        return ((Object[]) dataField.get(l)).length;
    }
}
