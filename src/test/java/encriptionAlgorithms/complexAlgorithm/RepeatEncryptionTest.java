package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RepeatEncryptionTest {
    EncryptionAlgorithmImpl encryptionAlgorithmMocking = mock(EncryptionAlgorithmImpl.class);
    RepeatEncryption tested =new RepeatEncryption(encryptionAlgorithmMocking,6);

    @Test
    void encryptChar() {
        IntKey key = new IntKey(6);
        when(encryptionAlgorithmMocking.encryptChar('d', key)).thenReturn('f');
        when(encryptionAlgorithmMocking.encryptChar('f', key)).thenReturn('g');
        when(encryptionAlgorithmMocking.encryptChar('g', key)).thenReturn('d');

        char encryptedChar = tested.encryptChar('d',key);

        char[] arr = {'d','f','g'};
        char res = arr[((tested.times)%3)];

        assertEquals(res,encryptedChar);
    }

    @Test
    void decryptChar() {
        IntKey key = new IntKey(6);
        when(encryptionAlgorithmMocking.decryptChar('d', key)).thenReturn('f');
        when(encryptionAlgorithmMocking.decryptChar('f', key)).thenReturn('g');
        when(encryptionAlgorithmMocking.decryptChar('g', key)).thenReturn('d');

        char decryptChar = tested.decryptChar('d',key);

        char[] arr = {'d','f','g'};
        char res = arr[((tested.times)%3)];

        assertEquals(res, decryptChar);
    }
}