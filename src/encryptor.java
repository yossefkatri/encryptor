import encriptionAlgorithms.basicAlgorithms.*;
import encriptionAlgorithms.EncryptionAlgorithm;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import encriptionAlgorithms.complexAlgorithm.RepeatEncryption;
import utils.FileEncryptor;
import utils.FileStream;

import java.util.Scanner;

public class encryptor {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            scanner.nextLine();
            EncryptionAlgorithm encryptionAlgorithm =new DoubleEncryption(new XorEncryption());
            FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
            if (input == 1) {
                //get the file-path from the user
                System.out.println("Enter the path of your source file:");
                String path = scanner.nextLine();
                path = path.replaceAll("\"", "");

                //calculate the path
                String filesPath = FileStream.getOutputFilesPath(path);
                String outputFilepath = FileStream.getFileName(path, "_encrypted");

                try {
                    fileEncryptor.encryptFile(path,outputFilepath,filesPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                System.out.println("the locations of the key file and the encrypted file :" + filesPath + "\n");


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
                String decryptedFilePath =FileStream.getFileName(encryptedFilePath,"_decrypted");

                try {
                    fileEncryptor.decryptFile(encryptedFilePath,decryptedFilePath,keyPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Error: you should put 1 or 2");
            }
        }
    }
}
