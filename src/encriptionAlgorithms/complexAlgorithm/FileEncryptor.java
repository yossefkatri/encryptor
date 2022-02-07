package encriptionAlgorithms.complexAlgorithm;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;

public class FileEncryptor implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    @Override
    public String Encrypt(String plainText, IKey key) {
        return  encryptionAlgorithm.Encrypt(plainText, key);
    }

    @Override
    public String Decrypt(String cipherText, IKey key) {
        return encryptionAlgorithm.Decrypt(cipherText, key);
    }

    @Override
    public int NumKeys() {
        return encryptionAlgorithm.NumKeys();
    }
}
