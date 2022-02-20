package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import keys.DoubleKey;
import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DoubleEncryptionTest {
    final EncryptionAlgorithmImpl encryptionAlgorithmMocking = mock(EncryptionAlgorithmImpl.class);
    final DoubleEncryption tested =new DoubleEncryption(encryptionAlgorithmMocking);

    @Test
    void encryptChar() {
        DoubleKey key = new DoubleKey(new IntKey(4),new IntKey(6));
        when(encryptionAlgorithmMocking.encryptChar('d', key.getKey1())).thenReturn('f');
        when(encryptionAlgorithmMocking.encryptChar('f', key.getKey2())).thenReturn('g');

        char encryptedChar = tested.encryptChar('d',key);
        assertEquals('g',encryptedChar);
    }

    @Test
    void decryptChar() {
        DoubleKey key = new DoubleKey(new IntKey(4),new IntKey(6));
        when(encryptionAlgorithmMocking.decryptChar('d', key.getKey2())).thenReturn('f');
        when(encryptionAlgorithmMocking.decryptChar('f', key.getKey1())).thenReturn('g');

        char encryptedChar = tested.decryptChar('d',key);
        assertEquals('g',encryptedChar);
    }
}