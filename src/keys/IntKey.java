package keys;

import interfaces.IKey;

public class IntKey implements IKey {
    public int key;

    //constructor
    public IntKey(int key) {
        this.key = key;
    }
    //getter
    public int getKey() {
        return key;
    }
    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
