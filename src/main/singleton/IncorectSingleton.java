package singleton;

import java.io.Serializable;

public class IncorectSingleton implements Serializable, Cloneable {
    private static IncorectSingleton instance;

    private IncorectSingleton() {

    }

    public static IncorectSingleton getInstance() {
        if (instance == null) {
            instance = new IncorectSingleton();
        }
        return instance;
    }

    @Override public IncorectSingleton clone() throws CloneNotSupportedException {
        return (IncorectSingleton) super.clone();
    }
}
