package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class EncryptionEndedEvent extends EventObject {

    private final EncryptionLogEventArgs encryptionLogEventArgs;

    /**
     * Constructs an encryption ended event
     * @param source The object on which the Event initially occurred.
     * @param key the used key to encrypt
     * @param decryptedFile output file of encryption
     * @param encryptedFile input file of encryption
     * @param encryptionAlgorithmName the name of the encryption algorithm that has been used
     * @param endTime the duration of the algorithm
     * @param isFile 1 == file , 0 == directory
     * @throws IllegalArgumentException if source is null.
     */
    public EncryptionEndedEvent(Object source, LocalDateTime endTime, String encryptionAlgorithmName ,Path decryptedFile, Path encryptedFile, String key, Boolean isFile) {
        super(source);
        encryptionLogEventArgs = new EncryptionLogEventArgs();
        encryptionLogEventArgs.Time = endTime;
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
