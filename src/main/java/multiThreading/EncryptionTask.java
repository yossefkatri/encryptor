package multiThreading;

import keys.IKey;
import utils.FileEncryptor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class EncryptionTask<T> implements Callable<Void> {

    private final FileEncryptor<T> fileEncryptor;
    private final Path originalDirPath;
    private final Path outputDirPath;
    private final IKey<T> key;
    private final String file;

    public EncryptionTask(FileEncryptor<T> fileEncryptor, Path originalDirPath, Path outputDirPath, IKey<T> key, String file) {
        this.fileEncryptor = fileEncryptor;
        this.originalDirPath = originalDirPath;
        this.outputDirPath = outputDirPath;
        this.key = key;
        this.file = file;
    }

    @Override
    public Void call() throws Exception {
        fileEncryptor.encryptFile(Paths.get(originalDirPath.toString(), file), Paths.get(outputDirPath.toString(), file), key);
        return null;
    }
}
