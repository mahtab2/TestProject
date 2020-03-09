package com.ac.oop;

import com.ac.oop.query.SearchEngine;

import java.util.Scanner;
import java.util.Set;

/**
 * user search a boolean statement with OR, AND operators and application return files that maches with given query.
 * implemented using oop.
 *
 * @author  Mahtab Sarlak
 * @version 1.0
 * @since   2020-03-9
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String query=scanner.nextLine();
        SearchEngine searchEngine=new SearchEngine();
        Set<String> fileNameSet =searchEngine.search(query);
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
}
