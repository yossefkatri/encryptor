package encriptionAlgorithms.basicAlgorithms;

import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XorEncryptionTest {

    final XorEncryption testedEncryption = XorEncryption.getInstance();

    @Test
    void encryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('d',new IntKey(54));
        assertEquals('R',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('ג',new IntKey(345));
        assertEquals('ҋ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('R',new IntKey(54));
        assertEquals('d',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('ҋ',new IntKey(345));
        assertEquals('ג',result);
    }
}