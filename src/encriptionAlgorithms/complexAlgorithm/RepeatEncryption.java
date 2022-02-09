package encriptionAlgorithms.complexAlgorithm;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import utils.FileEncryptor;

public class RepeatEncryption implements EncryptionAlgorithm {
    final EncryptionAlgorithm encryptionAlgorithm;

    final int times;
    public RepeatEncryption(EncryptionAlgorithm encryptionAlgorithm, int times) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.times = times;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    @Override
    public String encryptChar(String plainChar, IKey key) {
        String ciphertext = plainChar;
        for (int i = 0; i < times; ++i) {
            ciphertext = FileEncryptor.encrypt(encryptionAlgorithm,ciphertext, key);
        }
        return ciphertext;
    }

    @Override
    public String decryptChar(String cipherChar, IKey key) {
        String decryptMessage = cipherChar;
        for (int i = times - 1; i >= 0; --i) {
            decryptMessage = FileEncryptor.decrypt(encryptionAlgorithm,decryptMessage, key);
        }
        return decryptMessage;
    }
}
