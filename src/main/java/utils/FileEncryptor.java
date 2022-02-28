package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import exceptions.InvalidEncryptionKeyException;
import keys.DoubleKey;
import keys.IKey;
import keys.IntKey;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileEncryptor {
    private static final int UPPER_LIMIT = 500;
    final IEncryptionAlgorithm encryptionAlgorithm;
    final StateChangeSupport stateChangeSupport = new StateChangeSupport();

    private IKey buildKey(List<Integer> keys) throws InvalidEncryptionKeyException {
        if (keys.size() == 1) {
            int key = keys.get(0);
            if (key < 0 || key > UPPER_LIMIT) {
                throw new InvalidEncryptionKeyException(key, "The key should be between 0 to " + UPPER_LIMIT);
            }
            return new IntKey(key);

        }
        else if (keys.size() == 0) {
            return null;
        }
        IKey key1;
        key1 = buildKey(keys.subList(0, keys.size() / 2));

        IKey key2;
        key2 = buildKey(keys.subList(keys.size() / 2, keys.size()));

        return new DoubleKey(key1, key2);
    }

    private IKey getKeys() throws Exception {
        //get the necessary number of the keys
        int numKeys = ((EncryptionAlgorithmImpl) encryptionAlgorithm).getNumberOfKeys();
        List<Integer> keys = new ArrayList<>();
        for (int i = 0; i < numKeys; ++i) {
            //generate the key
            Random randomizer = new Random();
            int tempKey = randomizer.nextInt(UPPER_LIMIT);
            keys.add(tempKey);
        }
        try {
            return buildKey(keys);
        }
        catch (InvalidEncryptionKeyException e) {
            e.addInfo("The problem is about the randomizer");
            throw e;
        }
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
        stateChangeSupport.notifyEncryptionStartedListeners(this, LocalDateTime.now(),encryptionAlgorithm.toString(),originalFilePath);

        String plainText;
        plainText = FileStream.readFileContent(originalFilePath);

        IKey key = getKeys();


        String cipherText = encrypt(plainText, key);

        File keyFile;
        File cipherFile;


        keyFile = FileStream.createFile(Paths.get(keyPath.toString(), "key.txt"));
        cipherFile = FileStream.createFile(outputFilePath);

        assert key != null;
        FileStream.saveData(keyFile, key.toString());
        FileStream.saveData(cipherFile, cipherText);

        stateChangeSupport.notifyEncryptionEndedListeners(this, LocalDateTime.now(), outputFilePath, keyPath);
    }

    public void decryptFile(Path encryptedFilePath, Path outputFilePath, Path keyPath) throws Exception {
        stateChangeSupport.notifyDecryptionStartedListeners(this,LocalDateTime.now(),encryptionAlgorithm.toString(),encryptedFilePath,keyPath);
        String cipherText = FileStream.readFileContent(encryptedFilePath);

        List<Integer> keys = FileStream.readKeys(keyPath);
        int numberOfKeys = ((EncryptionAlgorithmImpl) encryptionAlgorithm).getNumberOfKeys();
        if (numberOfKeys != keys.size()) {
            throw new InvalidEncryptionKeyException("Number of keys: " + keys.size() + "  \nExpected number of key: " + numberOfKeys);
        }
        IKey key = buildKey(keys);

        String decryptMessage = decrypt(cipherText, key);
        File decryptedFile;
        decryptedFile = FileStream.createFile(outputFilePath);

        FileStream.saveData(decryptedFile, decryptMessage);
        stateChangeSupport.notifyDecryptionEndedListeners(this,LocalDateTime.now(),outputFilePath);
    }

}
