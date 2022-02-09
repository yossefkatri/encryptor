package encriptionAlgorithms.basicAlgorithms;

import encriptionAlgorithms.EncryptionAlgorithm;
import keys.IKey;
import keys.IntKey;

public class ShiftMultiplyEncryption implements EncryptionAlgorithm, BasicEncryption{

    @Override
    public String encryptChar(String plaintext, IKey key) {
        int intKey = ((IntKey)key).getKey();
        int product = (int) plaintext.charAt(0) * intKey;
        char divider = (char) (product /NUM);
        char remainder = (char) (product %NUM);
        return String.valueOf(divider) + remainder;
    }

    @Override
    public String decryptChar(String ciphertext, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return String.valueOf((char) ((ciphertext.charAt(0) * NUM + ciphertext.charAt(1)) / intKey));
    }

    // encrypt the plaintext with the key and return the ciphertext
    public  String encrypt(String plaintext, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plaintext.length(); ++i) {
            int product = (int) plaintext.charAt(i) * intKey;
            char divider = (char) (product /NUM);
            char remainder = (char) (product %NUM);
            encryptedData.append(divider).append(remainder);
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    public  String decrypt(String ciphertext, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i+=2) {
            decryptedData.append((char) ((ciphertext.charAt(i) *NUM+ciphertext.charAt(i+1))/intKey));
        }
        return decryptedData.toString();
    }
}
