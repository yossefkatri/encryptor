package encriptionAlgorithms.basicAlgorithms;

import utils.keys.BasicKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XorEncryptionTest {

    final XorEncryption testedEncryption = XorEncryption.getInstance();

    @Test
    void encryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('d', new BasicKey<>(54));
        assertEquals('R',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('ג', new BasicKey<>(345));
        assertEquals('ҋ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('R', new BasicKey<>(54));
        assertEquals('d',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('ҋ', new BasicKey<>(345));
        assertEquals('ג',result);
    }
}