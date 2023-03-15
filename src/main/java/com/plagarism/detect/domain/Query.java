package com.plagarism.detect.domain;

public class Query {

    private String queryText;
    private boolean foundOnline;

    public Query() {
        queryText = "";
        foundOnline = false;
    }

    public Query(String question) {
        queryText = question;
        foundOnline = false;
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
}
