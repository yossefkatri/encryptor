package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import exceptions.InvalidEncryptionKeyException;
import utils.keys.BasicKey;
import utils.keys.DoubleKey;
import utils.keys.IKey;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KeyManagerTest {
    EncryptionAlgorithmImpl<?> encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    KeyManager keyManager = new KeyManager(encryptionAlgorithm);
    @Test
    void buildKey() {
        IKey<Integer> key = keyManager.buildKey(Arrays.asList(1,2,3,4));
        assertEquals("DoubleKey{DoubleKey{BasicKey{1}, BasicKey{2}}, DoubleKey{BasicKey{3}, BasicKey{4}}}",key.type());
    }

    @Test
    void checkKeysDifferentNumberOfKeys() {
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(3);
        assertThrows(InvalidEncryptionKeyException.class,()->keyManager.checkKeys(Arrays.asList(1,2,3,4,5)));
    }
    @Test
    void checkKeysKeyOutOfRange() {
        when(encryptionAlgorithm.getKeyStrength()).thenReturn(1);
        assertThrows(InvalidEncryptionKeyException.class, ()->keyManager.checkKeys(Arrays.asList(123)));
    }

    @Test
    void getKeys() {
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(4);
        IKey<Integer> key = keyManager.getKeys();
        assertEquals(key.toString().split("\n").length,4);
    }

    @Test
    void saveKey(){
        Path userDirectory = Paths.get("src\\main\\java\\outputFiles");
        IKey<Integer> key= new DoubleKey<>(new BasicKey<>(3),new BasicKey<>(5));
        try {
            keyManager.saveKey(userDirectory,key);
            Charset charset = StandardCharsets.UTF_8;
            List<String> lines = Files.readAllLines(Paths.get(userDirectory.toString(),"key.txt"), charset);
            assertEquals(String.join("\n",lines),"3\n5");
        } catch (IOException e) {
            fail();
        }
    }
}