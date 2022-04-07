package multiThreading;

import encriptionAlgorithms.IEncryptionAlgorithm;
import utils.keys.IKey;
import utils.FileEncryptor;
import utils.FileStream;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class SyncDirectoryProcessor<T> implements IDirectoryProcessor<T> {
    FileEncryptor<T> fileEncryptor;

    private LocalDateTime getPeriod(LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();
        long mills = ChronoUnit.MILLIS.between(startTime, endTime);
        return Instant.ofEpochMilli(mills).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }

    public SyncDirectoryProcessor(FileEncryptor<T> fileEncryptor) {
        this.fileEncryptor = fileEncryptor;
    }

    @Override
    public void encryptDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {
        IEncryptionAlgorithm<T> encryptionAlgorithm = fileEncryptor.getEncryptionAlgorithm();
        LocalDateTime startTime = LocalDateTime.now();
        fileEncryptor.getStateChangeSupport().notifyEncryptionStartedListeners(this, startTime, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);

        FilenameFilter filter = (dir, name) -> name.endsWith(".txt") && !name.equals("key.txt") && new File(dir,name).isFile();
        File dir = new File(originalDirPath.toString());
        String[] files = Objects.requireNonNull(dir.list(filter));

        FileStream.createDirectory(outputDirPath);
        for (String file : files) {
                fileEncryptor.encryptFile(Paths.get(originalDirPath.toString(), file), Paths.get(outputDirPath.toString(), file), key);
        }

        LocalDateTime period = getPeriod(startTime);
        fileEncryptor.getStateChangeSupport().notifyEncryptionEndedListeners(this, period, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);
    }

    @Override
    public void decryptedDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {
        IEncryptionAlgorithm<T> encryptionAlgorithm = fileEncryptor.getEncryptionAlgorithm();
        LocalDateTime startTime = LocalDateTime.now();
        fileEncryptor.getStateChangeSupport().notifyDecryptionStartedListeners(this, startTime, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);

        FilenameFilter filter = (dir, name) -> name.endsWith(".txt") && !name.equals("key.txt") && new File(dir,name).isFile();
        File dir = new File(originalDirPath.toString());
        String[] files = Objects.requireNonNull(dir.list(filter));

        FileStream.createDirectory(outputDirPath);
        for (String file : files) {
                fileEncryptor.decryptFile(Paths.get(originalDirPath.toString(), file), Paths.get(outputDirPath.toString(), file), key);

        }

        LocalDateTime period = getPeriod(startTime);
        fileEncryptor.getStateChangeSupport().notifyDecryptionEndedListeners(this, period, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);
    }
}
