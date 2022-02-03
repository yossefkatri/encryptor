package OperationsAlgorithms;

import interfaces.EncryptionAlgorithm;

public class FileEncryptor implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    @Override
    public String Encrypt(String plainText, int key) {
        return  encryptionAlgorithm.Encrypt(plainText, key);
    }

    @Override
    public String Decrypt(String cipherText, int key) {
        return encryptionAlgorithm.Decrypt(cipherText, key);
    }
}
