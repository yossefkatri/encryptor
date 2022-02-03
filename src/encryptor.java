import EncriptionAlgorithms.ShiftMultiplyEncryption;
import interfaces.EncryptionAlgorithm;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class encryptor {

    private static final int UPPER_LIMIT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            scanner.nextLine();
            EncryptionAlgorithm encryptionAlgorithm = new ShiftMultiplyEncryption();
            FileStream fileManager = new FileStream();
            if (input == 1) {
                //get the file-path from the user
                System.out.println("Enter the path of your source file:");
                String path = scanner.nextLine();
                path = path.replaceAll("\"", "");

                String plainText;
                try {
                    plainText = fileManager.getFileContent(path);
                }
                catch (FileNotFoundException e) {
                    System.out.println("the system can't find the file: \n" + e.getMessage());
                    return;
                }

                //generate the key
                Random randomizer = new Random();
                int key = randomizer.nextInt(UPPER_LIMIT);

                //encrypt the data
                String cipherText = encryptionAlgorithm.Encrypt(plainText, key);

                //calculate the path
                String filesPath = fileManager.getOutputFilesPath(path);
                System.out.println("the locations of the key file and the encrypted file :" + filesPath + "\n");

                //create the output-files
                FileWriter keyWriter;
                FileWriter cipherWriter;
                try {
                    keyWriter = fileManager.createFile(filesPath + "key.txt");
                    cipherWriter = fileManager.createFile(fileManager.getFileName(path, "_encrypted"));
                } catch (IOException e) {
                    System.out.println("the system can't create the file: \n" + e.getMessage());
                    return;
                }

                //save the data into the files
                try {
                    fileManager.saveData(keyWriter,  String.valueOf(key) );
                    fileManager.saveData(cipherWriter, cipherText);
                } catch (IOException e) {
                    System.out.println("the system can't write into the files: \n" + e.getMessage());
                    return;
                }

                //close the writer-files
                try {
                    keyWriter.close();
                    cipherWriter.close();
                } catch (IOException e) {
                    System.out.println("the system can't close the file: \n" + e.getMessage());
                    return;
                }

            }
            else if (input == 2) {
                //get the encrypted-file-path from the user
                System.out.println("Enter the location of the encrypted source file: ");
                String encryptedFilePath = scanner.nextLine();
                encryptedFilePath = encryptedFilePath.replaceAll("\"", "");

                //get the key-file-path from the user
                System.out.println("Enter the location of the key file: ");
                String keyPath = scanner.nextLine();
                keyPath = keyPath.replaceAll("\"", "");

                String cipherText;
                try {
                    cipherText = fileManager.getFileContent(encryptedFilePath);
                }
                catch (FileNotFoundException e) {
                    System.out.println("the system can't find the file: \n" + e.getMessage());
                    return;
                }
                int key;
                try {
                    key = fileManager.getKey(keyPath);
                }
                catch (FileNotFoundException e) {
                    System.out.println("the system can't find the file: \n" + e.getMessage());
                    return;
                }
                String decryptMessage = encryptionAlgorithm.Decrypt(cipherText,key);
                String decryptedFilePath =fileManager.getFileName(encryptedFilePath,"_decrypted");
                FileWriter decryptedFile;
                try {
                    decryptedFile = fileManager.createFile(decryptedFilePath);
                }
                catch (IOException e) {
                    System.out.println("the system can't create the file: \n" + e.getMessage());
                    return;
                }
                try {
                    fileManager.saveData(decryptedFile,decryptMessage);
                }
                catch (IOException e) {
                    System.out.println("the system can't write into the files: \n" + e.getMessage());
                    return;
                }
                try {
                    decryptedFile.close();
                }
                catch (IOException e) {
                    System.out.println("the system can't close the file: \n" + e.getMessage());
                    return;
                }
            }
            else {
                System.out.println("Error: you should put 1 or 2");
            }
        }
    }
}
