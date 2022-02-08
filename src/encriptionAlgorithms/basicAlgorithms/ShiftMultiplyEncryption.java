package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;

public class ShiftMultiplyEncryption implements EncryptionAlgorithm, BasicEncryption{

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String encrypt(String plainText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            int product = (int) plainText.charAt(i) * intKey;
            char divider = (char) (product /NUM);
            char remainder = (char) (product %NUM);
            encryptedData.append(divider).append(remainder);
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String decrypt(String cipherText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i+=2) {
            decryptedData.append((char) ((cipherText.charAt(i) *NUM+cipherText.charAt(i+1))/intKey));
        }
        return decryptedData.toString();
    }
}
