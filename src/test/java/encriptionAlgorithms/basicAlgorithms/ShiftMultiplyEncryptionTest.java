package encriptionAlgorithms.basicAlgorithms;

import utils.keys.BasicKey;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertEquals;

class ShiftMultiplyEncryptionTest {

    final ShiftMultiplyEncryption testedEncryption = ShiftMultiplyEncryption.getInstance();


    @Test
    void encryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('a', new BasicKey<>(4));
        assertEquals('ӭ',result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('d', new BasicKey<>(4));
        assertEquals('Ԕ',result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = testedEncryption.decryptChar('ӭ', new BasicKey<>(4));
        assertEquals('a',result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = testedEncryption.decryptChar('Ԕ', new BasicKey<>(4));
        assertEquals('d',result);
    }
}