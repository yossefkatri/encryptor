package keys;

public class DoubleKey<T> implements IKey<T> {
    final IKey<T> key1;
    final IKey<T> key2;

    //constructor
    public DoubleKey(IKey<T> key1, IKey<T> key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
    //getters
    public IKey<T> getKey1() {
        return key1;
    }
    public IKey<T> getKey2() {
        return key2;
    }

    @Override
    public String toString() {
        return key1 +"\n"+ key2;
    }
}
