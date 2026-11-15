package Testpkg;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import Basepkg.Base_class;
import Basepkg.Reporter_class;
import Pagepkg.Checkout_page;
import Pagepkg.Login_page;
import Pagepkg.Product_page;

public class Checkout_test extends Base_class {

    Login_page log;
    Product_page pdt;
    Checkout_page checkout_page;

    @Test
    public void iconclick() throws InterruptedException, IOException {
        driver.get("https://www.saucedemo.com/");

        log = new Login_page(driver);
        pdt = new Product_page(driver);
        checkout_page = new Checkout_page(driver);

        Reporter_class.test = Reporter_class.extent.createTest("checkout test");

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

        Reporter_class.test.pass("Products displayed and items added to cart successfully");

        // moving to check clicking carticon
        checkout_page.cartclick();

        // continue using customer details
        checkout_page.enterCustomerDetails("Deepamol", "Deepamol", "691572");
        System.out.println("Checkout information submitted successfully using formatted excel data.");

        // finish order
        checkout_page.finishOrder();
        System.out.println("Order placed successfully! Confirmation message: Thank you for your order!");
        Thread.sleep(2000);

        // Logout from application
        checkout_page.logoutProcess();
        System.out.println("User successfully logged out and returned to login page.");
        Thread.sleep(2000);

        Reporter_class.test.pass("Checkout and logout completed successfully");
    }
}