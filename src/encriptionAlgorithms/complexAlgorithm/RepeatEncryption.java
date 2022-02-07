package encriptionAlgorithms.complexAlgorithm;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;

public class RepeatEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;
    int times;
    public RepeatEncryption(EncryptionAlgorithm encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.times = times;
    }

    @Override
    public String Encrypt(String plainText, IKey key) {
        String cipherText = plainText;
        for (int i = 0; i < times; ++i) {
            cipherText = encryptionAlgorithm.Encrypt(cipherText, key);
        }
        return cipherText;
    }

    @Override
    public String Decrypt(String cipherText, IKey key) {
        String decryptMessage = cipherText;
        for (int i = times - 1; i >= 0; --i) {
            decryptMessage = encryptionAlgorithm.Decrypt(decryptMessage, key);
        }
        return decryptMessage;
    }

    @Override
    public int NumKeys() {
        return encryptionAlgorithm.NumKeys();
    }
}
