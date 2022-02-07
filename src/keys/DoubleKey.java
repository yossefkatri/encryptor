package keys;

import interfaces.IKey;

public class DoubleKey implements IKey {
    IKey key1;
    IKey key2;

    //constructor
    public DoubleKey(IKey key1, IKey key2) {
        this.key1 = key1;
        this.key2 = key2;
    }
    //getters
    public IKey getKey1() {
        return key1;
    }
    public IKey getKey2() {
        return key2;
    }

    @Override
    public String toString() {
        return key1 +"\n"+ key2;
    }
}
