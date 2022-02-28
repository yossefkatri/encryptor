package ilisteners;

import events.DecryptionEndedEvent;

public interface IDecryptionEndedListener {
    void decryptionEnded(DecryptionEndedEvent event);
}
