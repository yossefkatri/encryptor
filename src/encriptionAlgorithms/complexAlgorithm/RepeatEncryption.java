package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;

public class RepeatEncryption implements EncryptionAlgorithm {
    EncryptionAlgorithm encryptionAlgorithm;

    int times;
    public RepeatEncryption(EncryptionAlgorithm encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.times = times;
    }
    @Override
    public String encrypt(String plainText, IKey key) {
        String cipherText = plainText;
        for (int i = 0; i < times; ++i) {
            cipherText = encryptionAlgorithm.encrypt(cipherText, key);
        }
        return cipherText;
    }

    @Override
    public String decrypt(String cipherText, IKey key) {
        String decryptMessage = cipherText;
        for (int i = times - 1; i >= 0; --i) {
            decryptMessage = encryptionAlgorithm.decrypt(decryptMessage, key);
        }
        return decryptMessage;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
