package com.plagarism.detect.controllers;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.QueriesDTO;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.domain.QueryDTO;
import com.plagarism.detect.reader.DocumentReader;

import java.io.File;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ScraperController {

   // NOTE this needs to be changed once deployed
   public static final String FILE_PATH = "C:\\Users\\smhal\\Documents\\Coding\\java\\docReader\\demo\\src\\main\\java\\com\\docs\\";
   
   // TODO reposiories if needed

   /*
    * These are all GET requests since it is cheaper and more advantagous for
    * our objective to just return data.
    * Endpoints:
    * 1. send scraper to search each query and return which queries return a
    * positive result
    * 2. compare documents
    * 3. Text document query parser
    * 4. Word document query parser
    * 5. info endoint
    * 6. test hello world
    * 7. Error catcher
    */

   /*
    * TODO
    * getQueryResults
    */
   @GetMapping(value = "/scraper")
   public Queries getQueryResults(@RequestBody QueriesDTO queries) {
      Queries newQueries = queriesDTOtoQueries(queries);
      Queries foundOnChegg = useScraper(newQueries);
      // Put scraper function here
      return foundOnChegg;
   }

   /*
    * TODO
    */
   @GetMapping(value = "/compare")
   public void compareDocuments(@RequestBody Object param) {

   }

   /*
    * TODO
    */
   @GetMapping(value = "/text")
   public Queries textDocuemntReader(@RequestBody String document) {
      return null;
   }

   /*
    * TODO
    */
   @GetMapping(value = "/word")
   public Queries wordDocuemntReader(@RequestBody String document) {
      return null;
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
      return "Plagarism Detection Service.";
   }

   /*
    * TODO
    * This is a catch-all error page that gives a basic non-descript error report.
    */
   @GetMapping(value = "/error")
   public String error() {
      return "Sorry, an error occured";
   }

   /*
    * Helper functions:
    * 1. scraper
    * 2. DTO converter
    * a. QueriesDTO -> Queries
    * b. QueryDTO -> Query
    */
   private Queries useScraper(Queries quereies) {
      return null;
   }

   private Queries queriesDTOtoQueries(QueriesDTO quereiesDTO) {
      Queries queries = new Queries();
      for (QueryDTO queryDTO : quereiesDTO.getListOfQueries()) {
         Query newQuery = queryDTOtoQuery(queryDTO);
         queries.addQuery(newQuery);
      }
      return queries;
   }

   private Query queryDTOtoQuery(QueryDTO queryDTO) {
      Query query = new Query();
      query.setQueryText(queryDTO.queryText);
      query.setFoundOnline(queryDTO.foundOnline);
      return query;
   }

   private void testDocumentReader(){
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
