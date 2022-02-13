package utils;

public class OperationsEncryption {
    public static int concat(int a, int b) {

        // Convert both the integers to string
        String s1 = Integer.toString(a);
        String s2 = Integer.toString(b);

        // Concatenate both strings
        String s = s1 + s2;

        // Convert the concatenated string
        // to integer
        // return the formed integer
        return Integer.parseInt(s);
    }
}
