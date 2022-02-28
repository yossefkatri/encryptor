package encriptionAlgorithms.basicAlgorithms;

import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShiftUpEncryptionTest {

    final ShiftUpEncryption testedEncryption = ShiftUpEncryption.getInstance();

    @Test
    void encryptCharAsciiTest() {
        char result = testedEncryption.encryptChar('a', new IntKey(5));
        assertEquals('f', result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = testedEncryption.encryptChar('ף', new IntKey(5));
        assertEquals('ר', result);
    }

    @Test
    void encryptCharOverflowTest() {
        char plainChar = (char) 65535;
        char result = testedEncryption.encryptChar(plainChar, new IntKey(234));
        assertEquals('é', result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = testedEncryption.decryptChar('f', new IntKey(5));
        assertEquals('a', result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = testedEncryption.decryptChar('ר', new IntKey(5));
        assertEquals('ף', result);
    }

    @Test
    void decryptCharOverflowTest() {
        char cipherChar = 'é';
        char result = testedEncryption.decryptChar(cipherChar, new IntKey(234));
        assertEquals((char) 65535, result);
    }
}