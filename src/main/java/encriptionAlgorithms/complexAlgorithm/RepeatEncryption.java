package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.IKey;

public class RepeatEncryption extends EncryptionAlgorithmImpl {
    final IEncryptionAlgorithm encryptionAlgorithm;
    final int times;

    public RepeatEncryption(EncryptionAlgorithmImpl encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.numberOfKeys = encryptionAlgorithm.numberOfKeys;
        this.times = times;
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
        for (int i = 0; i < times; ++i) {
            plainChar =encryptionAlgorithm.decryptChar(plainChar,key);
        }
        return plainChar;
    }

    @Override
    public int getKeyStrength() {
        return times* encryptionAlgorithm.getKeyStrength();
    }
}
