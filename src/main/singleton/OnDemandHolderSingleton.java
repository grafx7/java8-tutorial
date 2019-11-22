package singleton;

public class OnDemandHolderSingleton {
    public static class SingletonHolder {
        public static final OnDemandHolderSingleton HOLDER_INSTANCE = new OnDemandHolderSingleton();
    }

    public static OnDemandHolderSingleton getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
