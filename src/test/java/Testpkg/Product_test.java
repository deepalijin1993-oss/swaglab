package Testpkg;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Basepkg.Base_class;
import Basepkg.Reporter_class;
import Pagepkg.Login_page;
import Pagepkg.Product_page;

public class Product_test extends Base_class {

    Login_page log;
    Product_page pdt;

    @Test(priority = 1)
    public void logoValidationTest() throws IOException, InterruptedException {
        // Initialize objects
        log = new Login_page(driver);
        pdt = new Product_page(driver);

        // Open base URL and perform login
        driver.get("https://www.saucedemo.com/");
        log.setvalue("standard_user", "secret_sauce");
        log.login_button();

        // Create Extent Test report
        Reporter_class.test = Reporter_class.extent.createTest("Logo Validation Test");

        WebElement logoElement = pdt.logoverify();
        if (logoElement.isDisplayed()) {
            Reporter_class.test.pass("Logo displayed in product page");
        } else {
            String path = Reporter_class.screenshotmethodString(driver, "logo_failed");
            Reporter_class.test.addScreenCaptureFromPath(path);
            Reporter_class.test.fail("Logo not displayed");
            Assert.fail("Logo not displayed");
        }
    }

    @Test(priority = 2)
    public void addItemToCartAndDisplayProductsTest() throws InterruptedException, IOException {
        // Initialize objects
        log = new Login_page(driver);
        pdt = new Product_page(driver);

        // Open website and login
        driver.get("https://www.saucedemo.com/");
        log.setvalue("standard_user", "secret_sauce");
        log.login_button();

        Reporter_class.test = Reporter_class.extent.createTest("Add Items To Cart And Display Products Test");

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

        Reporter_class.test.pass("Products displayed and items added to cart successfully");
    }

    @Test(priority = 3)
    public void productDetailsReportTest() throws InterruptedException, IOException {
        Reporter_class.test = Reporter_class.extent.createTest("Product Details Report Test");

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

            Reporter_class.test.info("<b>Product Name:</b> " + name);
            Reporter_class.test.info("<b>Product Price:</b> " + price);
            Reporter_class.test.info("<b>Product Description:</b> " + description);
        }

        System.out.println("Total Products: " + products.size());
        Reporter_class.test.pass("All product details displayed successfully");
    }
}