package encriptionAlgorithms;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertTrue( encryptionComparator.compare(encryptionAlgorithm1, encryptionAlgorithm2)<0);
    }

    @Test
    void compareBigger() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(3);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        assertTrue( encryptionComparator.compare(encryptionAlgorithm2, encryptionAlgorithm1)>0);
    }

    @Test
    void compareEqual() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(4);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        assertEquals(0, encryptionComparator.compare(encryptionAlgorithm2, encryptionAlgorithm1));
    }

    @Test
    void sortTest() {
        IEncryptionAlgorithm encryptionAlgorithm1 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm2 = mock(IEncryptionAlgorithm.class);
        IEncryptionAlgorithm encryptionAlgorithm3 = mock(IEncryptionAlgorithm.class);
        when(encryptionAlgorithm1.getKeyStrength()).thenReturn(6);
        when(encryptionAlgorithm2.getKeyStrength()).thenReturn(4);
        when(encryptionAlgorithm3.getKeyStrength()).thenReturn(5);

        when(encryptionAlgorithm1.toString()).thenReturn("encryptionAlgorithm1");
        when(encryptionAlgorithm2.toString()).thenReturn("encryptionAlgorithm2");
        when(encryptionAlgorithm3.toString()).thenReturn("encryptionAlgorithm3");

        List<IEncryptionAlgorithm> encryptionAlgorithmList = new ArrayList<IEncryptionAlgorithm>(){{add(encryptionAlgorithm1);
                                                                                                    add(encryptionAlgorithm2);
                                                                                                    add(encryptionAlgorithm3);}};
        encryptionAlgorithmList.sort(encryptionComparator);

        assertEquals("[encryptionAlgorithm2, encryptionAlgorithm3, encryptionAlgorithm1]",encryptionAlgorithmList.toString());
    }

}