package encriptionAlgorithms.complexAlgorithm;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;
import keys.DoubleKey;


public class DoubleEncryption implements EncryptionAlgorithm{
    EncryptionAlgorithm encryptionAlgorithm;

    public DoubleEncryption(EncryptionAlgorithm aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
    }

    @Override
    public String Encrypt(String plainText, IKey key) {
        //encrypt-twice
        String cipherText = encryptionAlgorithm.Encrypt(plainText, ((DoubleKey) key).getKey1());
        cipherText = encryptionAlgorithm.Encrypt(cipherText,((DoubleKey) key).getKey2());

        return cipherText;
    }

    @Override
    public String Decrypt(String cipherText, IKey key) {
        //encrypt-twice
        String plainText = encryptionAlgorithm.Decrypt(cipherText,((DoubleKey) key).getKey2());
        plainText = encryptionAlgorithm.Decrypt(plainText,((DoubleKey) key).getKey1());

        return plainText;
    }

    @Override
    public int NumKeys() {
        return 2*encryptionAlgorithm.NumKeys();
    }
}
