package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import utils.keys.DoubleKey;
import utils.keys.BasicKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DoubleEncryptionTest {

    @SuppressWarnings("unchecked")
    final EncryptionAlgorithmImpl<Integer> encryptionAlgorithmMocking =(EncryptionAlgorithmImpl<Integer>) mock(EncryptionAlgorithmImpl.class);
    final DoubleEncryption<Integer> testedEncryption = new DoubleEncryption<>(encryptionAlgorithmMocking);

    @Test
    void encryptChar() {
        try {
            DoubleKey<Integer> key = new DoubleKey<>(new BasicKey<>(4), new BasicKey<>(6));
            when(encryptionAlgorithmMocking.encryptChar('d', key.getKey1())).thenReturn('f');
            when(encryptionAlgorithmMocking.encryptChar('f', key.getKey2())).thenReturn('g');

            char encryptedChar = testedEncryption.encryptChar('d', key);
            assertEquals('g', encryptedChar);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    void decryptChar() {
        try {

            DoubleKey<Integer> key = new DoubleKey<>(new BasicKey<>(4), new BasicKey<>(6));
            when(encryptionAlgorithmMocking.decryptChar('d', key.getKey2())).thenReturn('f');
            when(encryptionAlgorithmMocking.decryptChar('f', key.getKey1())).thenReturn('g');

            char encryptedChar = testedEncryption.decryptChar('d', key);
            assertEquals('g', encryptedChar);
        }catch (Exception ex)
        {
            ex.printStackTrace();
            fail();
        }
    }
}