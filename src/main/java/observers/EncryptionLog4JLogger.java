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
    public void decryptionStarted(DecryptionStartedEvent event) {

        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        logger.info(eventArgs.Time + ": The decryption operation " + eventArgs.encryptionAlgorithmName + " has been started with file: " + eventArgs.encryptedFile + " and key: " + eventArgs.key.replaceAll("\n"," "));
    }

    @Override
    public void decryptionEnded(DecryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        logger.info(eventArgs.Time + ": The decryption is successfully done. The decrypted file stored: " + eventArgs.decryptedFile);
    }

    @Override
    public void encryptedStarted(EncryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        logger.info(eventArgs.Time + ": The encryption operation " + eventArgs.encryptionAlgorithmName + "has been started with file: " + eventArgs.decryptedFile);
    }

    @Override
    public void encryptEnded(EncryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        logger.info(eventArgs.Time + ": The encryption is successfully done. " +
                " the encrypted file stored: " + eventArgs.decryptedFile + " and the key: " + eventArgs.key.replaceAll("\n"," "));
    }
}
