package observers;

import events.*;
import ilisteners.IDecryptionEndedListener;
import ilisteners.IDecryptionStartedListener;
import ilisteners.IEncryptionEndedListener;
import ilisteners.IEncryptionStartedListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.FileEncryptor;
import utils.StateChangeSupport;

import java.time.ZoneId;

public class EncryptionLog4JLogger implements IDecryptionEndedListener, IEncryptionEndedListener, IDecryptionStartedListener, IEncryptionStartedListener {
    private final Logger logger;

    public EncryptionLog4JLogger(StateChangeSupport stateChangeSupport) {
        this.logger = LogManager.getLogger(FileEncryptor.class);
        stateChangeSupport.addEncryptionStartedListener(this);
        stateChangeSupport.addDecryptedEndedListener(this);
        stateChangeSupport.addEncryptedEndedListener(this);
        stateChangeSupport.addDecryptionStartedListener(this);
    }

    @Override
    public synchronized void decryptionStarted(DecryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }

        logger.info(eventArgs.Time + ": The decryption operation " + eventArgs.encryptionAlgorithmName + " has been started with " + name + ": " + eventArgs.encrypted + " and key: " + eventArgs.key.replaceAll("\n", " "));
    }

    @Override
    public synchronized void decryptionEnded(DecryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        logger.info("The decryption is successfully done and takes " + eventArgs.Time.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli() + " milliseconds.The decrypted " + name + " stored: " + eventArgs.decrypted);
    }

    @Override
    public synchronized void encryptedStarted(EncryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        logger.info(eventArgs.Time + ": The encryption operation " + eventArgs.encryptionAlgorithmName + "has been started with " + name + ": " + eventArgs.encrypted);
    }

    @Override
    public synchronized void encryptEnded(EncryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        logger.info(" The encryption is successfully done and takes " + eventArgs.Time.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli() + " milliseconds. " +
                " the encrypted " + name + " stored: " + eventArgs.decrypted + " and the key: " + eventArgs.key.replaceAll("\n", " "));
    }
}
