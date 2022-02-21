package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import exceptions.InvalidEncryptionKeyException;
import keys.IKey;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void decryptFileDifferentNumberOfKeys(){
        Path keyPath = Paths.get("C:\\InvalidEncryptionKeyException");
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(4);
        try(MockedStatic<FileStream> fileStream = mockStatic(FileStream.class)) {
            fileStream.when(()->FileStream.getListOfIntegers(keyPath)).thenReturn(Arrays.asList(1,2,3));
            fileStream.when(()->FileStream.getFileContent(keyPath)).thenReturn("tested");
            assertThrows(InvalidEncryptionKeyException.class, () -> testedEncryptor.decryptFile(keyPath, keyPath, keyPath));
        }
    }

    @Test
    void decryptedFileInvalidKey() {
        Path keyPath = Paths.get("C:\\tested");
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(3);
        try(MockedStatic<FileStream> fileStream = mockStatic(FileStream.class)) {
            fileStream.when(()->FileStream.getListOfIntegers(keyPath)).thenReturn(Arrays.asList(10000,2,3));
            fileStream.when(()->FileStream.getFileContent(keyPath)).thenReturn("tested");
            assertThrows(InvalidEncryptionKeyException.class, () -> testedEncryptor.decryptFile(keyPath, keyPath, keyPath));
        }
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
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);

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