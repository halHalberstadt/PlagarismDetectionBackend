package com.plagarism.detect.script;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.plagarism.detect.domain.Queries;
import com.plagarism.detect.reader.TextReader;

/*
 * This class is an adaptation of a python web scraper.
 */
public class webScraper {
 
    public WebDriver driver;

    final String BASE_URL = "https://www.google.com/";
    // String driverPATH = "C:\\Program Files\\chromedriver_win32";

    // public methods
    public webScraper() {
    }

    /*
     * This is an application of the longest common subsequence between 2
     * strings.
     */
    public void lcs() {

    }

    /*
     * 
     */
    public List<String> searchForRequests() {
        WebDriver driver = new FirefoxDriver();

        String baseUrl = "https://www.google.com/search?q=";
        String expectedText = "Chegg.com";
        String actualText = "";

        driver.get(baseUrl);

        List<WebElement> textBox = driver.findElements(By.className("LC20lb MBeuO DKV0Md"));

        // actualTitle = driver.getTitle();

        // if (actualTitle.contentEquals(expectedTitle)){
        // System.out.println("Test Passed!");
        // } else {
        // System.out.println("Test Failed");
        // }
        System.out.println(textBox.get(0).getText());
        // close Fire fox
        driver.close();
        return formatResponses(textBox);
    }

    // Private methods

    /*
     * 
     */
    private Queries loadTxtData() throws Exception{
        Queries queries = new Queries();
        BufferedReader reader;

        reader = new BufferedReader(new FileReader("questions.txt"));
        String line = reader.readLine();

        while (line != null) {
            queries.addQuery(false, line);
            System.out.println(line);
            // read next line
            line = reader.readLine();
        }

        reader.close();

        return queries;
    }

    /*
     * 
     */
    private Queries loadData(String data) {
        TextReader textReader = new TextReader();
        textReader.setDocument(data);
        List<String> list = textReader.findOrderedQuestions();

        Queries queries = new Queries();
        for (String string : list) {
            queries.addQuery(false, string);
        }
        return queries;
    }

    
    /*
     * 
     */
    private List<String> formatResponses(List<WebElement> data) {
        List<String> list = new ArrayList<String>();
        for (WebElement webElement : data) {
            list.add(webElement.getText());
        }
        return list;
    }
}
