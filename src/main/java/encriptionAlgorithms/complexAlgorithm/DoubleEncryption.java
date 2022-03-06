package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.DoubleKey;
import keys.IKey;


public class DoubleEncryption<T> extends EncryptionAlgorithmImpl<T> {
    final IEncryptionAlgorithm<T> encryptionAlgorithm;


    public DoubleEncryption(EncryptionAlgorithmImpl<T> aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
        numberOfKeys = 2 * aEncryptionAlgorithm.numberOfKeys;
    }

    @Override
    public char encryptChar(char plainChar, IKey<T> key) {
        char cipherChar = encryptionAlgorithm.encryptChar(plainChar, ((DoubleKey<T>) key).getKey1());
        cipherChar = encryptionAlgorithm.encryptChar(cipherChar, ((DoubleKey<T>) key).getKey2());
        return cipherChar;
    }

    @Override
    public char decryptChar(char cipherChar, IKey<T> key) {

        char plainChar = encryptionAlgorithm.decryptChar(cipherChar, ((DoubleKey<T>) key).getKey2());
        plainChar = encryptionAlgorithm.decryptChar(plainChar, ((DoubleKey<T>) key).getKey1());
        return plainChar;
    }

    @Override
    public int getKeyStrength() {
        return 2*encryptionAlgorithm.getKeyStrength();
    }

    @Override
    public String toString() {
        return "DoubleEncryption{"+ encryptionAlgorithm +"}";
    }
}
