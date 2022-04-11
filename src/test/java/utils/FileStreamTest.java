package utils;

import exceptions.InvalidEncryptionKeyException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class FileStreamTest {

    @Test
    void getEncryptedFileName() {
        Path filePath = Paths.get("C:\\tested.txt");
        Path encryptedFilePath = FileStream.getFileName(filePath,"_encrypted");
        assertEquals("C:\\tested_encrypted.txt",encryptedFilePath.toString());
    }

    @Test
    void getDecryptedFileName() {
        Path filePath = Paths.get("C:\\tested_encrypted.txt");
        Path encryptedFilePath = FileStream.getFileName(filePath,"_decrypted");
        assertEquals("C:\\tested_decrypted.txt",encryptedFilePath.toString());
    }

    @Test
    void createFile() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        Path path = Paths.get(userDirectory,"tested3.txt");

        try {
            File file = FileStream.createFile(path);
            if(!file.exists())
            {
                throw new IOException();
            }
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getKeys() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        Path Path = Paths.get(userDirectory, "tested2.txt");
        try {
            FileWriter fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1324\n455667\n5555");
            fileWriter.close();
            List<Integer> content = FileStream.readKeys(Path);
            List<Integer> expected = Arrays.asList(1324,455667,5555);
            assertEquals(expected,content);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void getListOfIntegersFileNotFoundException() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested.txt");
        assertThrows(FileNotFoundException.class,()->FileStream.readKeys(Path));
    }

    @Test
    void getListOfIntegersInvalidEncryptionKeyException() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();
        Path Path = Paths.get(userDirectory,"tested9.txt");
        try {
            FileWriter fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1324\n45s56d67\n55a55");
            fileWriter.close();
            assertThrows(InvalidEncryptionKeyException.class,()->FileStream.readKeys(Path));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void readFileContent() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        Path Path = Paths.get(userDirectory,"tested1.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1ב324\n455667\n5555");
            fileWriter.close();
            String content = FileStream.readFileContent(Path);
            assertEquals("1ב324\n455667\n5555",content);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getFileContentFileNotFound() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        Path Path = Paths.get(userDirectory,"tested.txt");
       assertThrows(NoSuchFileException.class,()->FileStream.readFileContent(Path));
    }

    @Test
    void saveData() {
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        Path path = Paths.get(userDirectory,"tested4.txt");
        File fileWriter = new File(path.toString());
        try {
            FileStream.saveData(fileWriter,"12344321");
            File file = new File(path.toString());
            Scanner fileReader;
            fileReader = new Scanner(file);
            String content = fileReader.nextLine();
            assertEquals("12344321",content);

        }catch (Exception e)
        {
            fail();
        }
    }

    @Test
    void saveDataIOException(){
        String userDirectory = Paths.get("src\\main\\java\\outputFiles").toAbsolutePath().toString();

        Path path = Paths.get(userDirectory);
        File file = new File(path.toString());
        assertThrows(IOException.class,()->FileStream.saveData(file,"tested"));
    }
}