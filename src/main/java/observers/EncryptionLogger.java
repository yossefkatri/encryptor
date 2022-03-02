package observers;


import events.*;
import ilisteners.IDecryptionEndedListener;
import ilisteners.IDecryptionStartedListener;
import ilisteners.IEncryptionEndedListener;
import ilisteners.IEncryptionStartedListener;
import utils.StateChangeSupport;

public class EncryptionLogger implements IDecryptionEndedListener, IEncryptionEndedListener, IEncryptionStartedListener, IDecryptionStartedListener {

    public EncryptionLogger(StateChangeSupport stateChangeSupport) {
        stateChangeSupport.addDecryptionStartedListener(this);
        stateChangeSupport.addDecryptedEndedListener(this);
        stateChangeSupport.addEncryptionStartedListener(this);
        stateChangeSupport.addEncryptedEndedListener(this);
    }

    @Override
    public void decryptionStarted(DecryptionStartedEvent event) {

        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        System.out.println(eventArgs.Time + ": The decryption operation " + eventArgs.encryptionAlgorithmName + " has been started with file: " + eventArgs.encryptedFile + " and key: " + eventArgs.keyPath);
    }

    @Override
    public void decryptionEnded(DecryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        System.out.println(eventArgs.Time + ": The decryption is successfully done. The decrypted file stored: " + eventArgs.decryptedFile);
    }

    @Override
    public void encryptedStarted(EncryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        System.out.println(eventArgs.Time + ": The encryption operation " + eventArgs.encryptionAlgorithmName + "has been started with file: " + eventArgs.decryptedFile);
    }

    @Override
    public void encryptEnded(EncryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        System.out.println(eventArgs.Time + ": The encryption is successfully done. " +
                " the encrypted file stored: " + eventArgs.decryptedFile + " and the key stored: " + eventArgs.keyPath);
    }
}
