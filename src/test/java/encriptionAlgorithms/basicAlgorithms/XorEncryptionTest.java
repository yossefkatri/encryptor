package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.basicAlgorithms.XorEncryption;
import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XorEncryptionTest {

    XorEncryption tested = new XorEncryption();

    @Test
    void encryptCharAsciiTest() {
        char result = tested.encryptChar('d',new IntKey(54));
        assertEquals('R',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = tested.encryptChar('ג',new IntKey(345));
        assertEquals('ҋ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = tested.encryptChar('R',new IntKey(54));
        assertEquals('d',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = tested.encryptChar('ҋ',new IntKey(345));
        assertEquals('ג',result);
    }
}