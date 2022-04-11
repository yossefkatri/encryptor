package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import exceptions.InvalidEncryptionKeyException;
import utils.keys.IKey;

public class RepeatEncryption<T> extends EncryptionAlgorithmImpl<T> {
    final IEncryptionAlgorithm<T> encryptionAlgorithm;

    final int times;

    public RepeatEncryption(EncryptionAlgorithmImpl<T> encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.numberOfKeys = encryptionAlgorithm.numberOfKeys;
        this.times = times;
    }

    @Override
    public char encryptChar(char plainChar, IKey<T> key) throws InvalidEncryptionKeyException {
        char ciphertext = plainChar;
        for (int i = 0; i < times; ++i) {
            ciphertext = encryptionAlgorithm.encryptChar(ciphertext,key);
        }
        return ciphertext;
    }

    @Override
    public char decryptChar(char cipherChar, IKey<T> key) throws InvalidEncryptionKeyException {
        char plainChar = cipherChar;
        for (int i = 0; i < times; ++i) {
            plainChar =encryptionAlgorithm.decryptChar(plainChar,key);
        }
        return plainChar;
    }

    @Override
    public int getKeyStrength() {
        return times * encryptionAlgorithm.getKeyStrength();
    }

    @Override
    public String toString() {
        return "RepeatEncryption{"+encryptionAlgorithm+","+times+"}";
    }

    public int getTimes() {
        return times;
    }

    public String getName()
    {
        return "{RepeatEncryption}";
    }

    public IEncryptionAlgorithm<T> getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
