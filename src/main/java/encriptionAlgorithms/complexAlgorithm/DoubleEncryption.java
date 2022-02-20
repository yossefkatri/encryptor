package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.DoubleKey;
import keys.IKey;


public class DoubleEncryption extends EncryptionAlgorithmImpl {
    final IEncryptionAlgorithm encryptionAlgorithm;


    public DoubleEncryption(EncryptionAlgorithmImpl aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
        numberOfKeys = 2 * aEncryptionAlgorithm.numberOfKeys;
    }

    @Override
    public char encryptChar(char plainChar, IKey key) {
        char cipherChar = encryptionAlgorithm.encryptChar(plainChar, ((DoubleKey) key).getKey1());
        cipherChar = encryptionAlgorithm.encryptChar(cipherChar, ((DoubleKey) key).getKey2());
        return cipherChar;
    }

    @Override
    public char decryptChar(char cipherChar, IKey key) {

        char plainChar = encryptionAlgorithm.decryptChar(cipherChar, ((DoubleKey) key).getKey2());
        plainChar = encryptionAlgorithm.decryptChar(plainChar, ((DoubleKey) key).getKey1());
        return plainChar;
    }
}
