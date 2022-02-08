package encriptionAlgorithms;

import encriptionAlgorithms.basicAlgorithms.BasicEncryption;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import encriptionAlgorithms.complexAlgorithm.RepeatEncryption;

public class OperationsEncryption {
     public static int NumberOfKeys(EncryptionAlgorithm encryptionAlgorithm)
     {
         if(encryptionAlgorithm instanceof BasicEncryption) {
             return 1;
         }
         else if(encryptionAlgorithm instanceof RepeatEncryption) {
             return NumberOfKeys(((RepeatEncryption) encryptionAlgorithm).getEncryptionAlgorithm());
         }
         else if(encryptionAlgorithm instanceof DoubleEncryption) {
             return 2*NumberOfKeys(((DoubleEncryption) encryptionAlgorithm).getEncryptionAlgorithm());
         }
         return 0;
     }
}
