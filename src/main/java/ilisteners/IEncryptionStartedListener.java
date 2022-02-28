package ilisteners;

import events.EncryptionStartedEvent;

public interface IEncryptionStartedListener {
    void encryptedStarted(EncryptionStartedEvent event);
}
