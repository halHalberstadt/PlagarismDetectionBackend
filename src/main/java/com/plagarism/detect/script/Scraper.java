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
<<<<<<< HEAD:src/main/java/com/plagarism/detect/script/Scraper.java
public class Scraper {
=======
public class webScraper {
>>>>>>> master:src/main/java/com/plagarism/detect/script/webScraper.java

    final String BASE_URL = "https://www.google.com/search?q=";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    // String driverPATH = "C:\\Program Files\\chromedriver_win32";

    // public methods
<<<<<<< HEAD:src/main/java/com/plagarism/detect/script/Scraper.java
    public Scraper() {
=======
    public webScraper() {
>>>>>>> master:src/main/java/com/plagarism/detect/script/webScraper.java
    }

    /*
     * This is an application of the longest common subsequence between 2
     * strings.
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
                // System.out.println("URL=" + BASE_URL + formatQuery(query));
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
                        // response.addQuery(responseStatus, "[" + df.format(common*100) + "%] " + query.getQueryText());
                    }
                }

                // response.addQuery(responseStatus, "[" + df.format(common*100) + "%] " + query.getQueryText());

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

    private double similarity(String first, String second) {
        // System.out.println("first = " + first);
        // System.out.println("second = " + second);
        int beginWord = 0, numberSimilar = 0, numberWords = 0;
        String word;
        for (int i = 0; i < second.length(); i++) {
            if(second.charAt(i) == ' '){
                word = second.substring(beginWord, i);
                numberWords++;
                if(first.contains(word)){
                    numberSimilar++;
                    // System.out.println("print line 91 " + numberSimilar + " " + numberWords);
                }
                beginWord = i+1;
            }
        }
        double percent = (double) numberSimilar/ (double) numberWords;
        // System.out.println("print line 99 " + percent);
        return percent;
    }

    private String cleanReturnedURL(String url) {
        String urlString = url;
        urlString = urlString.substring(9, urlString.indexOf("-q", 9));
        // remove
        int queryIndex = urlString.indexOf("questions-and-answers/"); // length of string = 22
        return urlString.substring(queryIndex + 22);
    }

    /*
     * 
     */
    private String formatQuery(Query query) {
        String formattedQuery = query.getQueryText();
        formattedQuery = formattedQuery.replace("^[0-9]+[.)]", "");
        formattedQuery = formattedQuery.trim();
        formattedQuery = formattedQuery.replace("[?.\"]", "");
        formattedQuery = formattedQuery.replaceAll(" ", "+");
        return formattedQuery;
        // return "chegg+" + formattedQuery;
    }
}

