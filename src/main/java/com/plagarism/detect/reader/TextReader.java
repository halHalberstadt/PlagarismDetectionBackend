package com.plagarism.detect.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class should handle all basic text files and basic string entries. 
 * Essentially, direct text enrty and .txt as they both take the same logic.
 */
public class TextReader {

    String body;

    /*
     * This should just handle reading .txt document sent in while word Document
     * implementation is set up.
     */
    public void setDocument(String textBody) {
        this.body = textBody;
    }

    /*
     * Just returns body's text
     */
    public String getDocumentText() {
        return this.body;
    }

    /*
     * 
     */
    public void formatBody() {
        String bodyReplacement = "";

        body = bodyReplacement;
    }

    /*
     * findQuestions() will run through document items and store them into
     * a list of strings.
     */
    public ArrayList<String> findOrderedQuestions() {
        ArrayList<String> foundQuestions = new ArrayList<>();
        
        int lastQuestion = 0;
        String question = "";
        // search for each line ending in '?'
        for (int i = 1; i < body.length(); i++) {
            if (body.charAt(i) == '.') {
                int queryEnd = body.indexOf("?", i);
                question = body.substring(i+1, queryEnd).trim();
                foundQuestions.add(question);
                i = queryEnd;
            }
        }
        return foundQuestions;
    }

    /*
     * findQuestions() will run through document items and store them into
     * a list of strings. The strings 
     */
    public ArrayList<String> findUnorderedQuestions() {
        ArrayList<String> foundQuestions = new ArrayList<>();

        int lastQuestion = 0;
        String question = "";
        // search for each line ending in '?'
        for (int i = 1; i < body.length(); i++) {
            if (body.charAt(i) == '?') {
                question = body.substring(lastQuestion, i).trim();
                if(question.startsWith("?\r\n")){
                    question = question.substring(3);
                } else if (question.startsWith("\r\n")){
                    question = question.substring(2);
                }
                // if question isn't empty, add to questions
                if(question.length() > 0)
                    foundQuestions.add(question);
                // set question from last 
                lastQuestion = i;
            }
        }

        return foundQuestions;
    }
    
    private boolean writeQuestionsToFile(){
        try {
            FileWriter myWriter = new FileWriter("../script/questions.txt");
            myWriter.write(""); // clear the file so no previous questions are there

            for (iterable_type iterable_element : iterable) {
                
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        return false;
    }
}