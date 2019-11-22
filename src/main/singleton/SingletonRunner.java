package singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
/*
https://habr.com/ru/post/129494/
 */
public class SingletonRunner {
    public static void main(String[] args)
            throws CloneNotSupportedException, InterruptedException, IOException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {

        // construct new singleton with Multithreading
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(LazySingleton.getInstance().hashCode());
            }).start();
        }
        Thread.sleep(100);

        IncorectSingleton incorectSingleton = IncorectSingleton.getInstance();
        System.out.println("Initial: " + incorectSingleton.hashCode());

        // construct new singleton with Clone
        IncorectSingleton clonedIncorrectSingleton = incorectSingleton.clone();
        System.out.println("Cloned: " + clonedIncorrectSingleton.hashCode());

        // construct new singleton with Serializable
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("singleton.txt")); ObjectInputStream in = new ObjectInputStream(new FileInputStream("singleton.txt"))) {
            objectOutputStream.writeObject(incorectSingleton);
            IncorectSingleton serializedSingleton = (IncorectSingleton) in.readObject();
            System.out.println("Serialized: " + serializedSingleton.hashCode());
        }

        // construct new singleton with Reflection
        Constructor constructor = IncorectSingleton.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        IncorectSingleton reflectionSingleton = (IncorectSingleton) constructor.newInstance();
        System.out.println("reflection: " + reflectionSingleton.hashCode());

        // with Classloading
        ClassLoader classLoader11 = Thread.currentThread().getContextClassLoader();
        Class<?> sclass = (classLoader11.loadClass(IncorectSingleton.class.getCanonicalName()));
        constructor = sclass.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        IncorectSingleton classLoadingSingleton = (IncorectSingleton) constructor.newInstance();
        System.out.println("class loading: " + classLoadingSingleton.hashCode());
    }
}
