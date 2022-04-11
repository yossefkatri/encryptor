package observers;


import events.*;
import Ilisteners.IDecryptionEndedListener;
import Ilisteners.IDecryptionStartedListener;
import Ilisteners.IEncryptionEndedListener;
import Ilisteners.IEncryptionStartedListener;
import utils.StateChangeSupport;

import java.time.ZoneId;

public class EncryptionLogger implements IDecryptionEndedListener, IEncryptionEndedListener, IEncryptionStartedListener, IDecryptionStartedListener {

    public EncryptionLogger(StateChangeSupport stateChangeSupport) {
        stateChangeSupport.addDecryptionStartedListener(this);
        stateChangeSupport.addDecryptedEndedListener(this);
        stateChangeSupport.addEncryptionStartedListener(this);
        stateChangeSupport.addEncryptedEndedListener(this);
    }

    @Override
    public  void decryptionStarted(DecryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }

        System.out.println(eventArgs.Time + ": The decryption operation " + eventArgs.encryptionAlgorithmName + " has been started with " + name + ": " + eventArgs.encrypted + " and key: " + eventArgs.key.replaceAll("\n", " "));
    }

    @Override
    public  void decryptionEnded(DecryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        System.out.println("The decryption is successfully done and takes" + eventArgs.Time.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli() + " milliseconds.The decrypted " + name + " stored: " + eventArgs.decrypted);
    }

    @Override
    public  void encryptedStarted(EncryptionStartedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        System.out.println(eventArgs.Time + ": The encryption operation " + eventArgs.encryptionAlgorithmName + "has been started with " + name + ": " + eventArgs.encrypted);
    }

    @Override
    public  void encryptEnded(EncryptionEndedEvent event) {
        EncryptionLogEventArgs eventArgs = event.getEncryptionLogEventArgs();
        String name = "file";
        if (!eventArgs.isFile) {
            name = "directory";
        }
        System.out.println(" The encryption is successfully done and takes " + eventArgs.Time.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli() + " milliseconds. " +
                " the encrypted " + name + " stored: " + eventArgs.decrypted + " and the key: " + eventArgs.key.replaceAll("\n", " "));
    }
}
