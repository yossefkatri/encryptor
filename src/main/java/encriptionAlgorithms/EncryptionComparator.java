package encriptionAlgorithms;

import java.util.Comparator;

public class EncryptionComparator implements Comparator<IEncryptionAlgorithm> {
    @Override
    public int compare(IEncryptionAlgorithm o1, IEncryptionAlgorithm o2) {
        return o1.getKeyStrength()-o2.getKeyStrength();
    }
}
