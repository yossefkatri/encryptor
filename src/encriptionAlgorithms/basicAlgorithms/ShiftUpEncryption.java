package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;

public class ShiftUpEncryption  implements EncryptionAlgorithm, BasicEncryption {
    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String encrypt(String plainText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( (char)((plainText.charAt(i)+intKey)%NUM));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String decrypt(String cipherText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); ++i) {
            decryptedData.append((char) ((cipherText.charAt(i)+NUM-intKey)%NUM));
        }
        return decryptedData.toString();
    }

}
