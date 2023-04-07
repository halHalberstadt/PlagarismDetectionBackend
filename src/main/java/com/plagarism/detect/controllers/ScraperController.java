package com.plagarism.detect.controllers;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.reader.DocumentReader;
import com.plagarism.detect.reader.TextReader;
<<<<<<< HEAD
import com.plagarism.detect.script.Scraper;

=======
>>>>>>> master
import java.io.File;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ScraperController {

   // NOTE this needs to be changed once deployed
   public static final String RELATIVE_FILE_PATH = "src\\main\\java\\com\\plagarism\\detect\\script\\";
   public static final String EXACT_FILE_PATH = "C:\\Users\\smhal\\Documents\\Coding\\detect\\src\\main\\java\\com\\plagarism\\detect\\script\\";

   /*
    * TODO Add copy of this for request param
    */
   @GetMapping(value = "/text")
   public ArrayList<String> textDocuemntReader(@RequestBody String document,
         @RequestParam("ordered") boolean orderedQuestions) {
      TextReader textReader = new TextReader();
      textReader.setDocument(document);
      ArrayList<String> questions;
      if (orderedQuestions) {
         questions = textReader.findOrderedQuestions();
      } else {
         questions = textReader.findUnorderedQuestions();
      }
      return questions;
   }

   /*
    * TODO
    */
   @GetMapping(value = "/word")
<<<<<<< HEAD
   public String wordDocumentReader(@RequestBody MultipartFile document,
         @RequestParam(name = "search") boolean search, RedirectAttributes redirectAttributes) {
      if (document.isEmpty()) {
         // throw new Exception("Document is empty");
         return null;
      }
      File docFile = new File("src/main/java/com/plagarism/detect/tmp/docFile.docx");

      // 0. read in document
      try {
         document.transferTo(docFile);
         // System.out.println("doc type = " + docFile.getName());
      } catch (Exception e) {
         // e.printStackTrace();

      }

      Queries queries = null;

      String documentName = docFile.getName();
      // 1. check if document is a word document
      if (documentName.contains(".docx") || documentName.contains(".doc")) {
         // 2. if it is a word document, read questions in document and return here
         DocumentReader documentReader = new DocumentReader();
         documentReader.setDocument(docFile);
         documentReader.findQuestions();
         queries = documentReader.getQuestionsAsQueries();
      } else {

         // throw new Exception("File" + documentName + "not a supported file type.");

      }

      // 3b. search for questions and return results
      if (!queries.equals(null) && search) {
         Queries queriesFound = null;

         Scraper scraper = new Scraper();
        try {
         queriesFound = scraper.searchQueries(queries);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
        return queriesFound.toJSON();
      }
      // 3. if they don't need to be searched for, return found questions
      // I hate that I need to specify this but I cannot re-route and return
      // the objects I want, will fix in cleanup after this all works.
      return queries.toJSON();
=======
   public Queries wordDocuemntReader(@RequestBody String document) {
      return null;
>>>>>>> master
   }

   /*
    * TODO
    * info send a text response of basic information of endpoints on the API
    */
   @GetMapping(value = "/info")
   public String info() {
      return "Info on API: (Under Construction)";
   }

   /*
    * TODO
    * this is he home or landing page mostly as a test to make sure the API is
    * running.
    */
   @GetMapping(value = "/")
   public String home() {
      return "Plagiarism Detection Service.";
   }

   /*
    * TODO
    * This is a catch-all error page that gives a basic non-descript error report.
    */
   @GetMapping(value = "/error")
   public String error() {
      System.err.println("Error called");
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

}
