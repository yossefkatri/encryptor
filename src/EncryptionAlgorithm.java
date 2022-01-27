import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class EncryptionAlgorithm {

    private static final int upper_limit = 50000;

    public static void Decrypt() {

        //get from the user the location of the files.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the location of the encrypted source file: ");
        String EncryptedFile_path = scanner.nextLine();
        EncryptedFile_path = EncryptedFile_path.replaceAll("\"","");
        System.out.println("Enter the location of the key file: ");
        String KeyFile_path = scanner.nextLine();
        KeyFile_path = KeyFile_path.replaceAll("\"","");

        //read the key from the file
        File key_file = new File(KeyFile_path);
        Scanner key_reader;
        try {
            key_reader = new Scanner(key_file);
        }
        catch (FileNotFoundException e) {
            System.out.println("error the key_file doesn't exist.");
            return;
        }

        //get the key from the file
        int key;
        try {
            key = Integer.parseInt(key_reader.nextLine());
        }
        catch(NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }

        //get the encrypted file
        File encrypted_file = new File(EncryptedFile_path);
        Scanner encryptedFile_reader;
        try {
            encryptedFile_reader = new Scanner(encrypted_file);
        }
        catch (FileNotFoundException e) {
            System.out.println("error the encrypted_file doesn't exist.");
            return;
        }

        String Extension = EncryptedFile_path.substring(EncryptedFile_path.lastIndexOf("."));
        String defile_name = EncryptedFile_path.substring(0, EncryptedFile_path.lastIndexOf(".")).replaceAll("_encrypted","");


        //generate the decrypted file
        File decrypted_file;
        try {
            decrypted_file = new File(defile_name + "_decrypted" + Extension);
            if (decrypted_file.createNewFile()) {
                System.out.println("File created: " + decrypted_file.getName());
            }
            else {
                System.out.println("File does exists.");
                PrintWriter writer = new PrintWriter(decrypted_file);
                writer.print("");
                writer.close();
            }
        }
        catch (IOException e) {
            System.out.println("ERROR: can't create new file.");
            e.printStackTrace();
            return;
        }
        FileWriter decrypted_writer;
        try {
            decrypted_writer = new FileWriter(decrypted_file.getAbsoluteFile());
        }
        catch (IOException e) {
            System.out.println("ERROR: can't create file_writer ");
            e.printStackTrace();
            return;
        }

        //read from the encrypted file and decrypt it to another file
        while (encryptedFile_reader.hasNextLine()) {
            String data = encryptedFile_reader.nextLine();
            StringBuilder decrypted_data = new StringBuilder();
            for (int i = 0; i < data.length(); ++i) {
                decrypted_data.append((char) ((int) data.charAt(i) - key));
            }
            System.out.println(data + ":--------> " + decrypted_data);
            try {
                decrypted_writer.write(decrypted_data.toString()+'\n');
            } catch (IOException e) {
                System.out.println("error: can't write to the file.");
                e.printStackTrace();
                return;
            }

        }

        key_reader.close();
        encryptedFile_reader.close();
        try {
            decrypted_writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Encrypt() {
        Scanner scanner = new Scanner(System.in);
        Random randomizer = new Random();
        System.out.println("Enter the path of yur source file:");
        String path = scanner.nextLine();
        path = path.replaceAll("\"", "");

        File reader_file = new File(path);
        Scanner reader;
        try {
            reader = new Scanner(reader_file);
        } catch (FileNotFoundException e) {
            System.out.println("error the file doesn't exist.");
            return;
        }


        String Extension = path.substring(path.lastIndexOf("."));
        String file_name = path.substring(0, path.lastIndexOf("."));


        // generate the encrypted file
        File encrypted_file;
        try {
            encrypted_file = new File(file_name + "_encrypted" + Extension);
            if (encrypted_file.createNewFile()) {
                System.out.println("File created: " + encrypted_file.getName());
            } else {
                System.out.println("File does exists.");
                PrintWriter writer = new PrintWriter(encrypted_file);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: can't create new file.");
            e.printStackTrace();
            return;
        }
        FileWriter writer;
        try {
            writer = new FileWriter(encrypted_file.getAbsoluteFile());
        } catch (IOException e) {
            System.out.println("ERROR: can't create file_writer ");
            e.printStackTrace();
            return;
        }

        //generate the key
        int key = randomizer.nextInt(upper_limit);
        //save the key in file named "key.txt"
        File key_file;
        try {
            key_file = new File(path.substring(0,path.lastIndexOf('\\')+1)+"key.txt");
            if (key_file.createNewFile()) {
                System.out.println("File created: " + key_file.getName());
            }
            else {
                System.out.println("File does exists.");
                PrintWriter key_writer = new PrintWriter(key_file);
                key_writer.print("");
                key_writer.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: can't create new file.");
            e.printStackTrace();
            return;
        }
        FileWriter key_writer;
        try {
            key_writer = new FileWriter(key_file.getAbsoluteFile());
        } catch (IOException e) {
            System.out.println("ERROR: can't create file_writer ");
            e.printStackTrace();
            return;
        }
        try {
            key_writer.write(String.valueOf(key));
            System.out.println(key);
        } catch (IOException e) {
            System.out.println("error: can't write to the file.");
            e.printStackTrace();
            return;
        }

        //read lines from the file encrypt and save it in the encrypted file
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            StringBuilder encrypted_data = new StringBuilder();
            for (int i = 0; i < data.length(); ++i) {
                encrypted_data.append( (char) ((int) data.charAt(i) + key));
            }
            System.out.println(data + ":--------> " + encrypted_data);
            try {
                writer.write(encrypted_data.toString() +'\n');
            } catch (IOException e) {
                System.out.println("error: can't write to the file.");
                e.printStackTrace();
                return;
            }
        }

        System.out.println("the locations of the key file and the encrypted file :"+path.substring(0,path.lastIndexOf('\\')+1)+"\n");

        reader.close();
        try {
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("error: can't close the encrypted_file");
        }
        try {
            key_writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("error: can't close the key file");
        }
    }
}
