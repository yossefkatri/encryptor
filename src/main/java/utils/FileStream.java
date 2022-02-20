package utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class FileStream {

    //create the proper-file-name from the path and the ending.
    public static Path getFileName(Path filePath, String ending) {
        String fullName = filePath.getFileName().toString();

        String extension = fullName.substring(fullName.lastIndexOf('.'));
        String name = fullName.substring(0,fullName.lastIndexOf('.'));

        name = name.replaceAll("_encrypted","");

        return Paths.get(filePath.getParent().toString(),name+ending+extension);
    }

    //create file by his name
    public static FileWriter createFile(Path fileName) throws IOException {
        //create the decrypted file
        File file;
        file = new File(fileName.toString());
        if (!file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }

        //create the file-writer for the file
        return new FileWriter(file.getAbsoluteFile());
    }

    //get the key file
    public static List<Integer> getListOfIntegers(Path keyFilePath) throws FileNotFoundException {

        //read the key from the file
        File keyFile = new File(keyFilePath.toString());
        Scanner itemReader = new Scanner(keyFile);

        //get the key from the file
        List<Integer> items = new ArrayList<>();
        while (itemReader.hasNextLine()) {
            int item = Integer.parseInt(itemReader.nextLine());
            items.add(item);
        }

        itemReader.close();
        return items;
    }

    //get the  message from the file
    public static String getFileContent(Path filePath) throws FileNotFoundException {

        File file = new File(filePath.toString());
        Scanner fileReader;
        fileReader = new Scanner(file);
        StringBuilder message = new StringBuilder();
        while (fileReader.hasNextLine()) {
            message.append(fileReader.nextLine()).append("\n");
        }
        message.deleteCharAt(message.length()-1);
        fileReader.close();

        return message.toString();
    }

    // save the data on the file
    public static void saveData(FileWriter file, String message) throws IOException {
        try {
            file.write(message+'\n');
        }
        catch (IOException e) {
            throw new IOException("ERROR: can't write to the file.");
        }

    }

    //return the path of the output-files of the encryption (encrypted and key file)
    public static Path getOutputFilesPath(Path path) {
        return path.getParent();
    }
}
