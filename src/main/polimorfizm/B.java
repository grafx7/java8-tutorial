package polimorfizm;

public class B extends A {
    public B(String a1, int i) {
        super(a1, i);
    }

    public void method1() {
        System.out.println("B.method1");
    }

    public void method2() {
        System.out.println("B.method2");
    }
}
