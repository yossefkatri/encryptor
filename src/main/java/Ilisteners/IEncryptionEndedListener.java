package Ilisteners;


import events.EncryptionEndedEvent;

public interface IEncryptionEndedListener {
    void encryptEnded(EncryptionEndedEvent event);
}
