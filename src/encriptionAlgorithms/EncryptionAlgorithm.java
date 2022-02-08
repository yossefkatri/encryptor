package encriptionAlgorithms;

import keys.IKey;

public interface EncryptionAlgorithm {
    
    // encrypt the plaintext with the key and return the ciphertext
    String encrypt(String plainText, IKey key);

    //decrypt the ciphertext with the key and return the plaintext
    String decrypt(String cipherText, IKey key);

}