package utils;

import encriptionAlgorithms.EncryptionAlgorithm;
import encriptionAlgorithms.EncryptionOperations;
import keys.DoubleKey;
import keys.IKey;
import keys.IntKey;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileEncryptor {
    private static final int UPPER_LIMIT = 5000;
    final EncryptionAlgorithm encryptionAlgorithm;

    private IKey BuildKey(List<Integer> keys) {
        if (keys.size() == 1) {
            return new IntKey(keys.get(0));

        } else if (keys.size() == 0) {
            return null;
        }
        IKey key1;
        key1 = BuildKey(keys.subList(0, keys.size() / 2));

        IKey key2;
        key2 = BuildKey(keys.subList(keys.size() / 2, keys.size()));

        return new DoubleKey(key1, key2);
    }

    public FileEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public void encryptFile(String originalFilePath, String outputFilePath, String keyPath) throws Exception {
        String plainText;
        try {
            plainText = FileStream.getFileContent(originalFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage());
        }

        //get the necessary number of the keys
        int numKeys = EncryptionOperations.NumberOfKeys(encryptionAlgorithm);
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < numKeys; ++i) {
            //generate the key
            Random randomizer = new Random();
            int tempKey = randomizer.nextInt(UPPER_LIMIT);
            keys.add(tempKey);
        }


        IKey key = BuildKey(keys);

        //encrypt the data
        String cipherText = encrypt(encryptionAlgorithm, plainText, key);
        //create the output-files
        FileWriter keyWriter;
        FileWriter cipherWriter;
        try {
            keyWriter = FileStream.createFile(keyPath + "key.txt");
            cipherWriter = FileStream.createFile(outputFilePath);
        } catch (IOException e) {
            throw new Exception("the system can't create the file: \n" + e.getMessage());
        }

        //save the data into the files
        try {
            assert key != null;
            FileStream.saveData(keyWriter, key.toString());
            FileStream.saveData(cipherWriter, cipherText);
        } catch (IOException e) {
            throw new Exception("the system can't write into the files: \n" + e.getMessage());
        }

        //close the writer-files
        try {
            keyWriter.close();
            cipherWriter.close();
        } catch (IOException e) {
            throw new Exception("the system can't close the file: \n" + e.getMessage());
        }
    }

    public void decryptFile(String encryptedFilePath, String outputFilePath, String keyPath) throws Exception {
        String cipherText;
        try {
            cipherText = FileStream.getFileContent(encryptedFilePath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage());
        }
        List<Integer> keys;
        try {
            keys = FileStream.getKeys(keyPath);
        } catch (FileNotFoundException e) {
            throw new Exception("the system can't find the file: \n" + e.getMessage());
        }

        IKey key = BuildKey(keys);

        String decryptMessage = decrypt(encryptionAlgorithm, cipherText, key);
        FileWriter decryptedFile;
        try {
            decryptedFile = FileStream.createFile(outputFilePath);
        } catch (IOException e) {
            throw new Exception("the system can't create the file: \n" + e.getMessage());
        }
        try {
            FileStream.saveData(decryptedFile, decryptMessage);
        } catch (IOException e) {
            throw new Exception("the system can't write into the files: \n" + e.getMessage());

        }
        try {
            decryptedFile.close();
        } catch (IOException e) {
            throw new Exception("the system can't close the file: \n" + e.getMessage());
        }
    }

    public static String encrypt(EncryptionAlgorithm encryptionAlgorithm, String plaintext, IKey key) {
        StringBuilder encryptedData = new StringBuilder();
        for (int i = 0; i < plaintext.length(); ++i) {
            encryptedData.append(encryptionAlgorithm.encryptChar(String.valueOf(plaintext.charAt(i)), key));
        }
        return encryptedData.toString();
    }

    public static String decrypt(EncryptionAlgorithm encryptionAlgorithm, String plaintext, IKey key) {
        StringBuilder decryptedData = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            if (i + 1 < plaintext.length()) {
                decryptedData.append(encryptionAlgorithm.decryptChar(plaintext.substring(i, i + 2), key));
            } else {
                decryptedData.append(encryptionAlgorithm.decryptChar("" + plaintext.charAt(i) + plaintext.charAt(i), key));
                decryptedData.deleteCharAt(decryptedData.length() - 1);
            }
        }
        return decryptedData.toString();
    }

}
