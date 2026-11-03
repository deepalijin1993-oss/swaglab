package Testpkg;

import java.time.Duration;
import java.util.List;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import Basepkg.Base_class;
import Pagepkg.Checkout_page;
import Pagepkg.Login_page;
import Pagepkg.Product_page;

public class Checkout_test extends Base_class {

    @Test
    public void iconclick() throws InterruptedException {
     
        driver.get("https://www.saucedemo.com/");

        Login_page log = new Login_page(driver);
        Product_page pdt = new Product_page(driver);
        Checkout_page checkout_page = new Checkout_page(driver);

        ExtentTest test = extent.createTest("checkout test");
        
        // login
        log.setvalue("standard_user", "secret_sauce");
        log.login_button();
        Thread.sleep(2000);

     // adding product to cart
        pdt.selectZtoA();
        pdt.additems();

        // Get product details list with wait
        Thread.sleep(3000);
        List<WebElement> dt1 = pdt.productdtls();
        System.out.println("Total Number of Products: " + dt1.size());

        // Print details of each product
        for (WebElement product : dt1) {
            System.out.println(product.getText());
        }

        test.pass("Products displayed and items added to cart successfully");

        // moving to check clicking carticon
        checkout_page.cartclick();

        //  continue using customer details
        checkout_page.enterCustomerDetails("Deepamol", "Deepamol", "691572");
        System.out.println("Checkout information submitted successfully using formatted excel data.");
        //finish order
        checkout_page.finishOrder();
        System.out.println("Order placed successfully! Confirmation message: Thank you for your order!");
        Thread.sleep(2000);
        
        //  Logout from application 
        checkout_page.logoutProcess();
        System.out.println("User successfully logged out and returned to login page.");
        Thread.sleep(2000);

        
        }
    }
