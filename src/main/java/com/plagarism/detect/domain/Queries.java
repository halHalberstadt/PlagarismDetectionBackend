package com.plagarism.detect.domain;

import java.util.ArrayList;

public class Queries {
    int numberQueries;
    ArrayList<Query> listOfQueries = new ArrayList<Query>();

    public Queries() {
        this.listOfQueries = new ArrayList<Query>();
    }

    public Queries(ArrayList<Query> queries) {
        this.listOfQueries = queries;
    }

    /*
     * addQuery returns the text of the query in case the
     * user wants to check which query was added
     */
    public String addQuery(Query query) {
        this.listOfQueries.add(query);
        // return the newly added query text
        this.numberQueries = this.listOfQueries.size();
        return listOfQueries.get(listOfQueries.size()).getQueryText();
    }

    /*
     * addQuery adds a query with the data given
     */
    public void addQuery(Boolean found, String query) {
        this.listOfQueries.add(new Query(found, query));
    }

    // Getters

    public Query[] getQueries() {
        Query[] returnedQueries = new Query[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query;
            queryNumber++;
        }
        return returnedQueries;
    }

    public String[] getQueriesText() {
        String[] returnedQueries = new String[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query.getQueryText();
            queryNumber++;
        }
        return returnedQueries;
    }

    public Boolean[] getFoundQueries() {
        Boolean[] returnedQueries = new Boolean[listOfQueries.size()];
        int queryNumber = 0;
        for (Query query : listOfQueries) {
            returnedQueries[queryNumber] = query.getFoundOnline();
            queryNumber++;
        }
        return returnedQueries;
    }

    @Override
    public String toString() {
        String returnString = "{\n" + "\"numberQueries\":" + this.numberQueries + ",\n";

        for (Query iterableQuery : this.listOfQueries) {
            returnString += "\t{";
            returnString += "\t\"foundOnline\":"+ iterableQuery.getFoundOnline() + "";
            returnString += "\t}";
            if(iterableQuery.equals(listOfQueries.get(this.numberQueries)))
                returnString += "\n";
            else 
                returnString += ",\n";
        }
        return returnString;
    }
}
