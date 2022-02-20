package encriptionAlgorithms.basicAlgorithms;

import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertEquals;

class ShiftMultiplyEncryptionTest {

    final ShiftMultiplyEncryption testedEncryption = new ShiftMultiplyEncryption();


    @Test
    void encryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('a',new IntKey(4));
        assertEquals('ӭ',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('d',new IntKey(4));
        assertEquals('Ԕ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = testedEncryption.decryptChar('ӭ',new IntKey(4));
        assertEquals('a',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = testedEncryption.decryptChar('Ԕ',new IntKey(4));
        assertEquals('d',result);
    }
}