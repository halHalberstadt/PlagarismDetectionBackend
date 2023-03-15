package com.plagarism.detect.domain;

import java.util.ArrayList;

public class Queries {
    ArrayList<Query> listOfQueries;

    public Queries(){
        listOfQueries = new ArrayList<>();
    }

    /*
     * addQuery returns the text of the query in case the
     * user wants to check which query was added
     */
    public String addQuery(Query query){
        listOfQueries.add(query);
        // return the newly added query text
        return listOfQueries.get(listOfQueries.size()).getQueryText();
    }

    // Getters

    public Query[] getQueries(){
        Query[] returnedQueries = new Query[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query;
            queryNumber++;
        }
        return returnedQueries;
    }

    public String[] getQueriesText(){
        String[] returnedQueries = new String[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query.getQueryText();
            queryNumber++;
        }
        return returnedQueries;
    }

    public Boolean[] getFoundQueries(){
        Boolean[] returnedQueries = new Boolean[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query.getFoundOnline();
            queryNumber++;
        }
        return returnedQueries;
    }
}
