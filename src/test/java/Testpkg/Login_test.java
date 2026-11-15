package Testpkg;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Basepkg.Base_class;
import Basepkg.Reporter_class;
import Pagepkg.Login_page;
import Utilitiespkg.ExcelUtil;

public class Login_test extends Base_class {

    Login_page log;

    // DATA PROVIDER FOR LOGIN
    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {
        String path = "C:\\Users\\hp\\Desktop\\Selenium Automation\\data.xlsx";
        return ExcelUtil.getTestData(path, "Sheet1");
    }

    // 1. LOGIN TEST (Using DataProvider)
    @Test(dataProvider = "loginData", priority = 1)
    public void logintest(String username, String password) throws InterruptedException, IOException {
        driver.get("https://www.saucedemo.com/");

        Reporter_class.test = Reporter_class.extent.createTest("Login Test - " + username);
        log = new Login_page(driver);

        log.setvalue(username, password);
        log.login_button();

        System.out.println("Executed for: " + username);
        String currentUrl = driver.getCurrentUrl();

        boolean loginCheck = currentUrl.contains("inventory.html");

        // LOGIN SUCCESS CONDITION
        if (loginCheck) {
            Reporter_class.test.pass("Login successful for: " + username);
        } 
        // LOGIN FAILURE CONDITION (Screenshot & Extent Report)
        else {
            String screenshotName = "LoginFailed_" + username;
            Reporter_class.screenshotmethodString(driver, screenshotName);

            String fullPath = System.getProperty("user.dir") + "\\screenshot\\" + screenshotName + ".png";
            Reporter_class.test.addScreenCaptureFromPath(fullPath);
            Reporter_class.test.fail("Login failed for: " + username);
        }
    }

 // 2. LOGO VERIFICATION TEST
    @Test(priority = 2)
    public void logoverification() throws IOException {
        driver.get("https://www.saucedemo.com/");
        
        Reporter_class.test = Reporter_class.extent.createTest("Logo Validation Test");
        
        log = new Login_page(driver);
        boolean isLogoPresent = log.logoverification();
        
        if (isLogoPresent) {
            Reporter_class.test.pass("Logo verification passed successfully");
        } else {
            String screenshotName = "logo_failed";
            String fullPath = System.getProperty("user.dir") + "\\screenshot\\" + screenshotName + ".png";
            Reporter_class.screenshotmethodString(driver, screenshotName);
            Reporter_class.test.addScreenCaptureFromPath(fullPath);
            Reporter_class.test.fail("Logo verification failed");
            Assert.fail("Logo verification failed");
        }
    }
    // 3. LOGIN BUTTON TEXT / DISPLAY TEST
    @Test(priority = 3)
    public void loginButtonTest() throws IOException {
        driver.get("https://www.saucedemo.com/");

        Reporter_class.test = Reporter_class.extent.createTest("Login Button Validation Test");

        log = new Login_page(driver);
        WebElement btn = log.buttontext();

        if (btn.isDisplayed()) {
            Reporter_class.test.pass("Login button is displayed properly. Text: " + btn.getText());
        } else {
            Reporter_class.test.fail("Login button is not displayed");
            Assert.fail("Login button is not displayed");
        }
    }
}