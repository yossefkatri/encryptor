package exceptions;

public class InvalidEncryptionKeyException extends Exception{
    int invalidKey;
    String errorMessage;
    boolean isThereKey;

    public InvalidEncryptionKeyException(int invalidKey, String errorMessage) {
        this.invalidKey = invalidKey;
        this.errorMessage = errorMessage;
        this.isThereKey = true;
    }

    public InvalidEncryptionKeyException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.isThereKey = false;
    }


    @Override
    public String toString() {
        if (! isThereKey)
        {
            return "InvalidEncryptionKeyException:\n Message: "+errorMessage;
        }
        return "InvalidEncryptionKeyException: \n Sent key: "+invalidKey+"\n Message: "+errorMessage;
    }

    public void addInfo(String info) {
        errorMessage += info;
    }
}
