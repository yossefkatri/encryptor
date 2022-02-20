package encriptionAlgorithms.basicAlgorithms;

import keys.IntKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShiftUpEncryptionTest {

    final ShiftUpEncryption tested = new ShiftUpEncryption();

    @Test
    void encryptCharAsciiTest() {
        char result = tested.encryptChar('a', new IntKey(5));
        assertEquals('f', result);
    }

    @Test
    void encryptCharNonAsciiTest() {
        char result = tested.encryptChar('ף', new IntKey(5));
        assertEquals('ר', result);
    }

    @Test
    void encryptCharOverflowTest() {
        char plainChar = (char) 65535;
        char result = tested.encryptChar(plainChar, new IntKey(234));
        assertEquals('é', result);
    }

    @Test
    void decryptCharAsciiTest() {
        char result = tested.decryptChar('f', new IntKey(5));
        assertEquals('a', result);
    }

    @Test
    void decryptCharNonAsciiTest() {
        char result = tested.decryptChar('ר', new IntKey(5));
        assertEquals('ף', result);
    }

    @Test
    void decryptCharOverflowTest() {
        char cipherChar = 'é';
        char result = tested.decryptChar(cipherChar, new IntKey(234));
        assertEquals((char) 65535, result);
    }
}