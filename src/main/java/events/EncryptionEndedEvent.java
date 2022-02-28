package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class EncryptionEndedEvent extends EventObject {
    private LocalDateTime endTime;
    private Path encryptedFilePath;
    private Path keyPath;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EncryptionEndedEvent(Object source, LocalDateTime endTime, Path EncryptedFilePath, Path KeyPath) {
        super(source);
        this.endTime = endTime;
        encryptedFilePath = EncryptedFilePath;
        keyPath = KeyPath;
    }
}
