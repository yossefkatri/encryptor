package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import utils.keys.BasicKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class RepeatEncryptionTest {
    final EncryptionAlgorithmImpl<Integer> encryptionAlgorithmMocking = mock(EncryptionAlgorithmImpl.class);
    final RepeatEncryption<Integer> testedEncryption = new RepeatEncryption<>(encryptionAlgorithmMocking, 6);

    @Test
    void encryptChar() {
        BasicKey<Integer> key = new BasicKey<>(6);
        when(encryptionAlgorithmMocking.encryptChar('d', key)).thenReturn('f');
        when(encryptionAlgorithmMocking.encryptChar('f', key)).thenReturn('g');
        when(encryptionAlgorithmMocking.encryptChar('g', key)).thenReturn('d');

        char encryptedChar = testedEncryption.encryptChar('d', key);

        char[] arr = {'d', 'f', 'g'};
        char res = arr[((testedEncryption.times) % 3)];

        assertEquals(res, encryptedChar);
    }

    @Test
    void decryptChar() {
        BasicKey<Integer> key = new BasicKey<>(6);
        when(encryptionAlgorithmMocking.decryptChar('d', key)).thenReturn('f');
        when(encryptionAlgorithmMocking.decryptChar('f', key)).thenReturn('g');
        when(encryptionAlgorithmMocking.decryptChar('g', key)).thenReturn('d');

        char decryptChar = testedEncryption.decryptChar('d', key);

        char[] arr = {'d', 'f', 'g'};
        char res = arr[((testedEncryption.times) % 3)];

        assertEquals(res, decryptChar);
    }
}