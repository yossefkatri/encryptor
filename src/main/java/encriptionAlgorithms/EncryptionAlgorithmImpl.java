package encriptionAlgorithms;

public abstract class EncryptionAlgorithmImpl implements IEncryptionAlgorithm {
    public int getNumberOfKeys() {
        return numberOfKeys;
    }

    public int numberOfKeys;

}
