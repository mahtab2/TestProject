package com.ac.mahtab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class QuestionOne {
    public static String srcDir = "C:\\Users\\ASUS\\Desktop\\testingProject\\test";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String query = scanner.next();
        List<String> words = Arrays.asList(query.toLowerCase());
        Set<String> fileNameSet = new HashSet<>();
        fileNameSet = findFile(words);
        printFileNames(fileNameSet);
    }
    private static void printFileNames(Set<String> fileNameSet)
    {
        if (!fileNameSet.isEmpty()) {
            for (String name : fileNameSet) {
                System.out.printf("find in : %s \n", name);
            }
        } else {
            System.out.println("Not found!");
        }
    }

    public static Set<String> findFile(List<String> words) {
        File folder = new File(srcDir);
        File[] listOfFiles = folder.listFiles();
        Set<String> fileNameSet = new HashSet<>();
        if (listOfFiles.length > 0) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = fileToString(srcDir + "\\" + file.getName());
                    List<String> inputStringList = Arrays.asList(content.split(" "));
                    if (inputStringList.containsAll(words)) {
                        fileNameSet.add(file.getName());
                    }
                }
            }
        }
        return fileNameSet;
    }

    public static String fileToString(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                stringBuilder.append(currentLine).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString().toLowerCase();

    }
}