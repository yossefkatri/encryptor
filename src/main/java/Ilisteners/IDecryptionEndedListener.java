package Ilisteners;

import events.DecryptionEndedEvent;

public interface IDecryptionEndedListener {
    void decryptionEnded(DecryptionEndedEvent event);
}
