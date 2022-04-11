package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import exceptions.InvalidEncryptionKeyException;
import utils.keys.DoubleKey;
import utils.keys.IKey;

import java.awt.geom.QuadCurve2D;


public class DoubleEncryption<T> extends EncryptionAlgorithmImpl<T> {
    final IEncryptionAlgorithm<T> encryptionAlgorithm;


    public DoubleEncryption(EncryptionAlgorithmImpl<T> aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
        numberOfKeys = 2 * aEncryptionAlgorithm.numberOfKeys;
    }

    @Override
    public char encryptChar(char plainChar, IKey<T> key) throws InvalidEncryptionKeyException {
        if (!(key instanceof DoubleKey)) {
            throw new InvalidEncryptionKeyException("expected: DoubleKey");
        }
            char cipherChar = encryptionAlgorithm.encryptChar(plainChar, ((DoubleKey<T>) key).getKey1());
        cipherChar = encryptionAlgorithm.encryptChar(cipherChar, ((DoubleKey<T>) key).getKey2());
        return cipherChar;
    }

    @Override
    public char decryptChar(char cipherChar, IKey<T> key) throws InvalidEncryptionKeyException {
        if (!(key instanceof DoubleKey)) {
            throw new InvalidEncryptionKeyException("expected: DoubleKey");
        }
        char plainChar = encryptionAlgorithm.decryptChar(cipherChar, ((DoubleKey<T>) key).getKey2());
        plainChar = encryptionAlgorithm.decryptChar(plainChar, ((DoubleKey<T>) key).getKey1());
        return plainChar;
    }

    @Override
    public int getKeyStrength() {
        return 2 * encryptionAlgorithm.getKeyStrength();
    }

    public String getName() {
        return "{DoubleEncryption}";
    }

    @Override
    public String toString() {
        return "DoubleEncryption{" + encryptionAlgorithm + "}";
    }

    public IEncryptionAlgorithm<T> getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
