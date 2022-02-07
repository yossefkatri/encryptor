import encriptionAlgorithms.complexAlgorithm.*;
import encriptionAlgorithms.basicAlgorithms.*;
import interfaces.EncryptionAlgorithm;
import interfaces.IKey;
import keys.DoubleKey;
import keys.IntKey;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            EncryptionAlgorithm encryptionAlgorithm = new DoubleEncryption(new DoubleEncryption(new RepeatEncryption(new ShiftUpEncryption(),2)));
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

                //get the necessary number of the keys
                int numKeys = encryptionAlgorithm.NumKeys();
                List<Integer> keys = new ArrayList<>();
                for(int i= 0;i<numKeys;++i) {
                    //generate the key
                    Random randomizer = new Random();
                    int tempKey = randomizer.nextInt(UPPER_LIMIT);
                    keys.add(tempKey);
                }


                IKey key = BuildKey(keys);

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
                    assert key != null;
                    fileManager.saveData(keyWriter,  key.toString() );
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
                List<Integer> keys;
                try {
                    keys = fileManager.getKeys(keyPath);
                }
                catch (FileNotFoundException e) {
                    System.out.println("the system can't find the file: \n" + e.getMessage());
                    return;
                }

                IKey key = BuildKey(keys);

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
    public static IKey BuildKey(List<Integer> keys) {
        if (keys.size() == 1)
        {
             return new IntKey(keys.get(0));

        }
        else if(keys.size() == 0)
        {
            return null;
        }
        IKey key1;
        key1 = BuildKey(keys.subList(0, keys.size()/2));

        IKey key2;
        key2 = BuildKey(keys.subList(keys.size()/2,keys.size()));

        return new DoubleKey(key1,key2);
    }
}
