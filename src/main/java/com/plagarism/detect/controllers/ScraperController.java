package com.plagarism.detect.controllers;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.reader.DocumentReader;
import com.plagarism.detect.reader.TextReader;
import com.plagarism.detect.script.Scraper;

import java.io.File;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ScraperController {

   public static final String FILE_PATH = "src\\main\\java\\com\\plagarism\\detect\\script\\";
   // public static final String ORIGIN_URL = "http://website-spring.herokuapp.com/";
   public static final String ORIGIN_URL = "http://capstone-frontend-plagarism.herokuapp.com/scraper";

   /*
    * textDocumentReader() reads a document read in
    * as a string rather than an actual file.
    */
   @CrossOrigin(origins = ORIGIN_URL)
   @PostMapping(value = "/text")
   public ArrayList<String> textDocuemntReader(@RequestBody String document,
         @RequestParam("ordered") boolean orderedQuestions) {
      TextReader textReader = new TextReader();
      textReader.setDocument(document);
      ArrayList<String> questions;
      if (orderedQuestions) {
         questions = textReader.findOrderedQuestionsText();
      } else {
         questions = textReader.findUnorderedQuestionsText();
      }
      return questions;
   }

   /*
    * wordDocumentReader() reads in a imported file that overwrites
    * docFile and then reads in the information and returns the search
    * results of questions found therein or just the questions found.
    */
   @CrossOrigin(origins = ORIGIN_URL)
   @PostMapping(value = "/word")
   public Queries wordDocumentReader(@RequestBody(required = false) MultipartFile document,
         @RequestParam(name = "search") boolean search, RedirectAttributes redirectAttributes) throws Exception {
      if (document == null) {
         return null;
      }

      String documentExtension = document.getOriginalFilename();
      documentExtension = documentExtension.substring(documentExtension.lastIndexOf('.'));
      File docFile = new File("src/main/java/com/plagarism/detect/tmp/docFile" + documentExtension + "");

      try {
         document.transferTo(docFile);
      } catch (Exception e) {
         // e.printStackTrace();
      }

      Queries queries = new Queries();
      // NOTE this needs to use .contains for some reason that I can't figure out but
      // it is adding an extra invisible character
      if (documentExtension.contains(".docx") || documentExtension.contains(".doc")) {
         DocumentReader documentReader = new DocumentReader();
         documentReader.setDocument(docFile);
         documentReader.findQuestions();
         queries = documentReader.getQuestionsAsQueries();
      } else if (documentExtension.contains(".txt")) {
         TextReader textReader = new TextReader();
         String docText = "";
         for (byte docByte : document.getBytes()) {
            docText += (char) docByte;
            System.out.print((char) docByte);
         }
         textReader.setDocument(docText);
         queries = textReader.getQueries();
      } else {
         // throw new Exception("File" + documentName + "not a supported file type.");
      }
      if (!queries.isEmpty() && search) {
         Queries queriesFound = null;
         Scraper scraper = new Scraper();
         try {
            queriesFound = scraper.searchQueries(queries);
         } catch (Exception e) {
            System.err.println(e.getStackTrace());
         }
         return queriesFound;
      }
      return queries;
   }

   /*
    * info send a text response of basic information of endpoints on the API
    */
   @GetMapping(value = "/info")
   public String info() {
      return "Info on API: (Under Construction)";
   }

   /*
    * this is he home or landing page mostly as a test to make sure the API is
    * running.
    */
   @GetMapping(value = "/")
   public String home() {
      return "Plagiarism Detection Service.";
   }

   /*
    * This is a catch-all error page that gives a basic non-descript error report.
    */
   @GetMapping(value = "/error")
   public String error() {
      System.err.println("Error called");
      return "Sorry, an error occurred";
   }

   /*
    * tester functions
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
         ArrayList<String> questions = reader.getQuestions();
         if (reader.getQuestions().size() == 0) {
            System.out.println("No questions found");
         } else {
            System.out.println("Questions found");
            int numberQuestion = 1;
            for (String question : questions) {
               System.out.println("" + (numberQuestion++) + " - " + question);
            }
         }
      }
   }

   private static ArrayList<String> findDocuments() {
      // Folder path and initialization of returned list
      File folder = new File(FILE_PATH);
      ArrayList<String> listOfFileLocations = new ArrayList<String>();

      // grab all the files
      File[] listOfFiles = folder.listFiles();

      // grab all the files and dir at the location, then put them into the list
      for (int i = 0; i < listOfFiles.length; i++) {
         if (listOfFiles[i].isFile()) {
            // System.out.println(listOfFiles[i].getName()); // check to make sure all files
            // are grabbed

            // Need to format the file location to get all exact paths
            listOfFileLocations.add(FILE_PATH + listOfFiles[i].getName());
         }
      }

      return listOfFileLocations;
   }

}
