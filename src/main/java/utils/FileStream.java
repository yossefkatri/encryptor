package utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import exceptions.*;

public final class FileStream {
    public static final long TIMES_TO_REPEAT = 262144;

    private static void repeatWrite(FileWriter fileWriter, String template) throws IOException {
        for (int i = 0; i < TIMES_TO_REPEAT; ++i) {
            fileWriter.write(template);
        }
    }

    //create the proper-file-name from the path and the ending.
    public static Path getFileName(Path filePath, String ending) {
        String fullName = filePath.getFileName().toString();

        String extension = fullName.substring(fullName.lastIndexOf('.'));
        String name = fullName.substring(0, fullName.lastIndexOf('.'));

        name = name.replaceAll("_encrypted", "");

        return Paths.get(filePath.getParent().toString(), name + ending + extension);
    }

    public static Path getDirectoryOutput(Path path, String subDir) {
        return Paths.get(path.toString(), subDir);
    }

    //create file by his name
    public static File createFile(Path fileName) throws IOException {
        try {
            //create the decrypted file
            File file;
            file = new File(fileName.toString());
            if (!file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
            return file;
        } catch (IOException ex) {
            throw new IOException("The function can't succeed to create file. \n File: " + fileName.getFileName() + "\n Error: " + ex.getMessage());
        }
    }

    //create directory by his name
    public static void createDirectory(Path dirPath) throws IOException {
        File theDir = new File(dirPath.toString());
        if (!(theDir.mkdirs() || theDir.exists())) {
            throw new IOException("The system cannot create the directory");
        }
    }

    //get the key file
    public static List<Integer> readKeys(Path keyFilePath) throws FileNotFoundException, InvalidEncryptionKeyException {
        File keyFile = new File(keyFilePath.toString());
        //read the key from the file
        try (Scanner itemReader = new Scanner(keyFile)) {
            //get the key from the file
            List<Integer> items = new ArrayList<>();
            while (itemReader.hasNextLine()) {
                int item = Integer.parseInt(itemReader.nextLine());
                items.add(item);
            }
            return items;
        } catch (NumberFormatException ex) {
            throw new InvalidEncryptionKeyException("The item isn't a pure number.\n" + ex.getMessage());
        }
    }

    //get the  message from the file
    public static String readFileContent(Path filePath) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        List<String> lines = Files.readAllLines(filePath, charset);
        return String.join("\n", lines);

    }

    //get directory path
    public static Path getDirPath(String stringPath) throws Exception {
        Path dirPath = Paths.get(stringPath);
        if (!new File(stringPath).isDirectory()) {
            throw new Exception("the path should be a directory");
        }
        return dirPath;
    }

    // save the data on the file
    public static void saveData(File file, String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(message + '\n');
        } catch (IOException e) {
            throw new IOException("The function doesn't succeed to write to the file. \n File: " + file.getName() + "\n Error: " + e.getMessage());
        }

    }

    //create directory with large files on the given location and the given amount
    public static void generateBigFiles(Path dir, int numOfFile) throws IOException {
        Path directory = Paths.get(dir.toString(), "Big");
        createDirectory(directory);
        for (int i = 0; i < numOfFile; ++i) {
            //create file and fill it with repeatedly template.
            File file = createFile(Paths.get(directory.toString(), "bigFile" + i + ".txt"));
            FileWriter fileWriter = new FileWriter(file);
            String template = "example,test";
            repeatWrite(fileWriter, template);
            fileWriter.close();
        }
    }

}
