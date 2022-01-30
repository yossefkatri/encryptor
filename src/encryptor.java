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
                    plainText = fileManager.getFileContent(path);
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
                System.out.println("the locations of the key file and the encrypted file :"+ filesPath+"\n");

                FileWriter keyWriter;
                FileWriter cipherWriter;
                try {
                    keyWriter = fileManager.createFile(filesPath + "key.txt");
                    cipherWriter = fileManager.createFile(fileManager.getFileName(path, "_encrypted"));
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
                String encryptedFilePath = scanner.nextLine();
                encryptedFilePath = encryptedFilePath.replaceAll("\"","");


                String cipherText;
                try {
                    cipherText = fileManager.getFileContent(encryptedFilePath);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found.");
                    e.printStackTrace();
                    return;
                }
                System.out.println("Enter the location of the key file: ");
                String keyFilePath = scanner.nextLine();
                keyFilePath = keyFilePath.replaceAll("\"","");
                int key;
                try {
                    key = fileManager.getKey(keyFilePath);
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: the file isn't found.");
                    e.printStackTrace();
                    return;
                }
                String decryptMessage = EncryptionAlgorithm.Decrypt(cipherText,key);
                FileWriter decryptedFile;
                try {
                    decryptedFile = fileManager.createFile(fileManager.getFileName(encryptedFilePath,"_decrypted"));
                }
                catch (IOException e) {
                    System.out.println("ERROR: can't create new file.");
                    e.printStackTrace();
                    return;
                }


                //save the decrypted-text into the file.
                try {
                    fileManager.saveData(decryptedFile,decryptMessage);
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
