package OperationsAlgorithms;

import interfaces.EncryptionAlgorithm;

public class RepeatEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;
    int times;
    public RepeatEncryption(EncryptionAlgorithm encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.times = times;
    }

    @Override
    public String Encrypt(String plainText, int key) {
        String cipherText = plainText;
        for (int i = 0; i < times; ++i) {
            cipherText = encryptionAlgorithm.Encrypt(cipherText, key);
        }
        return cipherText;
    }

    @Override
    public String Decrypt(String cipherText, int key) {
        String decryptMessage = cipherText;
        for (int i = times - 1; i >= 0; --i) {
            decryptMessage = encryptionAlgorithm.Decrypt(decryptMessage, key);
        }
        return decryptMessage;
    }
}
