package com.ac.mahtab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * user search a boolean statement with OR, AND operators and application return files that maches with given query
 *
 * @author  Mahtab Sarlak
 * @version 1.0
 * @since   2020-02-25
 */

public class QuestionFour {
    public static String srcDir = "C:\\Users\\ASUS\\Desktop\\testingProject\\test";
    /**
     * main method which makes use of wordProcess, operatorProcess, generatePattern, findFile, printFileNames methods.
     * @param args Unused.
     * @return Nothing.
     * @exception IOException On file error.
     * @see IOException
     */
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
    /**
     * This method is used to print files(name) that maches with given query
     * @param fileNameSet This is the first paramter to printFileNames method
     * @return nothing.
     */
    private static void printFileNames(Set<String> fileNameSet) {
        if (!fileNameSet.isEmpty()) {
            for (String name : fileNameSet) {
                System.out.printf("find in : %s \n", name);
            }
        } else {
            System.out.println("Not found!");
        }
    }
    /**
     * This method is used to pre process the given word and tokenize the given query.
     * @param query This is the first paramter to wordProcess method. It's the input word.
     * @return list of requested words.
     */
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
    /**
     * This method is used to generate a list of logic operators in given query.
     * @param query This is the first paramter to operatorProcess method. It's the input query.
     * @return list of logic operators in given query.
     */
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
    /**
     * This method is used to generate a panttern for given query.
     * @param words This is the first paramter to generatePattern method. It's list of requested words.
     * @param operators This is the second paramter to generatePattern method. It's list of  logic operators in given query.
     * @return regex pattern.
     */
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
    /**
     * This method is used to find files that contains requested words.
     * @param pattern This is the first paramter to findFile method. It's regex pattern.
     * @return list of files that contains requested words.
     */
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
    /**
     * This method is used to concat file's lines and generate a string.
     * @param filePath This is the first paramter to readFileToString method.
     * @return  file's texts as string.
     */
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