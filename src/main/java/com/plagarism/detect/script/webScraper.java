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

            for(Query query : queries.getQueries()){
                Document doc = Jsoup.connect(BASE_URL + formatQuery(query)).get();
                System.out.println("URL=" + BASE_URL + formatQuery(query));
                responseStatus = false;
                
                Elements divElements = doc.getElementsByClass("qLRx3b tjvcx GvPZzd cHaqb");
                // Elements cheggElements = doc.getElementsContainingText("Chegg");
                for (Element element : divElements) {
                    String temp = element.text();
                    System.out.println(temp);
                    // needed to add more than just a '\n' to get the information needed
                    if(temp.length() > 1)
                        if(temp.contains("www.chegg.com")){
                            responseStatus = true;
                            System.out.println("turned true");
                        }
                }

                response.addQuery(responseStatus, query.getQueryText());
                
                // for (Element element : cheggElements) {
                //     String temp = element.ownText();
                //     if(temp.length() > 1)
                //         response.addQuery(true, temp);
                // }
            }
            /*<cite class="qLRx3b tjvcx GvPZzd cHaqb" role="text" style="max-width:315px">https://www.chegg.com<span class="dyjrff qzEoUe" role="text"> › questions-and-answers › con...</span></cite>*/
        
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
    private String formatQuery(Query query){
        String formattedQuery = query.getQueryText();
        formattedQuery = formattedQuery.replace("^[0-9]+[.)]", "");
        formattedQuery = formattedQuery.trim();
        formattedQuery = formattedQuery.replace("[?.\"]", "");
        formattedQuery = formattedQuery.replaceAll(" ", "+");
        return formattedQuery;
        // return "chegg+" + formattedQuery;
    }
}
