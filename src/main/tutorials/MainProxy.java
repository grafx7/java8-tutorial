package tutorials;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

public class MainProxy {
    public static void main(String[] args) {
        InvocationHandler handler = new MyProxy(new Integer(5));
        Class[] classes = new Class[]{Comparable.class, Callable.class};
        Comparable proxy = (Comparable)Proxy.newProxyInstance(null, classes, handler);
        System.out.println(proxy.compareTo(3));
        Integer a = 128;
        Integer b = 128;
        Integer c = -128;
        Integer d = -128;
        System.out.println(a == b);
        System.out.println(c == d);
    }

    static class MyProxy implements InvocationHandler {

        Object target;

        public MyProxy (Object target){
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(args);
            return method.invoke(target, args);
        }
    }
}
