package com.ac.oop.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * Class for reading files from a folder in srcDir
 *
 * @author  Mahtab Sarlak
 * @version 1.0
 * @since   2020-03-9
 */
public class FileConfiguration {
    private final String srcDir = "C:\\Users\\ASUS\\Documents\\term6\\private\\untitled2\\test";
    private  Map<String, String> fileData;

    public FileConfiguration() {
        fileData = new HashMap<String, String>();
    }

    /**
     * This method is used to create a map for files which keys are file's name and values are concated file's lines.
     * @return map between file's name and their texts as string
     */
    public Map<String, String> fileProcessing() {
        File folder = new File(srcDir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles.length > 0) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileToString(srcDir + "\\" + file.getName());
                    fileData.put(file.getName(),content);
                }
            }
        }
        return fileData;
    }

    /**
     * This method is used to concat file's lines and generate a string.
     * @param filePath This is the first paramter to readFileToString method.
     * @return file's texts as string.
     */
    private String readFileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString().toLowerCase();

    }

}
