package com.plagarism.detect.controllers;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aspose.words.Document;
import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.reader.DocumentReader;
import com.plagarism.detect.reader.TextReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ScraperController {

   // NOTE this needs to be changed once deployed
   public static final String RELATIVE_FILE_PATH = "src\\main\\java\\com\\plagarism\\detect\\";
   public static final String EXACT_FILE_PATH = "C:\\Users\\smhal\\Documents\\Coding\\detect\\src\\main\\java\\com\\plagarism\\detect\\script\\";

   /*
    * textDocumentReader takes in a document and finds ordered or unordered
    * questions
    * it can also search for the questions if needed.
    */
   @GetMapping(value = "/text")
   public ArrayList<String> textDocumentReader(@RequestBody String document,
         @RequestParam(name = "ordered") boolean orderedQuestions) {

      TextReader textReader = new TextReader();
      textReader.setDocument(document);
      ArrayList<String> questions;
      if (orderedQuestions) {
         questions = textReader.findOrderedQuestions();
      } else {
         questions = textReader.findUnorderedQuestions();
      }

      // TODO add search functionality
      return questions;
   }

   /*
    * TODO send a Word doc to this and see what happens
    */
   @GetMapping(value = "/word")
   public String wordDocumentReader(@RequestBody MultipartFile document,
         @RequestParam(name = "search") boolean search, RedirectAttributes redirectAttributes) {
      if (document.isEmpty()) {
         // throw new Exception("Document is empty");
         return null;
      }
      File docFile = new File("../tmp/docFile.docx");

      // 0. read in document
      try {
         document.transferTo(docFile);
         // System.out.println("doc type = " + docFile.getName());
      } catch (Exception e) {
         e.printStackTrace();
      }

      Queries queries = null;

      String documentName = docFile.getName();
      // 1. check if document is a word document
      if (documentName.contains(".docx") || documentName.contains(".doc")) {
         // 2. if it is a word document, read questions in document and return here
         DocumentReader documentReader = new DocumentReader(documentName);
         documentReader.findQuestions();
         queries = documentReader.getQuestionsAsQueries();
      } else {
         
         // throw new Exception("File" + documentName + "not a supported file type.");
         
      }

      // 3b. search for questions and return results
      if(!queries.equals(null) && search){
         redirectAttributes.addAttribute(queries.toJSON());
         return "redirect:/scraper";
      }
      // 3. if they don't need to be searched for, return found questions
      // I hate that I need to specify this but I cannot re-route and return 
      // the objects I want, will fix in cleanup after this all works.
      return queries.toJSON();
   }

   /*
    * /info sends a text response of basic information of endpoints on the API for
    * new users.
    */
   @GetMapping(value = "/info")
   public String info() {
      return "Basic endpoints:\n" +
            "- /info: You're already here, so you can see what this does." +
            "- /scraper: This takes in queries and then returns the confidence we think the text in the query were posted to Chegg.com.\n"
            +
            "- /scraperText: This does the same as /scraper but with just text files (under construction).\n" +
            "- /word: This takes in a word document and can return the questions found, and even search for them if you'd like (under construction).\n"
            +
            "- /text: This does the same as /word but with text files specifically.\n" +
            "- /exampleQueries: Example Queries object response for API testing.\n" +
            "- /exampleQuery: Example Queries object response for API testing.\n" +
            "(that's all of the endpoints that are currently working)";
   }

   /*
    * TODO
    * this is he home or landing page mostly as a test to make sure the API is
    * running.
    */
   @GetMapping(value = "/")
   public String home() {
      return "Plagiarism Detection Service.\n" +
            "Basic endpoints on /info" +
            "Credit: Michael Silva, Elijah Barsky-Ex, Hal Halberstadt, Julian Diaz, Luz Violeta Robles";
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
    * Helper functions
    */

   @SuppressWarnings(value = "unused")
   private void testDocumentReader() {
      DocumentReader reader = new DocumentReader();
      ArrayList<String> fileLocations = findDocuments();

      for (String fileLocation : fileLocations) {
         System.out.println("Reading document @ " + fileLocation);
         reader.setDocument(fileLocation);
         // make sure I am reading the documents properly test
         // reader.readDocument();
         try {
            reader.findQuestions();
         } catch (Exception e) {

            System.out.println("error finding questions error = " + e);
         }
         // now we read out questions found if any
         // ArrayList<String> questions = reader.getQuestions();
         // if (reader.getQuestions().size() == 0) {
         // System.out.println("No questions found");
         // } else {
         // System.out.println("Questions found");
         // int numberQuestion = 1;
         // for (String question : questions) {
         // System.out.println("" + (numberQuestion++) + " - " + question);
         // }
         // }
      }
   }

   private static ArrayList<String> findDocuments() {
      // Folder path and initialization of returned list
      File folder = new File(EXACT_FILE_PATH);
      ArrayList<String> listOfFileLocations = new ArrayList<String>();

      // grab all the files
      File[] listOfFiles = folder.listFiles();

      // grab all the files and dir at the location, then put them into the list
      for (int i = 0; i < listOfFiles.length; i++) {
         if (listOfFiles[i].isFile()) {
            // System.out.println(listOfFiles[i].getName()); // check to make sure all files
            // are grabbed

            // Need to format the file location to get all exact paths
            listOfFileLocations.add(EXACT_FILE_PATH + listOfFiles[i].getName());
         }
      }

      return listOfFileLocations;
   }

   /*
    * 
    */
   private boolean isWordDocument(File file) {

      return false;
   }
}
