package encriptionAlgorithms.basicAlgorithms;

import utils.keys.IKey;
import utils.keys.BasicKey;

import java.math.BigInteger;

//singleton class
public class ShiftMultiplyEncryption extends BasicEncryption {

    static ShiftMultiplyEncryption instanceShiftMultiplyEncryption = null;
    private ShiftMultiplyEncryption() {
        UPPER_LIMIT = 9999;
    }

    @Override
    public String toString() {
        return "{ShiftMultiplyEncryption}";
    }

    public static ShiftMultiplyEncryption getInstance() {
        if (instanceShiftMultiplyEncryption == null)
        {
            instanceShiftMultiplyEncryption = new ShiftMultiplyEncryption();
        }
        return instanceShiftMultiplyEncryption;
    }


    private int generatePrimeNum(int intKey) {

        int start =3;
        int result =0;
        for(int i=0;i<=intKey;++i) {
            for (int j = start;;++j){
                boolean flag = false;
                for (int k = 2; k<j;++k) {
                    if(j%k == 0) {
                        flag =true;
                        break;
                    }
                }
                if(!flag)
                {
                    start =j+1;
                    result =j;
                    break;
                }
            }
        }
        return result;
    }
    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public char encryptChar(char plaintext, IKey<Integer> key) {
        //limited my key to 0-10
        int intKey = ((BasicKey<Integer>)key).getKey() %11;
        //calculate the prime-key
        int primeKey = generatePrimeNum(intKey);

        int product = (int) plaintext * primeKey;
        return (char) (product%ConstantsEncryption.MAX_CHAR);
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public char decryptChar(char ciphertext, IKey<Integer> key) {
        int intKey = ((BasicKey<Integer>)key).getKey() %11;
        int primeKey =generatePrimeNum(intKey);

        //calculate the multiplication inverse
        BigInteger bigKey = new BigInteger(String.valueOf(primeKey));
        BigInteger bigModulo = new BigInteger(String.valueOf(ConstantsEncryption.MAX_CHAR));
        BigInteger inverse =(bigKey.modInverse(bigModulo));

        return (char) ((ciphertext*inverse.intValue())%ConstantsEncryption.MAX_CHAR);
    }

    @Override
    public int getKeyStrength() {
        return Integer.toString(UPPER_LIMIT).length();
    }

}
