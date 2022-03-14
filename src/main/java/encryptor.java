import encriptionAlgorithms.IEncryptionAlgorithm;
import encriptionAlgorithms.basicAlgorithms.XorEncryption;
import encriptionAlgorithms.complexAlgorithm.DoubleEncryption;
import keys.IKey;
import multiThreading.AsyncDirectoryProcessor;
import multiThreading.IDirectoryProcessor;
import multiThreading.SyncDirectoryProcessor;
import observers.EncryptionLog4JLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import utils.FileEncryptor;
import utils.FileStream;
import utils.KeyManager;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class encryptor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = LogManager.getLogger(encryptor.class);
        isDebug();
        while (true) {
            System.out.println("menu: \n 1: encryption file \n 2: decryption file \n 3: encryption directory \n 4: decryption directory");
            int input = scanner.nextInt();
            logger.debug("choice: " + input);
            scanner.nextLine();
            IEncryptionAlgorithm<Integer> encryptionAlgorithm = new DoubleEncryption<>(new DoubleEncryption<>(new DoubleEncryption<>(XorEncryption.getInstance())));
            logger.debug("encryption operation: " + encryptionAlgorithm);
            FileEncryptor<Integer> fileEncryptor = new FileEncryptor<>(encryptionAlgorithm);
            KeyManager keyManager = new KeyManager(encryptionAlgorithm);
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

                    logger.debug("generate keys for encryption");
                    IKey<Integer> key = keyManager.getKeys();
                    logger.debug("input file path:" + path + " output file path: " + outputFilepath + " key content: " + key);
                    fileEncryptor.encryptFile(path, outputFilepath, key);

                    logger.debug("start saving key operation with path:" + filesPath + "and key: " + key);
                    Path keyPath = keyManager.saveKey(filesPath, key);
                    logger.info("save key file successfully in " + keyPath);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            else if (input == 2) {
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

                    logger.debug("start extract key from file: " + keyPath);
                    List<Integer> keys = FileStream.readKeys(keyPath);
                    logger.debug("finish to read all of the data from the key file");
                    logger.debug("start checking the validation of the keys");
                    keyManager.checkKeys(keys);
                    logger.debug("all keys are validations");
                    logger.debug("create structure of keys");
                    IKey<Integer> key = keyManager.buildKey(keys);
                    logger.debug("finish to create the structure keys");
                    fileEncryptor.decryptFile(encryptedFilePath, decryptedFilePath, key);
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
            else if (input == 3) {
                try {
                    //get the file-path from the user
                    System.out.println("Enter the path of your directory:");
                    String stringPath = scanner.nextLine();
                    stringPath = stringPath.replaceAll("\"", "");

                    Path dirPath = FileStream.getDirPath(stringPath);

                    Path outputPath = FileStream.getDirectoryOutput(dirPath.getParent(), "encrypted");
                    logger.debug("input file path:" + dirPath + " output file path: " + outputPath + " key directory:" + outputPath);

                    logger.debug("generate keys for encryption");
                    IKey<Integer> key = keyManager.getKeys();
                    IDirectoryProcessor<Integer> iDirectoryProcessor = getIDirectoryProcessor(scanner, logger, input, fileEncryptor);
                    iDirectoryProcessor.encryptDirectory(dirPath, outputPath, key);
                    Path keyPath = keyManager.saveKey(outputPath, key);
                    logger.info("save key file successfully in " + keyPath);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
            else if (input == 4) {
                try {
                    //get the encrypted-file-path from the user
                    System.out.println("Enter the location of the encrypted source Directory: ");
                    String encryptedDirPathInput = scanner.nextLine();
                    encryptedDirPathInput = encryptedDirPathInput.replaceAll("\"", "");

                    //get the key-file-path from the user
                    System.out.println("Enter the location of the key file: ");
                    String keyPathInput = scanner.nextLine();
                    keyPathInput = keyPathInput.replaceAll("\"", "");


                    Path encryptedDirPath = Paths.get(encryptedDirPathInput);
                    Path decryptedDirPath = FileStream.getDirectoryOutput(encryptedDirPath.getParent(), "decrypted");
                    Path keyPath = Paths.get(keyPathInput);

                    logger.debug("input Directory path:" + encryptedDirPath + " output Directory path: " + decryptedDirPath + " key path:" + keyPath);

                    logger.debug("start extract key from file: " + keyPath);
                    List<Integer> keys = FileStream.readKeys(keyPath);
                    logger.debug("finish to read all of the data from the key file");
                    logger.debug("start checking the validation of the keys");
                    keyManager.checkKeys(keys);
                    logger.debug("all keys are validations");
                    logger.debug("create structure of keys");
                    IKey<Integer> key = keyManager.buildKey(keys);
                    logger.debug("finish to create the structure keys");

                    IDirectoryProcessor<Integer> iDirectoryProcessor = getIDirectoryProcessor(scanner, logger, input, fileEncryptor);

                    iDirectoryProcessor.decryptedDirectory(encryptedDirPath, decryptedDirPath, key);

                } catch (Exception e) {
                    logger.error(e);
                }
            }
            else {
                logger.error("you should choice between 1-4");
            }
        }
    }

    private static IDirectoryProcessor<Integer> getIDirectoryProcessor(Scanner scanner, Logger logger, int input, FileEncryptor<Integer> fileEncryptor) throws Exception {
        System.out.println("menu: \n 1: Synchronize \n 2: Asynchronous");
        int choice = scanner.nextInt();
        logger.debug("choice: " + input);
        scanner.nextLine();
        IDirectoryProcessor<Integer> iDirectoryProcessor;
        if (choice == 1) {
            iDirectoryProcessor = new SyncDirectoryProcessor<>(fileEncryptor);
            logger.debug("the sync operation is chosen");
        } else if (choice == 2) {
            iDirectoryProcessor = new AsyncDirectoryProcessor<>(fileEncryptor);
            logger.debug("the Async operation is chosen");
        } else {
            throw new Exception("you should choice between 1-2");
        }
        return iDirectoryProcessor;
    }

    private static void isDebug() {
        boolean isDebug = java.lang.management.ManagementFactory.
                getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
        if (!isDebug) {
            Configurator.setRootLevel(Level.INFO);
        }
    }

}
