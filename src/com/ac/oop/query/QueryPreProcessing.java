package com.ac.oop.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
/**
 * Class for preprocessing search query
 *
 * @author  Mahtab Sarlak
 * @version 1.0
 * @since   2020-03-9
 */
public class QueryPreProcessing {
    public String query;

    public QueryPreProcessing(String query) {
        this.query = query;
    }
    /**
     * This method is used to create a pattern for given query.
     * @return regex pattern.
     */
    public Pattern getQueryPattern()
    {
        List<String> words = wordProcess();
        List<String> operators = operatorProcess();
        Pattern pattern = generatePattern(words, operators);
        return pattern;
    }
    /**
     * This method is used to pre process the given query and tokenize the given query.
     * @return list of requested words.
     */
    private List<String> wordProcess() {
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
     * @return list of logic operators in given query.
     */
    private List<String> operatorProcess() {
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
    private Pattern generatePattern(List<String> words, List<String> operators) {
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
}
