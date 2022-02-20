package utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            FileStream.createFile(path);
            File file = new File(path.toString());
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
        } catch (IOException e) {
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
    void saveData() {
        Path path = Paths.get("C:\\Users\\Yossef Katri\\IdeaProjects\\encryptor\\src\\main\\java\\outputFiles\\tested4.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path.toString());
            FileStream.saveData(fileWriter,"12344321");
            fileWriter.close();
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
    void getOutputFilesPath() {
        Path tested = Paths.get("C:\\sss\\tested.txt");
        Path result = FileStream.getOutputFilesPath(tested);
        assertEquals("C:\\sss",result.toString());
    }
}