package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import keys.IKey;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileEncryptorTest {
    final EncryptionAlgorithmImpl encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    final FileEncryptor testedEncryptor = new FileEncryptor(encryptionAlgorithm);

    @Test
    void encryptFileSourceFileNonExist() {
         assertThrows(FileNotFoundException.class,()->testedEncryptor.encryptFile(Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input2.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles")));
    }

    @Test
    void decryptFileSourceFileNonExist() {
         assertThrows(FileNotFoundException.class,()->testedEncryptor.decryptFile(Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt"),
                Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input2.txt"),
                Paths.get("C:\\Users\\Yossef\\Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\input1.txt")));
    }

    @Test
    void encryptFileTest() {
        when(encryptionAlgorithm.encryptChar(any(char.class),any(IKey.class))).thenReturn('d');
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);
        Path sPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested5.txt");

        File sFile = new File(sPath.toString());
        try {
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            Path outputPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested6.txt");
            Path keyPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\");

            testedEncryptor.encryptFile(sPath,outputPath,keyPath);

            File keyFile = new File(keyPath.toString());
            if (!keyFile.exists()) {
                fail();
            }

            File outputFile = new File(outputPath.toString());
            Scanner outputReader = new Scanner(outputFile);
            String content = outputReader.nextLine();
            assertEquals("dddddd",content);

        }catch (Exception e) {
            fail();
        }

    }

    @Test
    void decryptFileTest() {
        when(encryptionAlgorithm.decryptChar(any(char.class),any(IKey.class))).thenReturn('d');

        Path sPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested7.txt");
        Path keyPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\key.txt");
        File sFile = new File(sPath.toString());
        File keyFile = new File(keyPath.toString());
        try {
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            FileWriter keyFileWriter = new FileWriter(keyFile);
            keyFileWriter.write("2222");
            keyFileWriter.close();

            Path outputPath = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested8.txt");
            testedEncryptor.decryptFile(sPath,outputPath,keyPath);


            File outputFile = new File(outputPath.toString());
            Scanner outputReader = new Scanner(outputFile);
            String content = outputReader.nextLine();
            assertEquals("dddddd",content);

        }catch (Exception e) {
            fail();
        }
    }
}