package com.smartcrop.util;

import java.io.*;

public class FileUtil {

    // Save user
    public static void saveUser(String name, String email, String password) {
        try {
            FileWriter fw = new FileWriter("users.txt", true);
            fw.write(name + "," + email + "," + password + "\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validate login
    public static boolean validateUser(String email, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[1].equals(email) && data[2].equals(password)) {
                    br.close();
                    return true;
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
