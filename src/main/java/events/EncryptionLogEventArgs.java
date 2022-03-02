package events;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class EncryptionLogEventArgs {
    public LocalDateTime Time;
    public String encryptionAlgorithmName;
    public Path decryptedFile;
    public Path encryptedFile;
    public Path keyPath;

}
