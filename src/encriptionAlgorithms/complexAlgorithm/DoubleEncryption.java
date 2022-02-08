package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.DoubleKey;


public class DoubleEncryption implements EncryptionAlgorithm{
    EncryptionAlgorithm encryptionAlgorithm;

    public DoubleEncryption(EncryptionAlgorithm aEncryptionAlgorithm) {
        encryptionAlgorithm = aEncryptionAlgorithm;
    }

    @Override
    public String encrypt(String plainText, IKey key) {
        //encrypt-twice
        String cipherText = encryptionAlgorithm.encrypt(plainText, ((DoubleKey) key).getKey1());
        cipherText = encryptionAlgorithm.encrypt(cipherText,((DoubleKey) key).getKey2());

        return cipherText;
    }

    @Override
    public String decrypt(String cipherText, IKey key) {
        //encrypt-twice
        String plainText = encryptionAlgorithm.decrypt(cipherText,((DoubleKey) key).getKey2());
        plainText = encryptionAlgorithm.decrypt(plainText,((DoubleKey) key).getKey1());

        return plainText;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
