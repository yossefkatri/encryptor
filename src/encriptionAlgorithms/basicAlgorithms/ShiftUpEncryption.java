package encriptionAlgorithms.basicAlgorithms;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;
import keys.IntKey;

public class ShiftUpEncryption implements EncryptionAlgorithm {

    static final int NUM = 65534;
    private int EncryptChar(int plainChar, int key) {
        return (plainChar+key)%NUM;
    }

    private int DecryptChar(int cipherChar, int key) {
        return (cipherChar+NUM-key)%NUM;
    }
    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( (char) EncryptChar(plainText.charAt(i) ,intKey));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); ++i) {
            decryptedData.append((char) DecryptChar( cipherText.charAt(i) , intKey));
        }
        return decryptedData.toString();
    }

    @Override
    public int NumKeys() {
        return 1;
    }

}
