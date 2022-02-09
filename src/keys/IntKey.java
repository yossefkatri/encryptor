package keys;

public class IntKey implements IKey {
    public final int key;

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
