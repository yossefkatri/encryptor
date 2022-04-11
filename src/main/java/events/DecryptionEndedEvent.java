package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class DecryptionEndedEvent extends EventObject {
    private final EncryptionLogEventArgs encryptionLogEventArgs;

    /**
     * Constructs a decryption ended event
     *
     * @param source The object on which the Event initially occurred.
     * @param key the used key to decrypt
     * @param decryptedFile output file of decryption
     * @param encryptedFile input file of decryption
     * @param encryptionAlgorithmName the name of the decryption algorithm that has been used
     * @param endTime the duration of the algorithm
     * @param isFile 1 == file , 0 == directory
     * @throws IllegalArgumentException if source is null.
     */
    public DecryptionEndedEvent(Object source, LocalDateTime endTime, String encryptionAlgorithmName, Path decryptedFile, Path encryptedFile, String key, Boolean isFile) {
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
