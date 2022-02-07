package encriptionAlgorithms.basicAlgorithms;

import interfaces.EncryptionAlgorithm;
import interfaces.IKey;
import keys.IntKey;

public class ShiftMultiplyEncryption implements EncryptionAlgorithm {

    static final int NUM = 65534;
    private String EncryptChar(int plainChar, int key) {
        int product = plainChar*key;
        char divider = (char) (product /NUM);
        char remainder = (char) (product %NUM);

        return String.valueOf(divider) + remainder;
    }

    private int DecryptChar(int divider,int remainder, int key) {
       return (divider*NUM+remainder)/key;
    }


    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( EncryptChar(plainText.charAt(i),intKey));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, IKey key) {
        int intKey = ((IntKey)key).getKey();
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i+=2) {
            decryptedData.append((char) DecryptChar(cipherText.charAt(i),cipherText.charAt(i+1), intKey));
        }
        return decryptedData.toString();
    }

    @Override
    public int NumKeys() {
        return 1;
    }
}
