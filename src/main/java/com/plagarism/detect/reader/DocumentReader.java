package com.plagarism.detect.reader;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.SaveFormat;
import com.plagarism.detect.domain.Queries;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

// imports for regex
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class DocumentReader {
    License licWordToPdf;
    Document document;
    String path;
    String extension;
    ArrayList<String> queries = new ArrayList<>();

    public DocumentReader() {
        // Create a license object to avoid limitations of the trial version
        // while reading the Word file
        try {
            this.licWordToPdf = new License();
            this.licWordToPdf.setLicense("Aspose.Words.lic");
        } catch (Exception e) {
            System.err.println("com.reader.DocumentReaderInitializationException: Error initializing DocumentReader. " +
                    "Nested Error: " + e); // make sure to print the error
        }
    }

    public DocumentReader(String documentPath) {
        // Create a license object to avoid limitations of the trial version
        // while reading the Word file
        try {
            this.licWordToPdf = new License();
            this.licWordToPdf.setLicense("Aspose.Words.lic");
            this.setDocument(documentPath);
        } catch (Exception e) {
            System.err.println("com.reader.DocumentReaderInitializationException: Error initializing DocumentReader. " +
                    "Nested Error: " + e); // make sure to print the error
        }
    }

    /*
     * Set Document sets up the question list and the path to the document
     * we need to read.
     * NOTE: This function requires the path to the document
     */
    public void setDocument(String documentPath) {
        this.document = null; // reset document to ensure document isn't re-read.
        this.queries.clear(); // clear queries for each document to not confuse where each came from.
        try {
            // set basic variables for document reading.
            this.path = documentPath;
            this.document = new Document(documentPath);
            this.extension = this.path.substring(path.lastIndexOf("."));
        } catch (Exception e) {
            this.path = "[NOT SUPPORTED] " + documentPath;
            // Specifies if failure is due to unsupported types or other.
            System.err.println("com.reader.SetDocumentReaderException: Error setting up document text. " +
                    "Nested Error: " + e); // make sure to print the error
        }

    }

    public void setDocument(File file) {
        this.document = null; // reset document to ensure document isn't re-read.
        this.queries.clear(); // clear queries for each document to not confuse where each came from.
        try {
            // set basic variables for document reading.
            this.path = file.getName();

            // since aspose words adds file locations before which I
            // cannot change, I need to make a input stream which it does
            // take, not something I can change but am upset about.
            FileInputStream fileInput = new FileInputStream(file);
            this.document = new Document(fileInput);
            this.extension = this.path.substring(path.lastIndexOf("."));

        } catch (Exception e) {
            this.path = "[NS] " + file.getAbsolutePath();
            // Specifies if failure is due to unsupported types or other.
            System.err.println(path); // make sure to print the error
            System.err.println("Error setting up document text. \n" +
                    "Nested Error: " + e); // make sure to print the error
        }

    }

    public void setDocument(MultipartFile file) {
        this.document = null; // reset document to ensure document isn't re-read.
        this.queries.clear(); // clear queries for each document to not confuse where each came from.
        try {
            // set basic variables for document reading.
            this.path = file.getName();

            // since aspose words adds file locations before which I
            // cannot change, I need to make a input stream which it does
            // take, not something I can change but am upset about.
            InputStream fileInput = file.getInputStream();
            this.document = new Document(fileInput);
            this.extension = this.path.substring(path.lastIndexOf("."));

        } catch (Exception e) {
            // this.path = "[NS] " + file.getName();
            // Specifies if failure is due to unsupported types or other.
            // System.err.println(path); // make sure to print the error
            // System.err.println("Error setting up document text. \n" +
            //         "Nested Error: " + e); // make sure to print the error
        }

    }

    /*
     * readDocument is meant to just read out what the document has without
     * formatting.
     */
    public void readDocument() {
        int line = 0;
        // System.out.println("start reading \"" + path + "\"");
        try {
            for (Object obj : this.document.getChildNodes(NodeType.PARAGRAPH, true)) {
                Paragraph para = (Paragraph) obj;
                System.out.println("" + (++line) + " - " + para.toString(SaveFormat.TEXT));
            }
        } catch (Exception e) {
            System.err.println("com.reader.ReadDocumentReaderException: Error reading document text. " +
                    "Nested Error: " + e); // make sure to print the error
        }
        // System.out.println("done reading \"" + path + "\"");
    }

    /*
     * getDocumentText() this method returns a arrayList of the entire document's
     * text
     * with each line being a entry to the document text.
     */
    public ArrayList<String> getDocumentText() {
        ArrayList<String> documentText = new ArrayList<>();
        int numberLinesRead = 0;
        // System.out.println("start reading \"" + path + "\"");
        try {
            for (Object obj : this.document.getChildNodes(NodeType.BODY, true)) {
                Paragraph para = (Paragraph) obj;
                documentText.add(para.toString(SaveFormat.TEXT));
                numberLinesRead++;
            }

        } catch (Exception e) {
            System.err.println("com.reader.GetDocumentReaderTextException: Error getting document text. " +
                    "# lines read=" + numberLinesRead + "Nested Error: " + e); // make sure to print the error
        }
        // System.out.println("done reading \"" + path + "\"");

        return documentText;
    }

    /*
     * findQuestions() will run through document items and store them into
     * a list of strings.
     */
    public void findQuestions() {
        /*
         * The way I find questions in the documents (word documents) is by
         */
        // System.out.println("start finding questions @ \"" + path + "\"");
        try {
            // find all questions we can possibly get from the document
            this.findFormattedQuestions();
            this.findUnformattedQuestions();
        } catch (Exception e) {
            System.err.println(
                    "com.reader.DocumentReaderFindQuestionsException: Error finding questions from document text. " +
                            "Nested Error: " + e); // make sure to print the error
        }
        // System.out.println("done finding questions @ \"" + path + "\"");

        // this.printAllQueries();
    }

    /*
     * Useful Functions
     */

    /*
     * queriesToTxt() this function saves queries found into a text file
     * for other program's use.
     */

    /*
     * findFormattedQuestions() goes through a loop finding any formatted
     * questions in the document, and saves them to the object not needing
     * a return type.
     * NOTE: this throws an error due to the nature of the objects used
     */
    private void findFormattedQuestions() throws Exception {
        for (Object obj : this.document.getChildNodes(NodeType.PARAGRAPH, true)) {
            Paragraph para = (Paragraph) obj;
            // if the paragraph is in an ordered list, save question as a query.
            if (isFormattedOrderedList(para)) {
                // I need to trim up the line found in the formatted list.
                // System.out.println(para.getText());
                this.queries.add(para.getText());
                // this.queries.add(para.toString(SaveFormat.TEXT).trim());
            }
        }
    }

    /*
     * isFormattedOrderedList()
     * this method just makes sure that the paragraph is an ordered list
     * and thus should be saved.
     */
    private boolean isFormattedOrderedList(Paragraph paragraph) {
        // This is to prevent error for calling .getListLevel(), etc. on null objects.
        if (paragraph.getListFormat().isListItem()) {
            // For the non-null objects we need to get how the "dots/letters" are formatted.
            byte[] bites = paragraph.getListFormat().getListLevel().getNumberFormat().getBytes(StandardCharsets.UTF_8);
            return bites.length == 2 || bites.length == 3;
        }
        return false;
    }

    /*
     * findUnformattedQuestions goes through a loop finding any questions that
     * are in plain text using regex to find them.
     * NOTE: this throws an error due to the nature of the objects used,
     * and that this is less accurate or not as assured to be accurate.
     */
    private void findUnformattedQuestions() throws Exception {
        // TODO test this method findUnformattedQuestions()
        /*
         * pattern finds any leading whitespace followed by a letter or number followed
         * by
         * any ending list character ('.', ')', or '-')
         */
        String pattern = "^\\s*[\\w|\\d]+[\\s]?[.)-]";
        Pattern patternFinder = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        for (Object obj : this.document.getChildNodes(NodeType.PARAGRAPH, true)) {
            Paragraph para = (Paragraph) obj;

            Matcher matcher = patternFinder.matcher(para.toString(SaveFormat.TEXT));
            // If regex is found in that line, save
            if (matcher.find()) {
                // get string version to convert string into formatted version
                String query = para.toString(SaveFormat.TEXT);
                // find last char of ordered list marker then trim whitespace.
                this.queries.add("(unformatted) " + query.substring(getQuestionIndex(query)).trim());
            }
        }
    }

    /*
     * getQuestionIndex() finds the first letter in the question found in
     * findUnformattedQuestions() with the index being the location of the
     * first character.
     * NOTE: not found is -1
     */
    private int getQuestionIndex(String string) {
        for (int location = 0; location < string.length(); location++) {
            // if we find the last part of the "question marker" ('.', ')' or '-')
            if ((string.charAt(location) == '.' ||
                    string.charAt(location) == ')' ||
                    string.charAt(location) == '-')) {
                return location + 1;
            }
        }
        return -1;
    }

    public void printAllQueries() {
        int queryNumber = 0;
        for (String q : this.queries) {
            System.out.println("Query #" + (++queryNumber) + " found: \"" + q + "\"");
        }
        if (queryNumber < 1) {
            System.out.println("No queries found.");
        }
    }

    /*
     * Getters and Setters
     */

    /*
     * This file was originally made before I wrote the Queries objects just to see
     * if
     * this project was feasible and got so distracted I forgot to change this to
     * account
     * for that change, so before I rewrite this entire file I want a working
     * version, thus
     * this method was made and seems off.
     */
    public Queries getQuestionsAsQueries() {
        Queries newQueries = new Queries();
        for (String text : this.queries) {
            newQueries.addQuery(false, text);
            // System.out.println(text);   
        }
        return newQueries;
    }

    public ArrayList<String> getQuestions() {
        return this.queries;
    }

    public String getExtension() {
        return this.extension;
    }
}
