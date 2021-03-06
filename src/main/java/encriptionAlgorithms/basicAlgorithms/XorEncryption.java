package encriptionAlgorithms.basicAlgorithms;

import utils.keys.IKey;
import utils.keys.BasicKey;


//singleton class
public class XorEncryption extends BasicEncryption {

    static XorEncryption instanceXorEncryption = null;

    private XorEncryption() {
        UPPER_LIMIT = 99999;
    }


    public static XorEncryption getInstance() {
        if (instanceXorEncryption == null) {
            instanceXorEncryption = new XorEncryption();
        }
        return instanceXorEncryption;
    }

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public char encryptChar(char plainChar, IKey<Integer> key) {
        int intKey = ((BasicKey<Integer>) key).getKey();
        return (char) (plainChar ^ intKey);
    }

    //decrypt the ciphertext with the key and return the plaintext
    public char decryptChar(char cipherChar, IKey<Integer> key) {
        int intKey = ((BasicKey<Integer>) key).getKey();
        return (char) (cipherChar ^ intKey);
    }


    @Override
    public int getKeyStrength() {
        return Integer.toString(UPPER_LIMIT).length();
    }
}