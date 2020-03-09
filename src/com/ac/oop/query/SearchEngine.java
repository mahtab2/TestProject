package com.ac.oop.query;

import com.ac.oop.config.FileConfiguration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Class for searching given query
 *
 * @author  Mahtab Sarlak
 * @version 1.0
 * @since   2020-03-9
 */
public class SearchEngine {
    private  Map<String,String> fileData;

    public SearchEngine() {
        FileConfiguration fileConfiguration=new FileConfiguration();
        fileData=fileConfiguration.fileProcessing();
    }
    /**
     * This method is used to find files that satisfy requested query.
     * @return list of files that csatisfy requested query.
     */
    public Set<String> search(String query){
       QueryPreProcessing queryPreProcessing=new QueryPreProcessing(query);
       Pattern pattern=queryPreProcessing.getQueryPattern();
        Set<String> fileNameSet = findFile(pattern);
        return fileNameSet;
    }

    /**
     * This method is used to find files that contains requested words.
     * @param pattern This is the first paramter to findFile method. It's regex pattern.
     * @return list of files that contains requested words.
     */
    private Set<String> findFile(Pattern pattern) {
        Set<String> fileNameSet = new HashSet<>();
        for (Map.Entry<String,String> file : fileData.entrySet()){
                Matcher m=pattern.matcher(file.getValue());
                if(m.find()){
                    fileNameSet.add(file.getKey());
                }
             }
        return fileNameSet;
    }

}
