package polimorfizm;

public class Main {
    public static void main(String[] args) {

        B obj1 = (B) new A("a1", 1);
        A obj2 = new B("a1", 1);
        obj2.process();

        B obj3 = new B("a1", 1);
        obj3.process();
        obj3.method1();

        A a1 = new A("a1", 1);
        A a2 = new A("a1", 1);
        System.out.println(a1.hashCode());
        System.out.println(a2.hashCode());
        System.out.println(a1.hashCode() == a2.hashCode());
    }
}
