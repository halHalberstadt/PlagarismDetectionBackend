package com.plagarism.detect.reader;

public class TextReader{

    String body;

    /*
     * Set Document sets up the question list and the path to the document
     * we need to read.
     * NOTE: This function requires the path to the document
     */
    public void setDocument(String textBody) {
        this.body = textBody;
    }
}