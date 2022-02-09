package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;

public class XorEncryption  implements EncryptionAlgorithm, BasicEncryption {

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public String encryptChar(String plainChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return String.valueOf((char) (plainChar.charAt(0) ^ intKey));
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public String decryptChar(String cipherChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return String.valueOf((char) (cipherChar.charAt(0) ^ intKey)) + (char) (cipherChar.charAt(1) ^ intKey);
    }
}