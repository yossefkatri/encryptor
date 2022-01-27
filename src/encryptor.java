import java.io.*;
import java.util.*;

public class encryptor {

    private static final int upper_limit = 50000;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        FileStream fileManager = new FileStream(System.in);
        while(true)
        {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            if(input == 1) {
                //get the input from the file
                String path = fileManager.getFileName();
                String plaintext;
                try {
                    plaintext = fileManager.getMsg(path);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found");
                    e.printStackTrace();
                    return;
                }

                //generate the key and calculate the output
                Random randomizer = new Random();
                int key = randomizer.nextInt(upper_limit);
                String ciphertext = EncryptionAlgorithm.Encrypt(plaintext, key);

                //calculate the path and save the output on files
                String files_path = fileManager.getOutputFilesPath(path);
                FileWriter key_writer;
                FileWriter cipher_writer;
                try {
                    key_writer = fileManager.getKeyFile(files_path);
                    cipher_writer = fileManager.getEncryptedFile(path);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                fileManager.saveData(key_writer,String.valueOf(key));
                fileManager.saveData(cipher_writer, ciphertext);


                //close the writer-files
                try {
                    key_writer.close();
                    cipher_writer.close();
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't close the decrypted-file.");
                    e.printStackTrace();
                    return;
                }
            }
            else if(input == 2)
            {
                String ciphertext;
                String EncryptedFile_path =  fileManager.getEncryptedFilePath();
                try {
                    ciphertext = fileManager.getMsg(EncryptedFile_path);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found.");
                    e.printStackTrace();
                    return;
                }
                int key = fileManager.getKey();
                String DecryptMessage = EncryptionAlgorithm.Decrypt(ciphertext,key);
                FileWriter decrypted_file;
                try {
                    decrypted_file = fileManager.getDecryptedFile(EncryptedFile_path);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                fileManager.saveData(decrypted_file,DecryptMessage);
                try {
                    decrypted_file.close();
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't close the decrypted-file.");
                    e.printStackTrace();
                    return;
                }

            }
            else
            {
                System.out.println("Error: you should put 1 or 2");
            }
        }
    }

}
