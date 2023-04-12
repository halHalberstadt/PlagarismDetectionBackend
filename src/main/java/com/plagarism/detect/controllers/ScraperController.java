package com.plagarism.detect.controllers;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.reader.DocumentReader;
import com.plagarism.detect.reader.TextReader;
import com.plagarism.detect.script.Scraper;

import java.io.File;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ScraperController {

   // NOTE this needs to be changed once deployed
   public static final String RELATIVE_FILE_PATH = "src\\main\\java\\com\\plagarism\\detect\\script\\";
   public static final String EXACT_FILE_PATH = "C:\\Users\\smhal\\Documents\\Coding\\detect\\src\\main\\java\\com\\plagarism\\detect\\script\\";

   /*
    * textDocumentReader() reads a document read in
    * as a string rather than an actual file.
    */
   @GetMapping(value = "/text")
   public ArrayList<String> textDocumentReader(@RequestBody String document,
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
    * wordDocumentReader() reads in a imported file that overwrites
    * docFile and then reads in the information and returns the search
    * results of questions found therein or just the questions found.
    */
   @GetMapping(value = "/word")
   public Queries wordDocumentReader(@RequestBody MultipartFile multipartFile,
         @RequestParam(name = "search") boolean search, RedirectAttributes redirectAttributes) throws Exception {
            System.out.println("check 0");
      if (multipartFile.isEmpty()) {
         System.out.println("check 0.1");
         throw new Exception("ERR: Document is Empty");
      }
      System.out.println("check 0.2");
      File docFile = new File("src/main/java/com/plagarism/detect/tmp/docFile.docx");
      System.out.println("check 0.3");
      try {
         multipartFile.transferTo(docFile);
         System.out.println("check 1");
      } catch (Exception e) {
         throw new Exception("ERR: transferTo():\n" + e);
      }
      Queries queries = null; // set as null as a fail test case for later.
      String documentName = docFile.getName();
      if (documentName.contains(".docx") || documentName.contains(".doc")) {
         System.out.println("check 2");
         DocumentReader documentReader = new DocumentReader(docFile);
         System.out.println("check 3");
         documentReader.findQuestions();
         queries = documentReader.getQuestionsAsQueries();
      } else {
         throw new Exception("ERR: documentReader get questions");
      }
      if (!queries.equals(null) && search) {
         Queries queriesFound = null;
         Scraper scraper = new Scraper();
         try {
            queriesFound = scraper.searchQueries(queries);
         } catch (Exception e) {
            throw new Exception("ERR: searchQueries" + e);
         }
         return queriesFound;
      }
      // If none are found, return the empty queries as a not-found signal
      return queries;
   }

   /*
    * info() send a text response of basic information of endpoints on the API
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
    * This is a catch-all error page that gives a basic non-specific error report.
    */
   @GetMapping(value = "/error")
   public String error() {
      System.err.println("Error called");
      return "Sorry, an error occurred";
   }

}
