package com.ac.mahtab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class QuestionFour {
    public static String srcDir = "C:\\Users\\ASUS\\Desktop\\testingProject\\test";

    public static void main(String[] args) throws IOException {
        Set<String> fileNameSet ;
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();
        List<String> words = wordProcess(query);
        List<String> operators = operatorProcess(query);
        Pattern pattern = generatePattern(words, operators);
        fileNameSet = findFile(pattern);
        printFileNames(fileNameSet);

    }

    private static void printFileNames(Set<String> fileNameSet) {
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
        List<String> words = Arrays.asList(processed.toLowerCase().trim().split("or|and"));
        return words;
    }

    private static List<String> operatorProcess(String query) {
        List<String> tokens = Arrays.asList(query.toLowerCase().trim().split(" "));
        List<String> operators = new ArrayList<>();
        for (String token : tokens) {
            if (token.equalsIgnoreCase("and") | token.equalsIgnoreCase("or")) {
                operators.add(token);
            }
        }
        return operators;
    }

    private static Pattern generatePattern(List<String> words, List<String> operators) {
        StringBuilder regexp = new StringBuilder();
        int counter = 0;
        String posfix = "";
        for (String word : words) {
            if (counter < words.size() - 1) {
                if (operators.get(counter).equalsIgnoreCase("or")) {
                    posfix = "|";
                } else {
                    posfix = "";
                }
            } else
                posfix = "";
            if (word.matches("^[\\w\\d]+")) {
                regexp.append("(?=.*").append("\\b" + word + "\\b").append(")" + posfix);
            } else {
                regexp.append("(?=.*").append(word).append(")" + posfix);
            }
            counter++;
        }
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