package encriptionAlgorithms.basicAlgorithms;

import utils.keys.IKey;
import utils.keys.BasicKey;

//singleton class
public class ShiftUpEncryption extends BasicEncryption {

    static ShiftUpEncryption instanceShiftUpEncryption = null;

    private ShiftUpEncryption() {
        UPPER_LIMIT = 999;
    }


    public static ShiftUpEncryption getInstance() {
        if (instanceShiftUpEncryption == null) {
            instanceShiftUpEncryption = new ShiftUpEncryption();
        }
        return instanceShiftUpEncryption;
    }

    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public char encryptChar(char plainChar, IKey<Integer> key) {
        int intKey = ((BasicKey<Integer>) key).getKey();
        return (char) ((plainChar + intKey) % ConstantsEncryption.MAX_CHAR);
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public char decryptChar(char cipherChar, IKey<Integer> key) {
        int intKey = ((BasicKey<Integer>) key).getKey();
        return (char) ((cipherChar + ConstantsEncryption.MAX_CHAR - intKey) % ConstantsEncryption.MAX_CHAR);
    }

    @Override
    public int getKeyStrength() {
        return Integer.toString(UPPER_LIMIT).length();
    }
}
