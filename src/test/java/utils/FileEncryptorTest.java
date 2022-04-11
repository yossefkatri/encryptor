package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import utils.keys.IKey;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class FileEncryptorTest {
    final EncryptionAlgorithmImpl<Integer> encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    final FileEncryptor<Integer> testedEncryptor = new FileEncryptor<>(encryptionAlgorithm);

    @Test
    void encryptFileSourceFileNonExist() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        IKey<Integer> key = mock(IKey.class);
        assertThrows(NoSuchFileException.class, () -> testedEncryptor.encryptFile(Paths.get(userDirectory, "input1.txt"),
                Paths.get(userDirectory, "input2.txt"),
                key));
    }

    @Test
    void decryptFileSourceFileNonExist() {
        IKey<Integer> key = mock(IKey.class);
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        assertThrows(NoSuchFileException.class, () -> testedEncryptor.decryptFile(Paths.get(userDirectory, "input1.txt"),
                Paths.get(userDirectory, "input2.txt"),
                key));
    }

    @Test
    void encryptFileTest() {
        try {

            String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

            when(encryptionAlgorithm.encryptChar(any(char.class), any(IKey.class))).thenReturn('d');
            when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);
            Path sPath = Paths.get(userDirectory, "tested5.txt");

            File sFile = new File(sPath.toString());
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            Path outputPath = Paths.get(userDirectory, "tested6.txt");
            IKey<Integer> iKey = mock(IKey.class);
            testedEncryptor.encryptFile(sPath, outputPath, iKey);

            File outputFile = new File(outputPath.toString());
            Scanner outputReader = new Scanner(outputFile);
            String content = outputReader.nextLine();
            assertEquals("dddddd", content);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void decryptFileTest() {

        try {

            String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

            when(encryptionAlgorithm.decryptChar(any(char.class), any(IKey.class))).thenReturn('d');
            when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);
            when(encryptionAlgorithm.getKeyStrength()).thenReturn(54);

            Path sPath = Paths.get(userDirectory, "tested7.txt");
            Path keyPath = Paths.get(userDirectory, "key.txt");
            File sFile = new File(sPath.toString());
            File keyFile = new File(keyPath.toString());
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            FileWriter keyFileWriter = new FileWriter(keyFile);
            keyFileWriter.write("2222");
            keyFileWriter.close();

            Path outputPath = Paths.get(userDirectory, "tested8.txt");
            IKey<Integer> ikey = mock(IKey.class);
            testedEncryptor.decryptFile(sPath, outputPath, ikey);


            File outputFile = new File(outputPath.toString());
            Scanner outputReader = new Scanner(outputFile);
            String content = outputReader.nextLine();
            assertEquals("dddddd", content);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}