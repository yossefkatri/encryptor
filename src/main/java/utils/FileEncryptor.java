package utils;

import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.IKey;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

public class FileEncryptor<T> {
    final IEncryptionAlgorithm<T> encryptionAlgorithm;
    final StateChangeSupport stateChangeSupport = new StateChangeSupport();

    private String encrypt(String plaintext, IKey<T> key) {
        StringBuilder encryptedData = new StringBuilder();
        for (char plainChar : plaintext.toCharArray()) {
            encryptedData.append(encryptionAlgorithm.encryptChar(plainChar, key));
        }
        return encryptedData.toString();
    }

    private String decrypt(String ciphertext, IKey<T> key) {
        StringBuilder decryptedData = new StringBuilder();
        for (char cipherChar : ciphertext.toCharArray()) {
            decryptedData.append(encryptionAlgorithm.decryptChar(cipherChar, key));
        }
        return decryptedData.toString();
    }

    public FileEncryptor(IEncryptionAlgorithm<T> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public void encryptDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {
        File dir = new File(originalDirPath.toString());
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile() && file.getName().contains(".txt")) {
                // TODO: 3/9/2022 encrypt the file
                encryptFile(Paths.get(originalDirPath.toString(), file.getName()), Paths.get(outputDirPath.toString(), file.getName()), key);
            }
        }
    }

    public void encryptFile(Path originalFilePath, Path outputFilePath, IKey<T> key) throws Exception {
        stateChangeSupport.notifyEncryptionStartedListeners(this, LocalDateTime.now(), encryptionAlgorithm.toString(), outputFilePath, originalFilePath, key.toString());

        String plainText = FileStream.readFileContent(originalFilePath);
        String cipherText = encrypt(plainText, key);


        File cipherFile = FileStream.createFile(outputFilePath);
        FileStream.saveData(cipherFile, cipherText);

        stateChangeSupport.notifyEncryptionEndedListeners(this, LocalDateTime.now(), encryptionAlgorithm.toString(), outputFilePath, originalFilePath, key.toString());
    }

    public void decryptFile(Path encryptedFilePath, Path outputFilePath, IKey<T> key) throws Exception {
        stateChangeSupport.notifyDecryptionStartedListeners(this, LocalDateTime.now(), encryptionAlgorithm.toString(), outputFilePath, encryptedFilePath, key.toString());

        String cipherText = FileStream.readFileContent(encryptedFilePath);
        String decryptMessage = decrypt(cipherText, key);

        File decryptedFile = FileStream.createFile(outputFilePath);
        FileStream.saveData(decryptedFile, decryptMessage);

        stateChangeSupport.notifyDecryptionEndedListeners(this, LocalDateTime.now(), encryptionAlgorithm.toString(), outputFilePath, encryptedFilePath, key.toString());
    }

    public StateChangeSupport getStateChangeSupport() {
        return stateChangeSupport;
    }

}
