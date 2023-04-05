package com.plagarism.detect.controllers;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.script.webScraper;

@RestController
public class QueryController {

    // NOTE this needs to be changed once deployed
    public static final String RELATIVE_FILE_PATH = "src\\main\\java\\com\\plagarism\\detect\\script\\";
    public static final String EXACT_FILE_PATH = "C:\\Users\\smhal\\Documents\\Coding\\detect\\src\\main\\java\\com\\plagarism\\detect\\script\\";

    /*
     * TODO
     * getQueryResults
     */
    @GetMapping(value = "/scraper")
    public Queries getQueryResults(@RequestBody String body) {
        // System.out.println(body);
        Queries searchQueries = fromBody(body);
        Queries queries = null;

        webScraper scraper = new webScraper();
        try {
            queries = scraper.searchQueries(searchQueries);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
        return queries;
    }

    /*
     * getQueryResultsPython
     */
    @GetMapping(value = "/scraperText")
    public String getQueryResultsPython(@RequestBody Queries text) {
        writeQuestionsToFile(text);
        // run the Python script
        return runPythonScript();
    }

    private boolean writeQuestionsToFile(Queries questions) {
        try {
            FileWriter myWriter = new FileWriter("../script/questions.txt");
            myWriter.write(""); // clear the file so no previous questions are there

            for (Query question : questions.getQueries()) {
                // '\n' needed to separate questions
                myWriter.append(question.getQueryText() + "\n");
            }

            myWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return false;
    }

    /*
     * TODO
     * this is an example Queries return endpoint
     */
    @GetMapping(value = "/exampleQueries")
    public Queries exampleQueries() {
        Query example;
        ArrayList<Query> listofQueries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            example = new Query();
            example.setFoundOnline(false);
            example.setQueryText("Example Question #" + i + "?");

            // System.out.println("out =>" + example);
            listofQueries.add(example);
        }

        // format response
        Queries queries = new Queries(listofQueries);
        // return queries.toJSON();
        return queries;
    }

    /*
     * TODO
     * this is an example Query return endpoint
     */
    @GetMapping(value = "/exampleQuery")
    public Query exampleQuery() {
        Query example = new Query();
        example.setFoundOnline(false);
        example.setQueryText("Example Text single query");
        return example;
    }

    /*
    * TODO
    * This is a catch-all error page that gives a basic non-descript error report.
    */
    @GetMapping(value = "/error")
    public String error() {
       return "Sorry, an error occurred";
    }
    /*
     * TODO finish endpoint
     * this is the document comparison endpoint. (in progress)
     */
    // @GetMapping(value = "/compare")
    // public void compareDocuments(@RequestBody Object param) {

    // }

    // helper function
    private String runPythonScript() {

        String returnString = "";
        try {
            // using the Runtime exec method:
            String s = "";
            Process p = Runtime.getRuntime().exec("python " + EXACT_FILE_PATH + "webScraper.py");

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // (kept just in case errors are needed for debug)
            // BufferedReader stdError = new BufferedReader(new
            // InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                returnString += s;
                System.out.println(s);
            }

            // read any errors from the attempted command (kept just in case errors are
            // needed for debug)
            // System.out.println("Here is the standard error of the command (if any):\n");
            // while ((s = stdError.readLine()) != null) {
            // System.out.println(s);
            // }

        } catch (IOException e) {
            System.out.println("exception happened: ");
            e.printStackTrace();
            System.exit(-1);
        }

        return returnString;
    }

    private Queries fromBody(String queries) {
        Queries queriesToSend = new Queries();
        int indexOfQuery = queries.indexOf("\"queryText\": \""); // offset of 14
        int indexOffset;
        while (indexOfQuery + 13 < queries.length() && indexOfQuery > 0) {
            indexOffset = indexOfQuery + 14;
            queriesToSend.addQuery(false, queries.substring(indexOffset, queries.indexOf('\"', indexOffset)));
            indexOfQuery = queries.indexOf("\"queryText\": \"", indexOffset);
        }
        return queriesToSend;
    }
}