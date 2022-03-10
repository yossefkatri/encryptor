package multiThreading;

import encriptionAlgorithms.IEncryptionAlgorithm;
import keys.IKey;
import utils.FileEncryptor;
import utils.FileStream;
import utils.StateChangeSupport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class AsyncDirectoryProcessor<T> implements IDirectoryProcessor<T>{
    final private FileEncryptor<T> fileEncryptor;

    public AsyncDirectoryProcessor(FileEncryptor<T> fileEncryptor) {
        this.fileEncryptor = fileEncryptor;
    }

    private LocalDateTime getPeriod(LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();
        long mills = ChronoUnit.MILLIS.between(startTime, endTime);
        return Instant.ofEpochMilli(mills).atZone(ZoneId.of("UTC")).toLocalDateTime();
    }
    @Override
    public void encryptDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {

        IEncryptionAlgorithm<T> encryptionAlgorithm = fileEncryptor.getEncryptionAlgorithm();
        LocalDateTime startTime = LocalDateTime.now();
        fileEncryptor.getStateChangeSupport().notifyEncryptionStartedListeners(this, startTime, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);

        File dir = new File(originalDirPath.toString());
        FileStream.createDirectory(outputDirPath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile() && file.getName().contains(".txt")) {
                fileEncryptor.encryptFile(Paths.get(originalDirPath.toString(), file.getName()), Paths.get(outputDirPath.toString(), file.getName()), key);
            }
        }

        LocalDateTime period = getPeriod(startTime);
        fileEncryptor.getStateChangeSupport().notifyEncryptionEndedListeners(this, period, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false);
    }
    @Override
    public void decryptedDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {

    }
}
