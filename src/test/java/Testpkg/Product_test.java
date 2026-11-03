package Testpkg;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;

import Basepkg.Base_class;
import Pagepkg.Checkout_page;
import Pagepkg.Login_page;
import Pagepkg.Product_page;
import Basepkg.Reporter_class;

public class Product_test extends Base_class {

	@Test(priority = 1)
    public void logoValidationTest() throws IOException, InterruptedException {

        // Initialize objects
        Login_page log = new Login_page(driver);
        Product_page pdt = new Product_page(driver);

        // Open base URL and perform login
        driver.get("https://www.saucedemo.com/");
        log.setvalue("standard_user", "secret_sauce");
        log.login_button();

        // Create Extent Test report
        test = extent.createTest("Logo Validation Test");

        WebElement x = pdt.logoverify();

        if (x.isDisplayed()) {
            test.pass("Logo displayed in product page");
        } else {
            String path = Reporter_class.screenshotmethodString(
                driver, "logo_failed");

            test.addScreenCaptureFromPath(path);
            test.fail("Logo not displayed");
        }
    }

    @Test(priority = 2)
    public void addItemToCartAndDisplayProductsTest()
            throws InterruptedException, IOException {

        // Initialize objects
        Login_page log = new Login_page(driver);
        Product_page pdt = new Product_page(driver);

        // Open website and login
        driver.get("https://www.saucedemo.com/");
        log.setvalue("standard_user", "secret_sauce");
        log.login_button();

        test = extent.createTest(
            "Add Items To Cart And Display Products Test");

        // Select Z to A
        pdt.selectZtoA();
        Thread.sleep(3000);

        // Add items to cart
        pdt.additems();

        // Get product details list
        List<WebElement> dt1 = pdt.productdtls();

        System.out.println("Total Number of Products: " + dt1.size());

        // Print product details
        for (WebElement product : dt1) {
            System.out.println(product.getText());
        }

        test.pass("Products displayed and items added to cart successfully");
    }

    @Test(priority = 3)
    public void productDetailsReportTest() throws InterruptedException {
        test = extent.createTest("Product Details Report Test");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        System.out.println("\n===== PRODUCT DETAILS REPORT =====");
        
        for (WebElement product : products) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            String price = product.findElement(By.className("inventory_item_price")).getText();
            String description = product.findElement(By.className("inventory_item_desc")).getText();
            
            System.out.println("Product Name: " + name);
            System.out.println("Product Price: " + price);
            System.out.println("Product Description: " + description);
            System.out.println("--------------------------------------------------");
            
            test.info("<b>Product Name:</b> " + name);
            test.info("<b>Product Price:</b> " + price);
            test.info("<b>Product Description:</b> " + description);
        }
        
        System.out.println("Total Products: " + products.size());
        test.pass("All product details displayed successfully");
    }
}