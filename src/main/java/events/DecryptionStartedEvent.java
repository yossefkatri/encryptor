package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class DecryptionStartedEvent extends EventObject {
    private LocalDateTime startTime;
    private String encryptionAlgorithmName;
    private Path encryptedFilePath;
    private Path keyPath;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DecryptionStartedEvent(Object source, LocalDateTime startTime, String encryptionAlgorithmName, Path encryptedFilePath, Path keyPath) {
        super(source);
        this.startTime = startTime;
        this.encryptionAlgorithmName = encryptionAlgorithmName;
        this.encryptedFilePath = encryptedFilePath;
        this.keyPath = keyPath;
    }
}
