import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RepeatEncryption {
    EncryptionAlgorithm encryptionAlgorithm;
    FileStream fileManager;
    int times;
    private static final int UPPER_LIMIT = 5000;
    public RepeatEncryption(EncryptionAlgorithm aEncryptionAlgorithm, FileStream aFileManager, int n ) {
        encryptionAlgorithm = aEncryptionAlgorithm;
        fileManager = aFileManager;
        times = n;
    }

    public String encryptFile(String originalFilePath) throws Exception {

        String plainText;
        try {
            plainText = fileManager.getFileContent(originalFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }

        //generate the key and calculate the output
        Random randomizer = new Random();
        String cipherText = plainText;
        StringBuilder keys = new StringBuilder();
        for (int i =0; i< times;++i) {
            int key = randomizer.nextInt(UPPER_LIMIT);
            cipherText = encryptionAlgorithm.Encrypt(cipherText, key);
            keys.append(String.valueOf(key)+"\n");
        }

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
            fileManager.saveData(keyWriter, keys.toString());
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

    public String decryptFile(String encryptedFilePath, String keyPath) throws Exception {
        String cipherText;
        try {
            cipherText = fileManager.getFileContent(encryptedFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }
        List<Integer> keys;
        try {
            keys = fileManager.getKeys(keyPath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage().toString());
        }

        String decryptMessage = cipherText;
        for(int i=times-1;i>= 0 ;--i){
            decryptMessage = encryptionAlgorithm.Decrypt(decryptMessage, keys.get(i));
        }


        String decryptedFilePath = fileManager.getFileName(encryptedFilePath, "_decrypted");
        FileWriter decryptedFile;
        try {
            decryptedFile = fileManager.createFile(decryptedFilePath);
        } catch (IOException e) {
            throw new Exception("the system can't create the file: \n" + e.getMessage().toString());
        }
        try {
            fileManager.saveData(decryptedFile, decryptMessage);
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
