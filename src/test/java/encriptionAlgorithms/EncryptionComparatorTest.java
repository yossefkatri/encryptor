package encriptionAlgorithms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncryptionComparatorTest {
    EncryptionComparator encryptionComparator = new EncryptionComparator();

    @Test
    void compareSmaller() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(3);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        assertEquals(-1,encryptionComparator.compare(encryptionAlgorithm1,encryptionAlgorithm2));
    }
    @Test
    void compareBigger() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(3);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        assertEquals(1,encryptionComparator.compare(encryptionAlgorithm2,encryptionAlgorithm1));
    }

    @Test
    void compareEqual() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(4);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        assertEquals(0,encryptionComparator.compare(encryptionAlgorithm2,encryptionAlgorithm1));
    }
}