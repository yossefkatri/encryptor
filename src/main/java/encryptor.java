import encriptionAlgorithms.IEncryptionAlgorithm;
import encriptionAlgorithms.basicAlgorithms.XorEncryption;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import observers.EncryptionLog4JLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import utils.FileEncryptor;
import utils.FileStream;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class encryptor {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = LogManager.getLogger(encryptor.class);
        isDebug();
        while (true) {
            System.out.println("menu: \n 1: encryption \n 2: decryption");
            int input = scanner.nextInt();
            logger.debug("choice: " + input);
            scanner.nextLine();
            IEncryptionAlgorithm encryptionAlgorithm = new DoubleEncryption(new DoubleEncryption(new DoubleEncryption(XorEncryption.getInstance())));
            logger.debug("encryption operation: " + encryptionAlgorithm);
            FileEncryptor fileEncryptor = new FileEncryptor(encryptionAlgorithm);
            new EncryptionLog4JLogger(fileEncryptor.getStateChangeSupport());
            if (input == 1) {
                try {
                //get the file-path from the user
                System.out.println("Enter the path of your source file:");
                String stringPath = scanner.nextLine();
                stringPath = stringPath.replaceAll("\"", "");

                    Path path = Paths.get(stringPath);
                    //calculate the path
                    Path filesPath = path.getParent();
                    Path outputFilepath = FileStream.getFileName(path, "_encrypted");
                    logger.debug("input file path:" + path + " output file path: " + outputFilepath + " key directory:" + filesPath);
                    fileEncryptor.encryptFile(path, outputFilepath, filesPath);
                } catch (Exception e) {
                    logger.error(e);
                }
            } else if (input == 2) {
                try {
                //get the encrypted-file-path from the user
                System.out.println("Enter the location of the encrypted source file: ");
                String encryptedFilePathInput = scanner.nextLine();
                encryptedFilePathInput = encryptedFilePathInput.replaceAll("\"", "");

                //get the key-file-path from the user
                System.out.println("Enter the location of the key file: ");
                String keyPathInput = scanner.nextLine();
                keyPathInput = keyPathInput.replaceAll("\"", "");


                    Path encryptedFilePath = Paths.get(encryptedFilePathInput);
                    Path decryptedFilePath = FileStream.getFileName(encryptedFilePath, "_decrypted");
                    Path keyPath = Paths.get(keyPathInput);

                    logger.debug("input file path:" + encryptedFilePath + " output file path: " + decryptedFilePath + " key path:" + keyPath);
                    fileEncryptor.decryptFile(encryptedFilePath, decryptedFilePath, keyPath);
                } catch (Exception e) {
                    logger.error(e);
                }
            } else {
                logger.error("you should choice 1 or 2");
            }
        }
    }

    private static void isDebug() {
        boolean isDebug = java.lang.management.ManagementFactory.
                getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
        if(!isDebug) {
            Configurator.setRootLevel(Level.INFO);
        }
    }
}
