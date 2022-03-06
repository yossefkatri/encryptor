package keys;

public class BasicKey<T> implements IKey<T> {
    public final T key;

    //constructor
    public BasicKey(T key) {
        this.key = key;
    }
    //getter
    public T getKey() {
        return key;
    }
    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
