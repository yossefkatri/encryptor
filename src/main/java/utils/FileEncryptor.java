package utils;

import encriptionAlgorithms.IEncryptionAlgorithm;
import utils.keys.IKey;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FileEncryptor<T> {
    final IEncryptionAlgorithm<T> encryptionAlgorithm;

    final StateChangeSupport stateChangeSupport = new StateChangeSupport();

    private LocalDateTime getPeriod(LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();
        long mills = ChronoUnit.MILLIS.between(startTime, endTime);
        return Instant.ofEpochMilli(mills).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

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

    public void encryptFile(Path originalFilePath, Path outputFilePath, IKey<T> key) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        stateChangeSupport.notifyEncryptionStartedListeners(this, startTime, encryptionAlgorithm.toString(), outputFilePath, originalFilePath, key.toString(), true);

        String plainText = FileStream.readFileContent(originalFilePath);
        String cipherText = encrypt(plainText, key);


        File cipherFile = FileStream.createFile(outputFilePath);
        FileStream.saveData(cipherFile, cipherText);

        LocalDateTime period = getPeriod(startTime);
        stateChangeSupport.notifyEncryptionEndedListeners(this, period, encryptionAlgorithm.toString(), outputFilePath, originalFilePath, key.toString(), true);
    }

    public void decryptFile(Path encryptedFilePath, Path outputFilePath, IKey<T> key) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        stateChangeSupport.notifyDecryptionStartedListeners(this, startTime, encryptionAlgorithm.toString(), outputFilePath, encryptedFilePath, key.toString(), true);

        String cipherText = FileStream.readFileContent(encryptedFilePath);
        String decryptMessage = decrypt(cipherText, key);

        File decryptedFile = FileStream.createFile(outputFilePath);
        FileStream.saveData(decryptedFile, decryptMessage);

        LocalDateTime period = getPeriod(startTime);
        stateChangeSupport.notifyDecryptionEndedListeners(this, period, encryptionAlgorithm.toString(), outputFilePath, encryptedFilePath, key.toString(), true);
    }

    public StateChangeSupport getStateChangeSupport() {
        return stateChangeSupport;
    }

    public IEncryptionAlgorithm<T> getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }
}
