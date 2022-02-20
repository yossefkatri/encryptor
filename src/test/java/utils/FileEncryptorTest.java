package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import keys.IKey;
import keys.IntKey;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileEncryptorTest {
    final EncryptionAlgorithmImpl encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    final FileEncryptor tested = new FileEncryptor(encryptionAlgorithm);

    @Test
    void encryptFileSourceFileNonExist() {
        Exception exception = assertThrows(FileNotFoundException.class,()->tested.encryptFile(Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input2.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles")));
        assertEquals(FileNotFoundException.class,exception.getClass());
    }

    @Test
    void decryptFileSourceFileNonExist() {
        Exception exception = assertThrows(FileNotFoundException.class,()->tested.decryptFile(Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input2.txt"),
                Paths.get("C:\\Users\\Yossef\\Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt")));
        assertEquals(FileNotFoundException.class,exception.getClass());
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