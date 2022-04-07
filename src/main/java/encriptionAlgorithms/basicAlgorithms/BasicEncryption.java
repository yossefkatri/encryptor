package encriptionAlgorithms.basicAlgorithms;


import encriptionAlgorithms.EncryptionAlgorithmImpl;

public abstract class BasicEncryption extends EncryptionAlgorithmImpl<Integer> {

    public int UPPER_LIMIT;
    public BasicEncryption() {
        numberOfKeys =1;
    }
    public String getName(){
        return this.toString();
    }
}
