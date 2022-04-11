package events;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class EncryptionLogEventArgs {
    public LocalDateTime Time;
    public String encryptionAlgorithmName;
    public Path decrypted;
    public Path encrypted;
    public String key;
    public boolean isFile;

    @Override
    public int hashCode() {
        return Time.hashCode() + encrypted.hashCode() + decrypted.hashCode()
                + encrypted.hashCode() + key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(getClass() != obj.getClass())
            return false;
        EncryptionLogEventArgs encryptionLogEventArgs = (EncryptionLogEventArgs) obj;
        return Time.equals(encryptionLogEventArgs.Time)&&encryptionAlgorithmName.equals(encryptionLogEventArgs.encryptionAlgorithmName)
                && decrypted.equals(encryptionLogEventArgs.decrypted)&& encrypted.equals(encryptionLogEventArgs.encrypted)
                && key.equals(encryptionLogEventArgs.key) && isFile == encryptionLogEventArgs.isFile;
    }
}
