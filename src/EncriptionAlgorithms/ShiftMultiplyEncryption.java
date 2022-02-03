package EncriptionAlgorithms;

import interfaces.EncryptionAlgorithm;

public class ShiftMultiplyEncryption implements EncryptionAlgorithm {

    static final int NUM = 65534;
    private String EncryptChar(int plainChar, int key) {
        int num = plainChar*key;
        StringBuilder sb =new StringBuilder();
        char num1 = (char) (num/NUM);
        char num2 = (char) (num%NUM);

        sb.append(num1);
        sb.append(num2);
        return sb.toString();
    }

    private int DecryptChar(int divider,int Remainder, int key) {
       return (divider*NUM+Remainder)/key;
    }


    // encrypt the plaintext with the key and return the ciphertext
    @Override
    public  String Encrypt(String plainText, int key) {
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plainText.length(); ++i) {
            encryptedData.append( EncryptChar(plainText.charAt(i),key));
        }
        return encryptedData.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    @Override
    public  String Decrypt(String cipherText, int key) {
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i+=2) {
            decryptedData.append((char) DecryptChar(cipherText.charAt(i),cipherText.charAt(i+1), key));
        }
        return decryptedData.toString();
    }
}
