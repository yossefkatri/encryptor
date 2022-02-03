package interfaces;
public interface EncryptionAlgorithm {
    
    // encrypt the plaintext with the key and return the ciphertext
    String Encrypt(String plainText, int key);

    //decrypt the ciphertext with the key and return the plaintext
    String Decrypt(String cipherText, int key);
}