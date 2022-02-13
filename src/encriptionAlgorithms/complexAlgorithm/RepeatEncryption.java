package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.IKey;

public class RepeatEncryption extends EncryptionAlgorithmImpl {
    final IEncryptionAlgorithm encryptionAlgorithm;
    final int times;

    public RepeatEncryption(EncryptionAlgorithmImpl encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.numKeys = encryptionAlgorithm.numKeys;
        this.times = times;
    }

    public IEncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    @Override
    public char encryptChar(char plainChar, IKey key) {
        char ciphertext = plainChar;
        for (int i = 0; i < times; ++i) {
            ciphertext = encryptionAlgorithm.encryptChar(ciphertext,key);
        }
        return ciphertext;
    }

    @Override
    public char decryptChar(char cipherChar, IKey key) {
        char plainChar = cipherChar;
        for (int i = times - 1; i >= 0; --i) {
            plainChar =encryptionAlgorithm.decryptChar(plainChar,key);
        }
        return plainChar;
    }
}
