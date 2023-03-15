package com.plagarism.detect.controllers;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.domain.QueriesDTO;
import com.plagarism.detect.domain.Query;
import com.plagarism.detect.domain.QueryDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ScraperController {
   // TODO reposiories if needed

   /*
    * Endpoints:
    * 1. send scraper to search each query and return which queries return a
    * positive result
    * 2. compare documents
    * 3. test hello world
    */

   @GetMapping(value = "/scraper")
   public Queries getQueryResults(@RequestBody QueriesDTO queries) {
      Queries newQueries = queriesDTOtoQueries(queries);
      Queries foundOnChegg = useScraper(newQueries);
      return foundOnChegg;
   }

   @GetMapping(value = "/info")
   public String info() {
      return "Info on API: (Under Construction)";
   }

   @GetMapping(value = "/")
   public String home() {
      return "Hello World";
   }

   /*
    * Helper functions:
    * 1. scraper
    * 2. DTO converter
    *    a. QueriesDTO -> Queries
    *    b. QueryDTO -> Query
    */
   private Queries useScraper(Queries quereies) {
      return null;
   }

   private Queries queriesDTOtoQueries(QueriesDTO quereiesDTO){
      Queries queries = new Queries();
      for (QueryDTO queryDTO : quereiesDTO.getListOfQueries()) {
         Query newQuery = queryDTOtoQuery(queryDTO);
         queries.addQuery(newQuery);
      }
      return queries;
   }

   private Query queryDTOtoQuery(QueryDTO queryDTO){
      Query query = new Query();
      query.setQueryText(queryDTO.queryText);
      query.setFoundOnline(queryDTO.foundOnline);
      return query;
   }

}
