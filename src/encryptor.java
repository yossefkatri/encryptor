import java.util.*;

public class encryptor {


    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            scanner.nextLine();
            EncryptionAlgorithm encryptionAlgorithm= new XorEncryption();
            FileStream fileManager = new FileStream();
            FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm, fileManager);
            if(input == 1) {

                //get the file-path from the user
                System.out.println("Enter the path of yur source file:");
                String path = scanner.nextLine();
                path = path.replaceAll("\"", "");


                String outputFilePath;
                try {
                    outputFilePath = fileEncryptor.encryptFile(path);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                System.out.println("the locations of the key file and the encrypted file :"+ outputFilePath+"\n");
            }
            else if(input == 2) {
                //get the encrypted-file-path from the user
                System.out.println("Enter the location of the encrypted source file: ");
                String encryptedFilePath = scanner.nextLine();
                encryptedFilePath = encryptedFilePath.replaceAll("\"","");

                //get the key-file-path from the user
                System.out.println("Enter the location of the key file: ");
                String keyFilePath = scanner.nextLine();
                keyFilePath = keyFilePath.replaceAll("\"","");

                try {
                    fileEncryptor.decryptFile(encryptedFilePath, keyFilePath);
                } catch (Exception e) {
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
