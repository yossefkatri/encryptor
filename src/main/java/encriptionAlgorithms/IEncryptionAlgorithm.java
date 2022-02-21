package encriptionAlgorithms;

import keys.IKey;

public interface IEncryptionAlgorithm {
    // encrypt the plainChar with the key and return the ciphertext
    char encryptChar(char plainChar, IKey key);

    //decrypt the cipherChar with the key and return the plaintext
    char decryptChar(char cipherChar, IKey key);

    int getKeyStrength();
}