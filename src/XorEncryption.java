public class XorEncryption implements EncryptionAlgorithm{

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, int key) {
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( (char) ((int) plainText.charAt(i) ^ key));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, int key) {
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); ++i) {
            decryptedData.append((char) ((int) cipherText.charAt(i) ^ key));
        }
        return decryptedData.toString();
    }
}
