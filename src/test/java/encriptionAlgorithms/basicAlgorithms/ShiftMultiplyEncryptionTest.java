package encriptionAlgorithms.basicAlgorithms;

import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertEquals;

class ShiftMultiplyEncryptionTest {

    ShiftMultiplyEncryption tested = new ShiftMultiplyEncryption();


    @Test
    void encryptCharAsciiTest() {
        char result = tested.encryptChar('a',new IntKey(4));
        assertEquals('ӭ',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = tested.encryptChar('d',new IntKey(4));
        assertEquals('Ԕ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = tested.decryptChar('ӭ',new IntKey(4));
        assertEquals('a',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = tested.decryptChar('Ԕ',new IntKey(4));
        assertEquals('d',result);
    }
}