package encriptionAlgorithms.basicAlgorithms;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;
import keys.IntKey;

public class XorEncryption implements EncryptionAlgorithm {

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( (char) ((int) plainText.charAt(i) ^ intKey));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); ++i) {
            decryptedData.append((char) ((int) cipherText.charAt(i) ^ intKey));
        }
        return decryptedData.toString();
    }

    @Override
    public int NumKeys() {
        return 1;
    }
}
