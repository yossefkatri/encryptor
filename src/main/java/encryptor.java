import encriptionAlgorithms.basicAlgorithms.*;
import encriptionAlgorithms.IEncryptionAlgorithm;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import encriptionAlgorithms.complexAlgorithm.RepeatEncryption;
import utils.FileEncryptor;
import utils.FileStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class encryptor {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            scanner.nextLine();
            IEncryptionAlgorithm encryptionAlgorithm =new DoubleEncryption(new DoubleEncryption( new DoubleEncryption(XorEncryption.getInstance())));
            FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
            if (input == 1) {
                //get the file-path from the user
                System.out.println("Enter the path of your source file:");
                String stringPath = scanner.nextLine();
                stringPath = stringPath.replaceAll("\"", "");
                try {
                Path path = Paths.get(stringPath);
                //calculate the path
                Path filesPath = path.getParent();
                Path outputFilepath = FileStream.getFileName(path, "_encrypted");

                    fileEncryptor.encryptFile(path,outputFilepath,filesPath);
                    System.out.println("The locations of the key file and the encrypted file :" + filesPath + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (input == 2) {
                //get the encrypted-file-path from the user
                System.out.println("Enter the location of the encrypted source file: ");
                String encryptedFilePathInput = scanner.nextLine();
                encryptedFilePathInput = encryptedFilePathInput.replaceAll("\"", "");

                //get the key-file-path from the user
                System.out.println("Enter the location of the key file: ");
                String keyPathInput = scanner.nextLine();
                keyPathInput = keyPathInput.replaceAll("\"", "");

                try {
                Path encryptedFilePath = Paths.get(encryptedFilePathInput);
                Path decryptedFilePath = FileStream.getFileName(encryptedFilePath,"_decrypted");
                Path keyPath = Paths.get(keyPathInput);


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
