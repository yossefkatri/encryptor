package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.DoubleKey;
import utils.FileEncryptor;


public class DoubleEncryption implements EncryptionAlgorithm{
    final EncryptionAlgorithm encryptionAlgorithm;


    public DoubleEncryption(EncryptionAlgorithm aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
    }

    @Override
    public String encryptChar(String plainChar, IKey key) {
        String cipherChar= FileEncryptor.encrypt(encryptionAlgorithm,plainChar,((DoubleKey) key).getKey1());
        cipherChar = FileEncryptor.encrypt(encryptionAlgorithm,cipherChar,((DoubleKey) key).getKey2());
        return cipherChar;
    }

    @Override
    public String decryptChar(String cipherChar, IKey key) {

        String plainChar = FileEncryptor.decrypt(encryptionAlgorithm,cipherChar,((DoubleKey) key).getKey2());
        plainChar = FileEncryptor.decrypt(encryptionAlgorithm,plainChar,((DoubleKey) key).getKey1());
        return plainChar;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
