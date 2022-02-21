package exceptions;

public class InvalidEncryptionKeyException extends Exception{
    Integer invalidKey;
    String errorMessage;

    public InvalidEncryptionKeyException(int invalidKey, String errorMessage) {
        this.invalidKey = invalidKey;
        this.errorMessage = errorMessage;
    }

    public InvalidEncryptionKeyException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.invalidKey = null;
    }


    @Override
    public String toString() {
        if (invalidKey == null)
        {
            return "InvalidEncryptionKeyException:\n Message: "+errorMessage;
        }
        return "InvalidEncryptionKeyException: \n Sent key: "+invalidKey+"\n Message: "+errorMessage;
    }
}
