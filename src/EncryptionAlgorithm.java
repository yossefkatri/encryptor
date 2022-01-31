public interface EncryptionAlgorithm {
    
    // encrypt the plaintext with the key and return the ciphertext
    public String Encrypt(String plainText, int key);

    //decrypt the ciphertext with the key and return the plaintext
    public String Decrypt(String cipherText, int key);
}