package ilisteners;

import events.DecryptionStartedEvent;

public interface IDecryptionStartedListener {
    void decryptionStarted(DecryptionStartedEvent event);
}
