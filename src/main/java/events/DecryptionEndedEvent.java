package events;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EventObject;

public class DecryptionEndedEvent extends EventObject {
    private LocalDateTime endTime;
    private Path inputFile;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DecryptionEndedEvent(Object source, LocalDateTime endTime, Path inputFile) {
        super(source);
        this.endTime = endTime;
        this.inputFile = inputFile;
    }
}
