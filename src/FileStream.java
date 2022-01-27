import java.util.Scanner;
import java.io.*;

public class FileStream {
    Scanner IScanner;
    public FileStream(InputStream input) {

        IScanner = new Scanner(input);
    }

    // get the key file path from the user
    private   String getKeyFilePath(){
        System.out.println("Enter the location of the key file: ");
        String KeyFile_path = IScanner.nextLine();
        KeyFile_path = KeyFile_path.replaceAll("\"","");
        return KeyFile_path;
    }

    //create the proper decrypted-file-name from the encrypted-file-name
    private String getDecryptFileName(String EncryptedFile_path) {
        String Extension = EncryptedFile_path.substring(EncryptedFile_path.lastIndexOf("."));
        String defile_name = EncryptedFile_path.substring(0, EncryptedFile_path.lastIndexOf(".")).replaceAll("_encrypted","");
        return defile_name + "_decrypted" + Extension;
    }

    //create the proper encrypted-file-name from the original-file
    private String getEncryptedFileName(String file_path)
    {
        String Extension = file_path.substring(file_path.lastIndexOf("."));
        String file_name = file_path.substring(0, file_path.lastIndexOf("."));
        return file_name + "_encrypted" + Extension;
    }

    //create file by his name
    private FileWriter createFile(String file_name) throws IOException {
        //create the decrypted file
        File file;
        try {
            file = new File(file_name);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
            else {
                System.out.println("File does exists.");
                PrintWriter writer = new PrintWriter(file);
                writer.print("");
                writer.close();
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: can't create new file.");
            throw e;
        }

        //create the file-writer for the file
        FileWriter writer;
        try {
            writer = new FileWriter(file.getAbsoluteFile());
        }
        catch (IOException e) {
            System.out.println("ERROR: can't create file_writer ");
            throw e;
        }
        return writer;
    }


    //get the input-file-name from the user
    public String getFileName() {
        System.out.println("Enter the path of yur source file:");
        String path = IScanner.nextLine();
        path = path.replaceAll("\"", "");
        return path;
    }

    // get the encrypted file path from the user
    public   String getEncryptedFilePath() {
        System.out.println("Enter the location of the encrypted source file: ");
        String EncryptedFile_path = IScanner.nextLine();
        EncryptedFile_path = EncryptedFile_path.replaceAll("\"","");
        return EncryptedFile_path;
    }

    //get the key file
    public int getKey() {
        //get from the user the location of the files.
        String keyFile_path = getKeyFilePath();

        //read the key from the file
        File key_file = new File(keyFile_path);
        Scanner key_reader;
        try {
            key_reader = new Scanner(key_file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return -1;
        }

        //get the key from the file
        int key;
        try {
            key = Integer.parseInt(key_reader.nextLine());
        }
        catch(NumberFormatException ex) {
            ex.printStackTrace();
            return -1;
        }

        key_reader.close();

        return key;
    }

    //get the  message from the file
    public String getMsg(String file_path) throws FileNotFoundException {

        File file = new File(file_path);
        Scanner File_reader;
        File_reader = new Scanner(file);
        StringBuilder message = new StringBuilder();
        while (File_reader.hasNextLine()) {
            message.append(File_reader.nextLine()).append("\n");
        }
        message.deleteCharAt(message.length()-1);
        File_reader.close();

        return message.toString();
    }

    //create and return the file-writer for the decrypted file
    public FileWriter getDecryptedFile(String EncryptedFile_path) throws IOException {
        String DecryptName = getDecryptFileName(EncryptedFile_path);
        return createFile(DecryptName);
    }

    // save the data on the file
    public void saveData(FileWriter File, String message) {
        try {
            File.write(message+'\n');
        }
        catch (IOException e) {
            System.out.println("ERROR: can't write to the file.");
            e.printStackTrace();
        }

    }


    //print and return the path of the output-files of the encryption (encrypted and key file)
    public String getOutputFilesPath(String path) {
        String files_path = path.substring(0,path.lastIndexOf('\\')+1);
        System.out.println("the locations of the key file and the encrypted file :"+ files_path+"\n");
        return files_path;
    }

    //create and return the file-writer for the key file
    public FileWriter getKeyFile(String KeyFilePath) throws IOException {
        String file_name = KeyFilePath + "key.txt";
        return createFile(file_name);
    }

    //create and return the file-writer for the encrypted file
    public FileWriter getEncryptedFile(String file_path) throws IOException {
        String file_name = getEncryptedFileName(file_path);
        return createFile(file_name);
    }
}
