package com.plagarism.detect.script;

import java.text.DecimalFormat;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.Query;

/*
 * This class is an adaptation of a python web scraper.
 */
public class Scraper {

    final String BASE_URL = "https://www.google.com/search?q=";
    // format for percentage likelihood found.
    private static final DecimalFormat df = new DecimalFormat("0.00");

    // public methods
        
    /*
     * basic Constructor
     */
    public Scraper() {
    }

    /*
     * This is an application of the longest common subsequence between 2
     * strings. 
     * NOT IMPLEMENTED IN CURRENT VERSION
     */
    // public void lcs() {

    // }

    /*
     * 
     */
    public Queries searchQueries(Queries queries) {
        try {
            Queries response = new Queries();
            String queryText;

            for (Query query : queries.getQueries()) {
                double common = 0.0, previous;
                Document doc = Jsoup.connect(BASE_URL + formatQuery(query)).get();
                queryText = query.getQueryText();

                Elements divElements = doc.getElementsByTag("a");

                for (Element element : divElements) {
                    String temp = element.toString();
                    // needed to add more than just a '\n' to get the information needed
                    previous = common;
                    if (temp.length() > 1)
                        if (temp.contains("www.chegg.com")) {
                            temp = cleanReturnedURL(temp);
                            // need similarity string check
                            common = similarity(temp, queryText);
                        }
                    if (common > previous) {
                    }
                }


                if(common > .7){ // with almost all the words the same in query and the link they chose
                    response.addQuery(true, df.format(common*100) + "%->" +query.getQueryText());
                } else {
                    response.addQuery(false, df.format(common*100) + "%->" +query.getQueryText());
                }
            }

            return response;
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    // Private methods

    /*
     * 
     */
    private double similarity(String first, String second) {
        int beginWord = 0, numberSimilar = 0, numberWords = 0;
        String word;
        for (int i = 0; i < second.length(); i++) {
            if(second.charAt(i) == ' '){
                word = second.substring(beginWord, i);
                numberWords++;
                if(first.contains(word)){
                    numberSimilar++;
                }
                beginWord = i+1;
            }
        }
        double percent = (double) numberSimilar/ (double) numberWords;
        return percent;
    }

    /*
     * 
     */
    private String cleanReturnedURL(String url) {
        String urlString = url;
        urlString = urlString.substring(9, urlString.indexOf("-q", 9));
        // remove beginning of URL
        int queryIndex = urlString.indexOf("questions-and-answers/"); // length of string = 22
        return urlString.substring(queryIndex + 22);
    }

    /*
     * 
     */
    private String formatQuery(Query query) {
        String formattedQuery = query.getQueryText();
        // format query for easy searchability and comparison
        formattedQuery = formattedQuery.replace("^[0-9]+[.)]", "");
        formattedQuery = formattedQuery.trim();
        formattedQuery = formattedQuery.replace("[?.\"]", "");
        formattedQuery = formattedQuery.replaceAll(" ", "+");
        return formattedQuery; // get results of unspecified search.
        // return "chegg+" + formattedQuery; // get more chegg results
    }
}

