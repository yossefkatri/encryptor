package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class DecryptionStartedEvent extends EventObject {
    private final EncryptionLogEventArgs encryptionLogEventArgs;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DecryptionStartedEvent(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path decryptedFile, Path encryptedFile, String key, Boolean isFile) {
        super(source);
        encryptionLogEventArgs = new EncryptionLogEventArgs();
        encryptionLogEventArgs.Time = startTime;
        encryptionLogEventArgs.encryptionAlgorithmName = encryptionAlgorithmName;
        encryptionLogEventArgs.decrypted = decryptedFile;
        encryptionLogEventArgs.encrypted = encryptedFile;
        encryptionLogEventArgs.key = key;
        encryptionLogEventArgs.isFile = isFile;
    }

    public EncryptionLogEventArgs getEncryptionLogEventArgs() {
        return encryptionLogEventArgs;
    }
}
