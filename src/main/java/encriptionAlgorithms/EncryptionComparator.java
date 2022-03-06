package encriptionAlgorithms;

import java.util.Comparator;

public class EncryptionComparator<T> implements Comparator<IEncryptionAlgorithm<T>> {
    @Override
    public int compare(IEncryptionAlgorithm<T> o1, IEncryptionAlgorithm<T> o2) {
        return o1.getKeyStrength()-o2.getKeyStrength();
    }
}
