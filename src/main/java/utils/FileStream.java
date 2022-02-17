package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class FileStream {

    //create the proper-file-name from the path and the ending.
    public static String getFileName(String filePath, String ending) {
        String extension = filePath.substring(filePath.lastIndexOf("."));
        String defileName = filePath.substring(0, filePath.lastIndexOf(".")).replaceAll("_encrypted","");
        return defileName +  ending + extension;
    }

    //create file by his name
    public static FileWriter createFile(String fileName) throws IOException {
        //create the decrypted file
        File file;
        file = new File(fileName);
        if (!file.createNewFile()) {
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }

        //create the file-writer for the file
        return new FileWriter(file.getAbsoluteFile());
    }

    //get the key file
    public static List<Integer> getListOfIntegers(String keyFilePath) throws FileNotFoundException {

        //read the key from the file
        File keyFile = new File(keyFilePath);
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
    public static String getFileContent(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
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
    public static String getOutputFilesPath(String path) {
        return path.substring(0,path.lastIndexOf('\\')+1);
    }
}