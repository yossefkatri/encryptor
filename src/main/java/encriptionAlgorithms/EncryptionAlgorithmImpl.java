package encriptionAlgorithms;

public abstract class EncryptionAlgorithmImpl<T> implements IEncryptionAlgorithm<T> {
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public int numberOfKeys;

}
