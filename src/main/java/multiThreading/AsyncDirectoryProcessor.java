package multiThreading;

import encriptionAlgorithms.IEncryptionAlgorithm;
import utils.keys.IKey;
import multiThreading.Tasks.DecryptionTask;
import multiThreading.Tasks.EncryptionTask;
import utils.FileEncryptor;
import utils.FileStream;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncDirectoryProcessor<T> implements IDirectoryProcessor<T> {
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
        fileEncryptor.getStateChangeSupport().notifyEncryptionStartedListeners(
                this, startTime, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false
                    );

        FilenameFilter filter = (dir, name) -> name.endsWith(".txt") && !name.equals("key.txt") && new File(dir, name).isFile();
        File dir = new File(originalDirPath.toString());
        String[] files = Objects.requireNonNull(dir.list(filter));

        ExecutorService executorService = Executors.newFixedThreadPool(files.length);
        FileStream.createDirectory(outputDirPath);

        List<EncryptionTask<T>> tasks = new ArrayList<>();
        for (String file : files) {
            tasks.add(new EncryptionTask<>(fileEncryptor, originalDirPath, outputDirPath, key, file));
        }

        List<Future<Void>> futures = executorService.invokeAll(tasks);
        for (Future<Void> future :
                futures) {
            future.get();
        }
        executorService.shutdown();


        LocalDateTime period = getPeriod(startTime);
        fileEncryptor.getStateChangeSupport().notifyEncryptionEndedListeners(
                this, period, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false
                    );
    }

    @Override
    public void decryptedDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception {

        IEncryptionAlgorithm<T> encryptionAlgorithm = fileEncryptor.getEncryptionAlgorithm();
        LocalDateTime startTime = LocalDateTime.now();
        fileEncryptor.getStateChangeSupport().notifyDecryptionStartedListeners(
                this, startTime, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false
        );

        FilenameFilter filter = (dir, name) -> name.endsWith(".txt") && !name.equals("key.txt") && new File(dir, name).isFile();
        File dir = new File(originalDirPath.toString());
        String[] files = Objects.requireNonNull(dir.list(filter));

        ExecutorService executorService = Executors.newFixedThreadPool(files.length);
        FileStream.createDirectory(outputDirPath);

        List<DecryptionTask<T>> tasks = new ArrayList<>();
        for (String file : files) {
            tasks.add(new DecryptionTask<>(fileEncryptor, originalDirPath, outputDirPath, key, file));
        }

        List<Future<Void>> futures = executorService.invokeAll(tasks);
        for (Future<Void> future :
                futures) {
            future.get();
        }
        executorService.shutdown();


        LocalDateTime period = getPeriod(startTime);
        fileEncryptor.getStateChangeSupport().notifyDecryptionEndedListeners(
                this, period, encryptionAlgorithm.toString(), outputDirPath, originalDirPath, key.toString(), false
        );
    }
}
