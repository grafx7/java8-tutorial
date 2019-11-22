package polimorfizm;

import java.util.Objects;

public class A {

    private String name;
    private int age;

    public A(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void process(){
        method1();
        method2();
    }

    private void method1() {
        System.out.println("A.method1");
    }

    protected void method2() {
        System.out.println("A.method2");
    }


}
