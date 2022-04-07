import encriptionAlgorithms.IEncryptionAlgorithm;
import utils.keys.IKey;
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
import utils.settingsUtils.JsonStream;
import utils.settingsUtils.SettingsInfo;
import utils.settingsUtils.SettingsStream;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class encryptor {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(encryptor.class);
        isDebug();
        System.out.println("menu: \n1: change settings \nelse:  run");
        SettingsStream<Integer> jsonStream;
        try {
            jsonStream = new JsonStream<>("info.json");
        } catch (IOException e) {
            logger.error(e);
            return;
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 1) {
            try {
                writeToSettings(jsonStream);
            } catch (Exception e) {
                logger.error(e);
            }
            return;
        }
        try {
            SettingsInfo<Integer> settingsInfo = readFromSettings(jsonStream);
            logger.debug("choice: " + settingsInfo.getChoice());
            logger.debug("input file: " + settingsInfo.getSourcePath());
            logger.debug("output file: " + settingsInfo.getOutputPath());
            logger.debug("key file: " + settingsInfo.getKeyPath());
            logger.debug("encryption operation:" + settingsInfo.getNameEncryptionAlgorithm());
            logger.debug("times: " + Arrays.toString(settingsInfo.getTimes()));
            IEncryptionAlgorithm<Integer> encryptionAlgorithm = settingsInfo.getEncryptionAlgorithm();
            FileEncryptor<Integer> fileEncryptor = new FileEncryptor<>(encryptionAlgorithm);
            KeyManager keyManager = new KeyManager(encryptionAlgorithm);
            new EncryptionLog4JLogger(fileEncryptor.getStateChangeSupport());
            int input = settingsInfo.getChoice();
            switch (input)
            {
                case 1: {
                    Path sourcePath = settingsInfo.getSourcePath();
                    //calculate the path
                    Path filesPath = sourcePath.getParent();
                    Path outputFilepath = FileStream.getFileName(sourcePath, "_encrypted");

                    logger.debug("generate keys for encryption");
                    IKey<Integer> key = keyManager.getKeys();
                    logger.debug("output file path: " + outputFilepath + " key content: " + key);
                    fileEncryptor.encryptFile(sourcePath, outputFilepath, key);

                    logger.debug("start saving key  in dir path:" + filesPath + " and key: " + key);
                    Path keyPath = keyManager.saveKey(filesPath, key);
                    logger.info("save key file successfully in " + keyPath);

                    settingsInfo.setKeyPath(keyPath);
                    settingsInfo.setOutputPath(outputFilepath);
                    logger.debug("change key and output path on the settings");
                    jsonStream.writeData(settingsInfo);
                    break;
                }
                case 2: {
                    Path encryptedFilePath = settingsInfo.getSourcePath();
                    Path decryptedFilePath = FileStream.getFileName(encryptedFilePath, "_decrypted");
                    Path keyPath = settingsInfo.getKeyPath();

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

                    settingsInfo.setOutputPath(decryptedFilePath);
                    logger.debug("change output file path on the settings");
                    jsonStream.writeData(settingsInfo);

                    break;
                }
                case 3: {
                    Path dirPath = FileStream.getDirPath(settingsInfo.getSourcePath().toString());

                    Path outputPath = FileStream.getDirectoryOutput(dirPath.getParent(), "encrypted");
                    logger.debug("input directory path:" + dirPath + " output directory path: " + outputPath + " key directory:" + outputPath);

                    logger.debug("generate keys for encryption");
                    IKey<Integer> key = keyManager.getKeys();
                    IDirectoryProcessor<Integer> iDirectoryProcessor = new SyncDirectoryProcessor<>(fileEncryptor);
                    iDirectoryProcessor.encryptDirectory(dirPath, outputPath, key);
                    Path keyPath = keyManager.saveKey(outputPath, key);
                    logger.info("save key file successfully in " + keyPath);

                    settingsInfo.setKeyPath(keyPath);
                    settingsInfo.setOutputPath(outputPath);
                    logger.debug("change key and output path on the settings");
                    jsonStream.writeData(settingsInfo);
                    break;
                }
                case 4: {
                    Path encryptedDirPath = settingsInfo.getSourcePath();
                    Path decryptedDirPath = FileStream.getDirectoryOutput(encryptedDirPath.getParent(), "decrypted");
                    Path keyPath = settingsInfo.getKeyPath();

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

                    IDirectoryProcessor<Integer> iDirectoryProcessor = new SyncDirectoryProcessor<>(fileEncryptor);

                    iDirectoryProcessor.decryptedDirectory(encryptedDirPath, decryptedDirPath, key);

                    settingsInfo.setOutputPath(decryptedDirPath);
                    logger.debug("change output directory path on the settings");
                    jsonStream.writeData(settingsInfo);
                    break;
                }
                case 5: {
                    Path dirPath = FileStream.getDirPath(settingsInfo.getSourcePath().toString());

                    Path outputPath = FileStream.getDirectoryOutput(dirPath.getParent(), "encrypted");
                    logger.debug("input file path:" + dirPath + " output file path: " + outputPath + " key directory:" + outputPath);

                    logger.debug("generate keys for encryption");
                    IKey<Integer> key = keyManager.getKeys();
                    IDirectoryProcessor<Integer> iDirectoryProcessor = new AsyncDirectoryProcessor<>(fileEncryptor);
                    iDirectoryProcessor.encryptDirectory(dirPath, outputPath, key);
                    Path keyPath = keyManager.saveKey(outputPath, key);
                    logger.info("save key file successfully in " + keyPath);

                    settingsInfo.setKeyPath(keyPath);
                    settingsInfo.setOutputPath(outputPath);
                    logger.debug("change key and output path on the settings");
                    jsonStream.writeData(settingsInfo);
                    break;
                }
                case 6: {
                    Path encryptedDirPath = settingsInfo.getSourcePath();
                    Path decryptedDirPath = FileStream.getDirectoryOutput(encryptedDirPath.getParent(), "decrypted");
                    Path keyPath = settingsInfo.getKeyPath();

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

                    IDirectoryProcessor<Integer> iDirectoryProcessor = new AsyncDirectoryProcessor<>(fileEncryptor);

                    iDirectoryProcessor.decryptedDirectory(encryptedDirPath, decryptedDirPath, key);

                    settingsInfo.setOutputPath(decryptedDirPath);
                    logger.debug("change output directory path on the settings");
                    jsonStream.writeData(settingsInfo);
                    break;
                }
                default:
                    logger.error("you should choice between 1-6");
                    break;
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void isDebug() {
        boolean isDebug = java.lang.management.ManagementFactory.
                getRuntimeMXBean().
                getInputArguments().toString().contains("jdwp");
        if (!isDebug) {
            Configurator.setRootLevel(Level.INFO);
        }
    }

    private static void writeToSettings(SettingsStream<Integer> jsonStream) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("if you want to skip press -1\n");
        SettingsInfo<Integer> settingsInfo = jsonStream.readData();
        System.out.println("menu: \n 1: encryption file \n 2: decryption file \n 3: encryption directory Sync \n 4: decryption directory Sync \n 5: encryption directory  Async\n 6: decryption directory Async");
        int input = scanner.nextInt();
        if (input != -1) {
            settingsInfo.setChoice(input);
        }
        scanner.nextLine();

        //get the input-file-path from the user
        System.out.println("Enter the path of your source file:");
        String stringPath = scanner.nextLine();
        stringPath = stringPath.replaceAll("\"", "");
        if (!stringPath.equals("-1")) {
            settingsInfo.setSourcePath(Paths.get(stringPath));
        }

        //get the output-file-path from the user
        System.out.println("Enter the location of the output file: ");
        String outputFilePath = scanner.nextLine();
        outputFilePath = outputFilePath.replaceAll("\"", "");
        if(!outputFilePath.equals("-1")) {
            settingsInfo.setOutputPath(Paths.get(outputFilePath));
        }
        //get the key-file-path from the user
        System.out.println("Enter the location of the key file: ");
        String keyPath = scanner.nextLine();
        keyPath = keyPath.replaceAll("\"", "");
        if (!keyPath.equals("-1")){
            settingsInfo.setKeyPath(Paths.get(keyPath));
        }

        System.out.println("Enter the structure of the encryption: \nformat: RepeatEncryption{RepeatEncryption{{XorEncryption}}}");
        String nameEncryption = scanner.nextLine();
        if (!nameEncryption.equals("-1")) {
            settingsInfo.setNameEncryptionAlgorithm(nameEncryption);
        }
        System.out.println("(in case if you have repeat encryption, if you don't have press 0) enter the times of the repeat algorithms in reverse order(the last algorithm to the first):");
        try {

            String stringNumbers = scanner.nextLine();
            if(!stringNumbers.equals("-1")) {
                String[] numbers = stringNumbers.split(",");
                int[] times = new int[numbers.length];
                for (int i = 0; i < numbers.length; ++i) {
                    times[i] = Integer.parseInt(numbers[i]);
                }
                settingsInfo.setTimes(times);
            }
        } catch (Exception ignored) {
            throw new Exception("you should write in this format 1,3 ,4,53,3");
        }

        jsonStream.writeData(settingsInfo);
    }

    private static SettingsInfo<Integer> readFromSettings(SettingsStream<Integer> jsonStream) throws Exception {
        SettingsInfo<Integer> settingsInfo = jsonStream.readData();
        settingsInfo.ToEncryptionAlgorithm();
        return settingsInfo;
    }
}
