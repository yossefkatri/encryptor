package utils;

import events.DecryptionEndedEvent;
import events.DecryptionStartedEvent;
import events.EncryptionEndedEvent;
import events.EncryptionStartedEvent;
import ilisteners.IDecryptionEndedListener;
import ilisteners.IDecryptionStartedListener;
import ilisteners.IEncryptionEndedListener;
import ilisteners.IEncryptionStartedListener;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StateChangeSupport {
    private List<IDecryptionStartedListener> decryptionStartedListeners = new ArrayList<>();
    private List<IDecryptionEndedListener> decryptionEndedListeners = new ArrayList<>();
    private List<IEncryptionEndedListener> encryptionEndedListeners = new ArrayList<>();
    private List<IEncryptionStartedListener> encryptionStartedListeners = new ArrayList<>();

    void addDecryptionStartedListener(IDecryptionStartedListener decryptionStartedListener) {
        decryptionStartedListeners.add(decryptionStartedListener);
    }
    void notifyDecryptionStartedListeners(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path encryptedFilePath, Path keyPath) {
        for (IDecryptionStartedListener listener :
                decryptionStartedListeners) {
            listener.decryptionStarted(new DecryptionStartedEvent(source, startTime, encryptionAlgorithmName, encryptedFilePath, keyPath));
        }
    }

    void addDecryptedEndedListener(IDecryptionEndedListener decryptionEndedListener) {
        decryptionEndedListeners.add(decryptionEndedListener);
    }
    void notifyDecryptionEndedListeners(Object source, LocalDateTime endTime, Path outputFile) {
        for (IDecryptionEndedListener listener :
                decryptionEndedListeners) {
            listener.decryptionEnded(new DecryptionEndedEvent(source, endTime, outputFile));
        }
    }

    void addEncryptedEndedListener(IEncryptionEndedListener encryptionEndedListener) {

        encryptionEndedListeners.add(encryptionEndedListener);
    }
    void notifyEncryptionEndedListeners(Object source, LocalDateTime endTime, Path encryptedFilePath, Path keyPath) {
        for (IEncryptionEndedListener listener :
                encryptionEndedListeners) {
            listener.encryptEnded(new EncryptionEndedEvent(source, endTime, encryptedFilePath, keyPath));
        }
    }

    void addEncryptionStartedListener(IEncryptionStartedListener encryptionStartedListener) {
        encryptionStartedListeners.add(encryptionStartedListener);
    }
    void notifyEncryptionStartedListeners(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path inputFile) {

        for (IEncryptionStartedListener listener :
                encryptionStartedListeners) {
            listener.encryptedStarted(new EncryptionStartedEvent(source, startTime, encryptionAlgorithmName, inputFile));
        }
    }

}
