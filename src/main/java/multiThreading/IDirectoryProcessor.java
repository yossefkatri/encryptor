package multiThreading;

import utils.keys.IKey;

import java.nio.file.Path;

public interface IDirectoryProcessor<T> {
    void encryptDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception;

    void decryptedDirectory(Path originalDirPath, Path outputDirPath, IKey<T> key) throws Exception;
}