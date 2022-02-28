package encriptionAlgorithms.basicAlgorithms;

import keys.IKey;
import keys.IntKey;


//singleton class
public class XorEncryption extends BasicEncryption {

    static XorEncryption instanceXorEncryption = null;
    private XorEncryption() {
        UPPER_LIMIT = 99999;
    }

    @Override
    public String toString() {
        return "{XorEncryption}";
    }

    public static XorEncryption getInstance(){
        if (instanceXorEncryption == null){
            instanceXorEncryption = new XorEncryption();
        }
        return instanceXorEncryption;
    }

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public char encryptChar(char plainChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return (char) (plainChar ^ intKey);
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public char decryptChar(char cipherChar, IKey key) {
        int intKey = ((IntKey) key).getKey();
        return (char) (cipherChar ^ intKey);
    }

    @Override
    public int getKeyStrength() {
        return Integer.toString(UPPER_LIMIT).length();
    }
}