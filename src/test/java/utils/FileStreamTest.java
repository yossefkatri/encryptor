package utils;

import exceptions.InvalidEncryptionKeyException;
import org.junit.jupiter.api.Test;

import java.io.*;
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
         Path path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested3.txt");
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
    void getListOfIntegers() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested2.txt");
        try {
            FileWriter fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1324\n455667\n5555");
            fileWriter.close();
            List<Integer> content = FileStream.getListOfIntegers(Path);
            List<Integer> expected = Arrays.asList(1324,455667,5555);
            assertEquals(expected,content);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void getListOfIntegersFileNotFoundException() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested.txt");
        assertThrows(FileNotFoundException.class,()->FileStream.getListOfIntegers(Path));
    }

    @Test
    void getListOfIntegersInvalidEncryptionKeyException() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested9.txt");
        try {
            FileWriter fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1324\n45s56d67\n55a55");
            fileWriter.close();
            assertThrows(InvalidEncryptionKeyException.class,()->FileStream.getListOfIntegers(Path));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void getFileContent() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested1.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(Path.toString());
            fileWriter.write("1324\n455667\n5555");
            fileWriter.close();
            String content = FileStream.getFileContent(Path);
            assertEquals("1324\n455667\n5555",content);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void getFileContentFileNotFound() {
        Path Path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested.txt");
       assertThrows(FileNotFoundException.class,()->FileStream.getFileContent(Path));
    }

    @Test
    void saveData() {
        Path path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested4.txt");
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
        Path path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles");
        File file = new File(path.toString());
        assertThrows(IOException.class,()->FileStream.saveData(file,"tested"));
    }
}