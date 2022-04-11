package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import encriptionAlgorithms.IEncryptionAlgorithm;
import exceptions.InvalidEncryptionKeyException;
import utils.keys.BasicKey;
import utils.keys.DoubleKey;
import utils.keys.IKey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KeyManager {
    private static final int UPPER_LIMIT = 500;
    IEncryptionAlgorithm<?> encryptionAlgorithm;

    public KeyManager(IEncryptionAlgorithm<?> encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public IKey<Integer> buildKey(List<Integer> keys) {
        if (keys.size() == 1) {
            int key = keys.get(0);
            return new BasicKey<>(key);

        } else if (keys.size() == 0) {
            return null;
        }
        IKey<Integer> key1;
        key1 = buildKey(keys.subList(0, keys.size() / 2));

        IKey<Integer> key2;
        key2 = buildKey(keys.subList(keys.size() / 2, keys.size()));

        return new DoubleKey<>(key1, key2);
    }

    public void checkKeys(List<Integer> keys) throws InvalidEncryptionKeyException {
        int numberOfKeys = ((EncryptionAlgorithmImpl<?>) encryptionAlgorithm).getNumberOfKeys();
        if (numberOfKeys != keys.size()) {
            throw new InvalidEncryptionKeyException("Number of keys: " + keys.size() + "  \nExpected number of key: " + numberOfKeys);
        }
        keys = keys.stream().filter(key-> key < 0 || key.toString().length() > encryptionAlgorithm.getKeyStrength()).collect(Collectors.toList());
        if (!keys.isEmpty()) {
            throw new InvalidEncryptionKeyException(keys.get(0), "The key should be between 0 to " + encryptionAlgorithm.getKeyStrength() + "digits");
        }
    }

    public IKey<Integer> generateKeys() {
        //get the necessary number of the keys
        int numKeys = ((EncryptionAlgorithmImpl<?>) encryptionAlgorithm).getNumberOfKeys();
        List<Integer> keys = Arrays.stream(IntStream.generate(() -> new Random().nextInt(UPPER_LIMIT))
                        .limit(numKeys).toArray()).boxed()
                .collect(Collectors.toList());
        return buildKey(keys);
    }

    public Path saveKey(Path outputPath, IKey<Integer> key) throws IOException {
        Path keyPath = Paths.get(outputPath.toString(), "key.txt");
        File keyFile = FileStream.createFile(keyPath);
        FileStream.saveData(keyFile, key.toString());
        return keyPath;
    }
}
