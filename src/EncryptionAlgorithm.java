import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


public class EncryptionAlgorithm {

    // encrypt the plaintext with the key and return the ciphertext
    public static String Encrypt(String plaintext, int key) {
        StringBuilder encrypted_data = new StringBuilder();
        for (int i = 0; i < plaintext.length(); ++i) {
            encrypted_data.append( (char) ((int) plaintext.charAt(i) + key));
        }
        return encrypted_data.toString();
    }

    //decrypt the ciphertext with the key and return the plaintext
    public static String Decrypt(String ciphertext, int key) {
        StringBuilder decrypted_data = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); ++i) {
            decrypted_data.append((char) ((int) ciphertext.charAt(i) - key));
        }
        return decrypted_data.toString();
    }

}
