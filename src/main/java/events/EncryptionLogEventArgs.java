package events;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class EncryptionLogEventArgs {
    public LocalDateTime Time;
    public String encryptionAlgorithmName;
    public Path decryptedFile;
    public Path encryptedFile;
    public Path keyPath;

    @Override
    public int hashCode() {
        return Time.hashCode() + encryptedFile.hashCode() + decryptedFile.hashCode()
                +encryptedFile.hashCode() +keyPath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass())
            return false;
        EncryptionLogEventArgs encryptionLogEventArgs = (EncryptionLogEventArgs) obj;
        return Time.equals(encryptionLogEventArgs.Time)&&encryptionAlgorithmName.equals(encryptionLogEventArgs.encryptionAlgorithmName)
                &&decryptedFile.equals(encryptionLogEventArgs.decryptedFile)&&encryptedFile.equals(encryptionLogEventArgs.encryptedFile)
                &&keyPath.equals(encryptionLogEventArgs.keyPath);
    }
}
