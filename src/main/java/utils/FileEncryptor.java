package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import exceptions.InvalidEncryptionKeyException;
import keys.DoubleKey;
import keys.IKey;
import keys.IntKey;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileEncryptor {
    private static final int UPPER_LIMIT = 5000;
    final IEncryptionAlgorithm encryptionAlgorithm;

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

    private IKey getKeys() {
        //get the necessary number of the keys
        int numKeys = ((EncryptionAlgorithmImpl) encryptionAlgorithm).getNumberOfKeys();
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < numKeys; ++i) {
            //generate the key
            Random randomizer = new Random();
            int tempKey = randomizer.nextInt(UPPER_LIMIT);
            keys.add(tempKey);
        }
        return BuildKey(keys);
    }

    private String encrypt(String plaintext, IKey key) {
        StringBuilder encryptedData = new StringBuilder();
        for (char plainChar : plaintext.toCharArray()) {
            encryptedData.append(encryptionAlgorithm.encryptChar(plainChar, key));
        }
        return encryptedData.toString();
    }

    private String decrypt(String ciphertext, IKey key) {
        StringBuilder decryptedData = new StringBuilder();
        for (char cipherChar : ciphertext.toCharArray()) {
            decryptedData.append(encryptionAlgorithm.decryptChar(cipherChar, key));
        }
        return decryptedData.toString();
    }

    public FileEncryptor(IEncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public void encryptFile(Path originalFilePath, Path outputFilePath, Path keyPath) throws Exception {
        String plainText;
        plainText = FileStream.getFileContent(originalFilePath);

        IKey key = getKeys();


        String cipherText = encrypt(plainText, key);

        File keyFile;
        File cipherFile;


        keyFile = FileStream.createFile(Paths.get(keyPath.toString() ,"key.txt"));
        cipherFile = FileStream.createFile(outputFilePath);

        assert key != null;
        FileStream.saveData(keyFile, key.toString());
        FileStream.saveData(cipherFile, cipherText);
    }

    public void decryptFile(Path encryptedFilePath, Path outputFilePath, Path keyPath) throws Exception {
        String cipherText;
        cipherText = FileStream.getFileContent(encryptedFilePath);

        List<Integer> keys;
        keys = FileStream.getListOfIntegers(keyPath);
        int numberOfKey = ((EncryptionAlgorithmImpl)encryptionAlgorithm).numberOfKeys;
        if(numberOfKey != keys.size()) {
            throw new InvalidEncryptionKeyException("Number of keys: "+keys.size()+"  \nExpected number of key: "+numberOfKey);
        }
        IKey key = BuildKey(keys);

        String decryptMessage = decrypt(cipherText, key);
        File decryptedFile;
        decryptedFile = FileStream.createFile(outputFilePath);

        FileStream.saveData(decryptedFile, decryptMessage);
    }

}
