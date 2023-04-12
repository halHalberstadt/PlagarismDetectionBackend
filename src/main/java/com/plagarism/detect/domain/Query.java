package com.plagarism.detect.domain;

public class Query {

    private boolean foundOnline;
    private String queryText;

    public Query() {
        this.queryText = "";
        this.foundOnline = false;
    }

    public Query(String question) {
        this.queryText = question;
        this.foundOnline = false;
    }

    public Query(Boolean isFound, String question) {
        this.queryText = question;
        this.foundOnline = isFound;
    }

    public void setQueryText(String text) {
        this.queryText = text;
    }

    public void setFoundOnline(Boolean bool){
        this.foundOnline = bool;
    }

    public String getQueryText() {
        return queryText;
    }

    public boolean getFoundOnline() {
        return foundOnline;
    }

    // This is just a manual formatted JSON primarily for readable responses during testing.
    public String toJSON() {
        String returnString = "{\n" + "\t\"foundOnline\": " + foundOnline + 
        ",\n\t\"queryText\": \"" + queryText + "\"\n";
        returnString += "\n}";
        return returnString;
    }
}
