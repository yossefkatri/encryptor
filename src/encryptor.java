import java.io.*;
import java.util.*;

public class encryptor {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            if(input == 1)
            {
                EncryptionAlgorithm.Encrypt();
            }
            else if(input == 2)
            {
                EncryptionAlgorithm.Decrypt();
            }
            else
            {
                System.out.println("Error: you should put 1 or 2");
            }
        }
    }

}
