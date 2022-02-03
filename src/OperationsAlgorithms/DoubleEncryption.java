package OperationsAlgorithms;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;


public class DoubleEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    public DoubleEncryption(EncryptionAlgorithm aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
    }

    //@Override
    public String Encrypt(String plainText, IKey key) {
        //get the key from the object
        int key1 = key.getKey1();
        int key2 = key.getKey2();

        //encrypt-twice
        String cipherText = encryptionAlgorithm.Encrypt(plainText, key1);
        cipherText = encryptionAlgorithm.Encrypt(cipherText,key2);

        return cipherText;
    }

    //@Override
    public String Decrypt(String cipherText, IKey key) {
        //get the key from the object
        int key1 = key.getKey1();
        int key2 = key.getKey2();

        //encrypt-twice
        String plainText = encryptionAlgorithm.Decrypt(cipherText, key2);
        plainText = encryptionAlgorithm.Decrypt(plainText,key1);

        return plainText;
    }
}
