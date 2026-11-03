package Basepkg;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class Base_class {

    public static ExtentReports extent;
    public static ExtentTest test;
    public static WebDriver driver;

    public String url = "https://www.saucedemo.com/";

    @BeforeSuite
    public void browser_launch() {
        extent = Reporter_class.sam();
       driver = new ChromeDriver();
      driver.get(url);
        
  }

    @AfterSuite
    public void teardown() {
        driver.quit();
        extent.flush();
    }
}