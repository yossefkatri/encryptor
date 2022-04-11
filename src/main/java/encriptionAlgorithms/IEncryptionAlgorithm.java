package encriptionAlgorithms;

import exceptions.InvalidEncryptionKeyException;
import utils.keys.IKey;

public interface IEncryptionAlgorithm<T> {
    // encrypt the plainChar with the key and return the ciphertext
    char encryptChar(char plainChar, IKey<T> key) throws InvalidEncryptionKeyException;

    //decrypt the cipherChar with the key and return the plaintext
    char decryptChar(char cipherChar, IKey<T> key) throws InvalidEncryptionKeyException;


    int getKeyStrength();
}