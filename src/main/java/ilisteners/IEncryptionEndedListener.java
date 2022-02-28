package ilisteners;


import events.EncryptionEndedEvent;

public interface IEncryptionEndedListener {
    void encryptEnded(EncryptionEndedEvent event);
}
