package com.plagarism.detect.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.reader.TextReader;

/*
 * This class is an adaptation of a python web scraper.
 */
public class webScraper {

    final String BASE_URL = "https://www.google.com/search?q=";
    // String driverPATH = "C:\\Program Files\\chromedriver_win32";

    // public methods
    public webScraper() {
    }

    /*
     * This is an application of the longest common subsequence between 2
     * strings.
     */
    public void lcs() {

    }

    /*
     * 
     */
    public Queries searchQueries(Queries queries) {
        try {
            Queries response = new Queries();
            boolean responseStatus = false;

            for (Query query : queries.getQueries()) {
                double commonChar = 0.0, previous;
                Document doc = Jsoup.connect(BASE_URL + formatQuery(query)).get();
                System.out.println("URL=" + BASE_URL + formatQuery(query));
                responseStatus = false;

                Elements divElements = doc.getElementsByTag("a");

                for (Element element : divElements) {
                    // System.out.println(element.children());
                    String temp = element.toString();
                    // needed to add more than just a '\n' to get the information needed
                    previous = commonChar;
                    if (temp.length() > 1)
                        if (temp.contains("www.chegg.com")) {
                            responseStatus = true;
                            temp = cleanReturnedURL(temp);
                            // need similarity string check

                        }
                    if (commonChar > previous) {
                        response.addQuery(responseStatus, "[" + commonChar + "] " + query.getQueryText());
                    }
                }

                response.addQuery(responseStatus, "[" + commonChar + "] " + query.getQueryText());

            }

            return response;
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
        return null;
    }

    // Private methods

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
