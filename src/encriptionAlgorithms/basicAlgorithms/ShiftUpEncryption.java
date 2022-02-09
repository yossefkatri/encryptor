package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;

public class ShiftUpEncryption  implements EncryptionAlgorithm, BasicEncryption {
    // encrypt the plaintext with the key and return the ciphertext
    public  String encrypt(String plaintext, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plaintext.length(); ++i) {
            encryptedData.append( (char)((plaintext.charAt(i)+intKey)%NUM));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    public  String decrypt(String ciphertext, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); ++i) {
            decryptedData.append((char) ((ciphertext.charAt(i)+NUM-intKey)%NUM));
        }
        return decryptedData.toString();
    }

    @Override
    public String encryptChar(String plainChar, IKey key) {
        int intKey = ((IntKey)key).getKey();
        return String.valueOf((char) ((plainChar.charAt(0) + intKey) % NUM));
    }

    @Override
    public String decryptChar(String cipherChar, IKey key) {
        int intKey = ((IntKey)key).getKey();
        return String.valueOf((char) ((cipherChar.charAt(0) + NUM - intKey) % NUM)) +
                (char) ((cipherChar.charAt(1) + NUM - intKey) % NUM);
    }
}
