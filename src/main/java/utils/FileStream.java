package utils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import exceptions.*;

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

           //create the file-writer for the file
           return file;
       }
       catch (IOException ex) {
           throw new IOException("The function can't succeed to create file. \n File: "+fileName.getFileName() +"\n Error: "+ex.getMessage());
       }
    }

    //get the key file
    public static List<Integer> getListOfIntegers(Path keyFilePath) throws FileNotFoundException, InvalidEncryptionKeyException {
        File keyFile = new File(keyFilePath.toString());
        //read the key from the file
        try(Scanner itemReader = new Scanner(keyFile)) {
            //get the key from the file
            List<Integer> items = new ArrayList<>();
            while (itemReader.hasNextLine()) {
                int item = Integer.parseInt(itemReader.nextLine());
                items.add(item);
            }
            return items;
        }catch (FileNotFoundException ex) {
            throw new FileNotFoundException("The source file isn't found. \n File: "+keyFilePath.getFileName()+"\n Error:"+ ex.getMessage());
        }
        catch (NumberFormatException ex) {
            throw new InvalidEncryptionKeyException("The item isn't a pure number.\n"+ex.getMessage());
        }
    }

    //get the  message from the file
    public static String getFileContent(Path filePath) throws FileNotFoundException {
        File file = new File(filePath.toString());

        try (Scanner fileReader = new Scanner(file)){
            StringBuilder message = new StringBuilder();
            while (fileReader.hasNextLine()) {
                message.append(fileReader.nextLine()).append("\n");
            }
            message.deleteCharAt(message.length()-1);
            return message.toString();
        }catch (FileNotFoundException ex){
            throw new FileNotFoundException("The source file isn't found. \n File: "+filePath.getFileName()+"\n Error:"+ ex.getMessage());
        }
    }

    // save the data on the file
    public static void saveData(File file, String message) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(message+'\n');
        }
        catch (IOException e) {
            throw new IOException("The function doesn't succeed to write to the file. \n File: "+file.getName()+"\n Error: "+e.getMessage());
        }

    }
}
