package Basepkg;

import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporter_class {
    public static WebDriver driver;
    public static ExtentSparkReporter reporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    public static ExtentReports sam() {
        reporter = new ExtentSparkReporter(".\\Reports\\swaglabreport.html");
        reporter.config().setDocumentTitle("Automation report");
        reporter.config().setReportName("Functional test");
        reporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        extent.setSystemInfo("hostname", "localhost");
        extent.setSystemInfo("os", "windows11");
        extent.setSystemInfo("tester name", "Deepa");
        extent.setSystemInfo("browsername", "chrome");

        return extent;
    }

    @AfterMethod
    public void browserclose(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            if (test != null) {
                test.fail("Test case failed is " + result.getName());
                test.fail(result.getThrowable());
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            if (test != null) {
                test.skip("Test case skipped is " + result.getName());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (test != null) {
                test.pass("Test case passed is " + result.getName());
            }
        }
    }

    public static String screenshotmethodString(WebDriver driver, String screenshotname) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File folder = new File("./screenshot");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String destination = "./screenshot/" + screenshotname + ".png";
        FileHandler.copy(src, new File(destination));

        return destination;
    }
}