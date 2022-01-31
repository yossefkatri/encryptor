import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileEncryptor {
    EncryptionAlgorithm encryptionAlgorithm;
    FileStream fileManager;


    private static final int UPPER_LIMIT = 5000;

    public FileEncryptor(EncryptionAlgorithm aEncryptionAlgorithm, FileStream aFileManager) {
        encryptionAlgorithm = aEncryptionAlgorithm;
        fileManager = aFileManager;
    }

    //get the original-file-path, encrypt the content and save the ciphertext and the key and return the location of these files.
    public String encryptFile(String originalFilePath) throws Exception {
        String plainText = null;
        try {
            plainText = fileManager.getFileContent(originalFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }
        //generate the key and calculate the output
        Random randomizer = new Random();
        int key = randomizer.nextInt(UPPER_LIMIT);
        String cipherText = encryptionAlgorithm.Encrypt(plainText, key);

        //calculate the path and save the output on files
        String filesPath = fileManager.getOutputFilesPath(originalFilePath);

        //create the output-files
        FileWriter keyWriter;
        FileWriter cipherWriter;
        try {
            keyWriter = fileManager.createFile(filesPath + "key.txt");
            cipherWriter = fileManager.createFile(fileManager.getFileName(originalFilePath, "_encrypted"));
        } catch (IOException e) {
            throw new Exception("the system can't create the file: \n" + e.getMessage().toString());
        }

        //save the data into the files
        try {
            fileManager.saveData(keyWriter,String.valueOf(key));
            fileManager.saveData(cipherWriter, cipherText);
        } catch (IOException e) {
            throw new Exception("the system can't write into the files: \n" + e.getMessage().toString());
        }

        //close the writer-files
        try {
            keyWriter.close();
            cipherWriter.close();
        } catch (IOException e) {
            throw new Exception("the system can't close the file: \n" + e.getMessage().toString());
        }

        return filesPath;
    }

    //get the encrypted-file-path, decrypt the content and save the plaintext  and return the location of the file.
    public String decryptFile(String encryptedFilePath, String keyPath) throws Exception {
        String cipherText;
        try {
            cipherText = fileManager.getFileContent(encryptedFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }
        int key;
        try {
            key = fileManager.getKey(keyPath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }
        String decryptMessage = encryptionAlgorithm.Decrypt(cipherText,key);
        String decryptedFilePath =fileManager.getFileName(encryptedFilePath,"_decrypted");
        FileWriter decryptedFile;
        try {
            decryptedFile = fileManager.createFile(decryptedFilePath);
        } catch (IOException e) {
            throw new Exception("the system can't create the file: \n" + e.getMessage().toString());
        }
        try {
            fileManager.saveData(decryptedFile,decryptMessage);
        } catch (IOException e) {
            throw new Exception("the system can't write into the files: \n" + e.getMessage().toString());
        }
        try {
            decryptedFile.close();
        } catch (IOException e) {
            throw new Exception("the system can't close the file: \n" + e.getMessage().toString());
        }
        return decryptedFilePath;
    }
}
