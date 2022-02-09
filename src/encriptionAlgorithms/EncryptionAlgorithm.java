package encriptionAlgorithms;

import keys.IKey;

public interface EncryptionAlgorithm {

    // encrypt the plainChar with the key and return the ciphertext
    String encryptChar(String plainChar, IKey key);

    //decrypt the cipherChar with the key and return the plaintext
    String decryptChar(String cipherChar, IKey key);

}