package encriptionAlgorithms.basicAlgorithms;

import keys.IKey;
import keys.IntKey;

public class XorEncryption extends BasicEncryption {

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public char encryptChar(char plainChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return (char) (plainChar ^ intKey);
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public char decryptChar(char cipherChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return (char) (cipherChar ^ intKey);
    }
}