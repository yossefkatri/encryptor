package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileEncryptorTest {
    EncryptionAlgorithmImpl encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    FileEncryptor tested = new FileEncryptor(encryptionAlgorithm);

    @Test
    void encryptFile() {
    }

    @Test
    void decryptFile() {
    }

    @Test
    void encrypt() {
        IKey key = new IntKey(5);
        when(encryptionAlgorithm.encryptChar('s',key)).thenReturn('d');
        assertEquals("ddddd",tested.encrypt("sssss",key));
    }

    @Test
    void decrypt() {
        IKey key = new IntKey(5);
        when(encryptionAlgorithm.decryptChar('s',key)).thenReturn('d');
        assertEquals("ddddd",tested.decrypt("sssss",key));
    }
}