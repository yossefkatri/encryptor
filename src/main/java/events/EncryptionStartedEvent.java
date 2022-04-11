package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class EncryptionStartedEvent extends EventObject {

    private final EncryptionLogEventArgs encryptionLogEventArgs;

    /**
     * Constructs an encryption Started event
     *
     * @param source The object on which the Event initially occurred.
     * @param key the used key to encrypt
     * @param decryptedFile output file of encryption
     * @param encryptedFile input file of encryption
     * @param encryptionAlgorithmName the name of the encryption algorithm that has been used
     * @param startTime the start-time of the algorithm
     * @param isFile 1 == file , 0 == directory
     * @throws IllegalArgumentException if source is null.
     */
    public EncryptionStartedEvent(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path decryptedFile, Path encryptedFile, String key, Boolean isFile) {
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
