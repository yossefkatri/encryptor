import java.util.Scanner;
import java.io.*;

public class FileStream {

    //create the proper-file-name from the path and the ending.
    private String getFileName(String filePath, String ending) {
        String Extension = filePath.substring(filePath.lastIndexOf("."));
        String defileName = filePath.substring(0, filePath.lastIndexOf(".")).replaceAll("_encrypted","");
        return defileName +  ending + Extension;
    }

    //create file by his name
    private FileWriter createFile(String fileName) throws IOException {
        //create the decrypted file
        File file;
        file = new File(fileName);
        if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        else {
                System.out.println("File does exists.");
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

    //get the  message from the file
    public String getMsg(String filePath) throws FileNotFoundException {

        File file = new File(filePath);
        Scanner FileReader;
        FileReader = new Scanner(file);
        StringBuilder message = new StringBuilder();
        while (FileReader.hasNextLine()) {
            message.append(FileReader.nextLine()).append("\n");
        }
        message.deleteCharAt(message.length()-1);
        FileReader.close();

        return message.toString();
    }

    //create and return the file-writer for the decrypted file
    public FileWriter getDecryptedFile(String EncryptedFilePath) throws IOException {
        String DecryptName = getFileName(EncryptedFilePath,"_decrypted");
        return createFile(DecryptName);
    }

    // save the data on the file
    public void saveData(FileWriter File, String message) throws IOException {
        try {
            File.write(message+'\n');
        }
        catch (IOException e) {
            throw new IOException("ERROR: can't write to the file.");
        }

    }

    //print and return the path of the output-files of the encryption (encrypted and key file)
    public String getOutputFilesPath(String path) {
        String filesPath = path.substring(0,path.lastIndexOf('\\')+1);
        System.out.println("the locations of the key file and the encrypted file :"+ filesPath+"\n");
        return filesPath;
    }

    //create and return the file-writer for the key file
    public FileWriter getKeyFile(String KeyFilePath) throws IOException {
        String fileName = KeyFilePath + "key.txt";
        return createFile(fileName);
    }

    //create and return the file-writer for the encrypted file
    public FileWriter getEncryptedFile(String filePath) throws IOException {
        String fileName = getFileName(filePath, "_encrypted");
        return createFile(fileName);
    }
}
