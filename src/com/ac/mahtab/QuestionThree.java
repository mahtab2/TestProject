package com.ac.mahtab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QuestionThree {
    public static String srcDir = "C:\\Users\\ASUS\\Desktop\\testingProject\\test";

    public static void main(String[] args) throws IOException {
        Set<String> fileNameSet = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();
        List<String> words=wordProcess(query);
        Pattern pattern=generatePattern(words);
        fileNameSet = findFile(pattern);
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

    private static List<String> wordProcess(String query) {
        String processed = query.replace(" ", "")
                .replace("\\", "\\\\")
                .replace(".", "\\.")
                .replace("+", "\\+")
                .replace("?", "\\?")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("*", "\\*")
                .replace("^", "\\^")
                .replace("$", "\\$")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                ;
        List<String> words = Arrays.asList(processed.toLowerCase().trim().split("or"));
       return words;
    }

    private static Pattern generatePattern(List<String> words) {
        StringBuilder regexp = new StringBuilder();
        regexp.append("(");
        int counter = 0;
        String posfix = "";
        for (String word : words) {
            if (counter < words.size() - 1)
                posfix = "|";
            else
                posfix = "";
            if (word.matches("^[\\w\\d]+")) {
                regexp.append("\\b" + word + "\\b" + posfix);
            } else {
                regexp.append(word + posfix);
            }
            counter++;
        }
        regexp.append(")");
        return Pattern.compile(regexp.toString());
    }

    public static Set<String> findFile(Pattern pattern) {
        File folder = new File(srcDir);
        File[] listOfFiles = folder.listFiles();
        Set<String> fileNameSet = new HashSet<>();
        if (listOfFiles.length > 0) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String content = readFileToString(srcDir + "\\" + file.getName());
                    Matcher m = pattern.matcher(content);
                    if (m.find()) {
                        fileNameSet.add(file.getName());
                    }
                }
            }
        }
        return fileNameSet;
    }

    public static String readFileToString(String filePath) {
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