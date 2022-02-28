package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class EncryptionStartedEvent extends EventObject {

    private LocalDateTime startTime;
    private String encryptionAlgorithmName;
    private Path input;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public EncryptionStartedEvent(Object source, LocalDateTime startTime, String EncryptionAlgorithmName, Path inputFile) {
        super(source);
        this.startTime = startTime;
        encryptionAlgorithmName = EncryptionAlgorithmName;

        this.input = inputFile;
    }
}
