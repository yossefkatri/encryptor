package utils;

import events.DecryptionEndedEvent;
import events.DecryptionStartedEvent;
import events.EncryptionEndedEvent;
import events.EncryptionStartedEvent;
import Ilisteners.IDecryptionEndedListener;
import Ilisteners.IDecryptionStartedListener;
import Ilisteners.IEncryptionEndedListener;
import Ilisteners.IEncryptionStartedListener;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StateChangeSupport {
    private final List<IDecryptionStartedListener> decryptionStartedListeners = new ArrayList<>();
    private final List<IDecryptionEndedListener> decryptionEndedListeners = new ArrayList<>();
    private final List<IEncryptionEndedListener> encryptionEndedListeners = new ArrayList<>();
    private final List<IEncryptionStartedListener> encryptionStartedListeners = new ArrayList<>();

    public void addDecryptionStartedListener(IDecryptionStartedListener decryptionStartedListener) {
        decryptionStartedListeners.add(decryptionStartedListener);
    }

    public void notifyDecryptionStartedListeners(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path decryptedFilePath, Path encryptedFilePath, String key, Boolean isFile) {
        decryptionStartedListeners.forEach(
                iDecryptionStartedListener -> iDecryptionStartedListener.decryptionStarted(
                        new DecryptionStartedEvent(
                                source, startTime, encryptionAlgorithmName, decryptedFilePath, encryptedFilePath, key, isFile
                        )
                )
        );
    }

    public void addDecryptedEndedListener(IDecryptionEndedListener decryptionEndedListener) {
        decryptionEndedListeners.add(decryptionEndedListener);
    }

    public void notifyDecryptionEndedListeners(Object source, LocalDateTime endTime, String encryptionAlgorithmName, Path decryptedFilePath, Path encryptedFilePath, String key, Boolean isFile) {
        decryptionEndedListeners.forEach(
                iDecryptionEndedListener -> iDecryptionEndedListener.decryptionEnded(
                        new DecryptionEndedEvent(
                                source, endTime, encryptionAlgorithmName, decryptedFilePath, encryptedFilePath, key, isFile
                        )
                )
        );
    }

    public void addEncryptedEndedListener(IEncryptionEndedListener encryptionEndedListener) {

        encryptionEndedListeners.add(encryptionEndedListener);
    }

    public void notifyEncryptionEndedListeners(Object source, LocalDateTime endTime, String encryptionAlgorithmName, Path decryptedFilePath, Path encryptedFilePath, String key, Boolean isFile) {
        encryptionEndedListeners.forEach(
                iEncryptionEndedListener -> iEncryptionEndedListener.encryptEnded(
                        new EncryptionEndedEvent(
                                source, endTime, encryptionAlgorithmName, decryptedFilePath, encryptedFilePath, key, isFile
                        )
                )
        );
    }

    public void addEncryptionStartedListener(IEncryptionStartedListener encryptionStartedListener) {
        encryptionStartedListeners.add(encryptionStartedListener);
    }

    public void notifyEncryptionStartedListeners(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path decryptedFilePath, Path encryptedFilePath, String key, Boolean isFile) {
        encryptionStartedListeners.forEach(
                iEncryptionStartedListener -> iEncryptionStartedListener.encryptedStarted(
                        new EncryptionStartedEvent(
                                source, startTime, encryptionAlgorithmName, decryptedFilePath, encryptedFilePath, key, isFile
                        )
                )
        );
    }

}
