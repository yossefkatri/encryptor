import java.io.*;
import java.util.*;

public class encryptor {

    private static final int upper_limit = 50000;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        FileStream fileManager = new FileStream();
        while(true)
        {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            if(input == 1) {
                //get the file-path from the user
                System.out.println("Enter the path of yur source file:");
                String path = scanner.nextLine();
                path = path.replaceAll("\"", "");


                String plainText;
                try {
                    plainText = fileManager.getMsg(path);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found");
                    e.printStackTrace();
                    return;
                }

                //generate the key and calculate the output
                Random randomizer = new Random();
                int key = randomizer.nextInt(upper_limit);
                String cipherText = EncryptionAlgorithm.Encrypt(plainText, key);

                //calculate the path and save the output on files
                String filesPath = fileManager.getOutputFilesPath(path);
                FileWriter keyWriter;
                FileWriter cipherWriter;
                try {
                    keyWriter = fileManager.getKeyFile(filesPath);
                    cipherWriter = fileManager.getEncryptedFile(path);
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't create new file.");
                    e.printStackTrace();
                    return;
                }

                //save the data into the files
                try {
                    fileManager.saveData(keyWriter,String.valueOf(key));
                    fileManager.saveData(cipherWriter, cipherText);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }


                //close the writer-files
                try {
                    keyWriter.close();
                    cipherWriter.close();
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't close the decrypted-file.");
                    e.printStackTrace();
                    return;
                }
            }
            else if(input == 2)
            {
                //get the encrypted-file-path from the user
                System.out.println("Enter the location of the encrypted source file: ");
                String EncryptedFilePath = scanner.nextLine();
                EncryptedFilePath = EncryptedFilePath.replaceAll("\"","");


                String cipherText;
                try {
                    cipherText = fileManager.getMsg(EncryptedFilePath);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found.");
                    e.printStackTrace();
                    return;
                }
                System.out.println("Enter the location of the key file: ");
                String KeyFilePath = scanner.nextLine();
                KeyFilePath = KeyFilePath.replaceAll("\"","");
                int key;
                try {
                    key = fileManager.getKey(KeyFilePath);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found.");
                    e.printStackTrace();
                    return;
                }
                String DecryptMessage = EncryptionAlgorithm.Decrypt(cipherText,key);
                FileWriter decryptedFile;
                try {
                    decryptedFile = fileManager.getDecryptedFile(EncryptedFilePath);
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't create new file.");
                    e.printStackTrace();
                    return;
                }


                //save the decrypted-text into the file.
                try {
                    fileManager.saveData(decryptedFile,DecryptMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    decryptedFile.close();
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
