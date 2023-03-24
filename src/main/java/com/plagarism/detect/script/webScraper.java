package com.plagarism.detect.script;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

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
    public void searchForRequests() {
        System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        // comment the above 2 lines and uncomment below 2 lines to use Chrome
        // System.setProperty("webdriver.chrome.driver","G:\\chromedriver.exe");
        // WebDriver driver = new ChromeDriver();

        String baseUrl = "https://www.google.com/search?q=";
        String expectedText = "";
        String actualText = "";

        driver.get(baseUrl);

        WebElement textBox = driver.findElement(By.className("LC20lb MBeuO DKV0Md"));

        // actualTitle = driver.getTitle();

        // if (actualTitle.contentEquals(expectedTitle)){
        // System.out.println("Test Passed!");
        // } else {
        // System.out.println("Test Failed");
        // }
        System.out.println(textBox);

        // close Fire fox
        driver.close();
    }

    // Private methods

    /*
     * 
     */
    private void formatResponses() {

    }

    /*
     * 
     */
    private void formatRequests() {

    }

    /*
     * 
     */
    private void loadTxtData() {

    }

    /*
     * 
     */
    private void loadData() {

    }
}
