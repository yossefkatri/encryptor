import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class FileStream {

    //create the proper-file-name from the path and the ending.
    public String getFileName(String filePath, String ending) {
        String extension = filePath.substring(filePath.lastIndexOf("."));
        String defileName = filePath.substring(0, filePath.lastIndexOf(".")).replaceAll("_encrypted","");
        return defileName +  ending + extension;
    }

    //create file by his name
    public FileWriter createFile(String fileName) throws IOException {
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
    public int getKey(String keyFilePath) throws FileNotFoundException {

        //read the key from the file
        File keyFile = new File(keyFilePath);
        Scanner keyReader = new Scanner(keyFile);
        //get the key from the file
        int key;
        key = Integer.parseInt(keyReader.nextLine());
        keyReader.close();

        return key;
    }

    //get the key file
    public List<Integer> getKeys(String keyFilePath) throws FileNotFoundException {

        //read the key from the file
        File keyFile = new File(keyFilePath);
        Scanner keyReader = new Scanner(keyFile);

        //get the key from the file
        List<Integer> keys = new ArrayList<>();
        while (keyReader.hasNextLine()) {
            int key = Integer.parseInt(keyReader.nextLine());
            keys.add(key);
        }

        keyReader.close();
        return keys;
    }

    //get the  message from the file
    public String getFileContent(String filePath) throws FileNotFoundException {

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
    public void saveData(FileWriter file, String message) throws IOException {
        try {
            file.write(message+'\n');
        }
        catch (IOException e) {
            throw new IOException("ERROR: can't write to the file.");
        }

    }

    //return the path of the output-files of the encryption (encrypted and key file)
    public String getOutputFilesPath(String path) {
        return path.substring(0,path.lastIndexOf('\\')+1);
    }
}
