package com.plagarism.detect.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.script.Scraper;

@RestController
public class QueryController {

    // NOTE this needs to be changed once deployed
    // public static final String ORIGIN_URL = "http://website-spring.herokuapp.com/";
    public static final String ORIGIN_URL = "http://localhost:3000";

    /*
     * getQueryResults returns the results for a string input like
     * those from textboxes
     */
    @CrossOrigin(origins = ORIGIN_URL)
    @GetMapping(value = "/scraper")
    public Queries getQueryResults(@RequestBody String body) {
        // System.out.println(body);
        Queries searchQueries = fromBody(body);
        Queries queries = null;
        Scraper scraper = new Scraper();
        try {
            queries = scraper.searchQueries(searchQueries);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
        return queries;
    }

    /*
     * this is an example Queries return endpoint
     */
    @CrossOrigin(origins = ORIGIN_URL)
    @GetMapping(value = "/exampleQueries")
    public String exampleQueries() {
        Query example;
        ArrayList<Query> listofQueries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            example = new Query();
            example.setFoundOnline(false);
            example.setQueryText("Example Question #" + i + "?");
            listofQueries.add(example);
        }

        Queries queries = new Queries(listofQueries);
        return queries.toJSON();
    }

    /*
     * this is an example Query return endpoint
     */
    @CrossOrigin(origins = ORIGIN_URL)
    @GetMapping(value = "/exampleQuery")
    public Query exampleQuery() {
        Query example = new Query();
        example.setFoundOnline(false);
        example.setQueryText("Example Text single query");
        return example;
    }

    /*
     * this is the document comparison endpoint. (in progress)
     */
    // @GetMapping(value = "/compare")
    // public void compareDocuments(@RequestBody Object param) {

    // }

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