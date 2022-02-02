public class ShiftMultiplyEncryption implements EncryptionAlgorithm{

    static final int NUM = 65534;
    private int EncryptChar(int plainChar, int key) {
        return (plainChar*key)%NUM;
    }

    private int DecryptChar(int chipherChar, int key) {
        int i = 0;
        for (;((chipherChar+i*NUM)%key != 0);++i);
        return (chipherChar+i*NUM)/key;
    }


    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, int key) {
        //TODO
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( (char) EncryptChar((int) plainText.charAt(i),key));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, int key) {
        //TODO
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); ++i) {
            decryptedData.append((char) DecryptChar((int) cipherText.charAt(i), key));
        }
        return decryptedData.toString();
    }
}
