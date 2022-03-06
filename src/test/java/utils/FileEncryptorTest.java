package utils;

import encriptionAlgorithms.EncryptionAlgorithmImpl;
import exceptions.InvalidEncryptionKeyException;
import keys.IKey;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class FileEncryptorTest {
    final EncryptionAlgorithmImpl<Integer> encryptionAlgorithm = mock(EncryptionAlgorithmImpl.class);
    final FileEncryptor testedEncryptor = new FileEncryptor(encryptionAlgorithm);

    @Test
    void encryptFileSourceFileNonExist() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
         assertThrows(NoSuchFileException.class,()->testedEncryptor.encryptFile(Paths.get(userDirectory,"input1.txt"),
                Paths.get(userDirectory, "input2.txt"),
                Paths.get(userDirectory,"input3.txt")));
    }

    @Test
    void decryptFileSourceFileNonExist() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        assertThrows(NoSuchFileException.class,()->testedEncryptor.decryptFile(Paths.get(userDirectory,"input1.txt"),
                Paths.get(userDirectory, "input2.txt"),
                Paths.get(userDirectory,"input3.txt")));
    }

    @Test
    void decryptFileDifferentNumberOfKeys(){

        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        Path keyPath = Paths.get(userDirectory, "tested");
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(4);
        try(MockedStatic<FileStream> fileStream = mockStatic(FileStream.class)) {
            fileStream.when(()->FileStream.readKeys(keyPath)).thenReturn(Arrays.asList(1,2,3));
            fileStream.when(()->FileStream.readFileContent(keyPath)).thenReturn("tested");
            assertThrows(InvalidEncryptionKeyException.class, () -> testedEncryptor.decryptFile(keyPath, keyPath, keyPath));
        }
    }

    @Test
    void decryptedFileInvalidKey() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        Path keyPath = Paths.get(userDirectory, "tested");
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(3);
        when(encryptionAlgorithm.getKeyStrength()).thenReturn(1);
        try(MockedStatic<FileStream> fileStream = mockStatic(FileStream.class)) {
            fileStream.when(()->FileStream.readKeys(keyPath)).thenReturn(Arrays.asList(10000,2,3));
            fileStream.when(()->FileStream.readFileContent(keyPath)).thenReturn("tested");
            assertThrows(InvalidEncryptionKeyException.class, () -> testedEncryptor.decryptFile(keyPath, keyPath, keyPath));
        }
    }

    @Test
    void encryptFileTest() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        when(encryptionAlgorithm.encryptChar(any(char.class),any(IKey.class))).thenReturn('d');
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);
        Path sPath = Paths.get(userDirectory,"tested5.txt");

        File sFile = new File(sPath.toString());
        try {
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            Path outputPath = Paths.get(userDirectory,"tested6.txt");
            Path keyPath = Paths.get(userDirectory);

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

        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        when(encryptionAlgorithm.decryptChar(any(char.class),any(IKey.class))).thenReturn('d');
        when(encryptionAlgorithm.getNumberOfKeys()).thenReturn(1);
        when(encryptionAlgorithm.getKeyStrength()).thenReturn(54);

        Path sPath = Paths.get(userDirectory,"tested7.txt");
        Path keyPath = Paths.get(userDirectory,"key.txt");
        File sFile = new File(sPath.toString());
        File keyFile = new File(keyPath.toString());
        try {
            FileWriter sFileWriter = new FileWriter(sFile);
            sFileWriter.write("ss\nsss");
            sFileWriter.close();

            FileWriter keyFileWriter = new FileWriter(keyFile);
            keyFileWriter.write("2222");
            keyFileWriter.close();

            Path outputPath = Paths.get(userDirectory,"tested8.txt");
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