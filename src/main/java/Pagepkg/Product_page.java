package Pagepkg;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Product_page {

    WebDriver driver;
    
    // Locators using @FindBy
    @FindBy(xpath = "//div[@class='app_logo']")
    WebElement logo;

    @FindBy(xpath = "//button[text()='Add to cart']")
    List<WebElement> cartbutton;
    
    @FindBy(xpath = "//div[@class='inventory_item_description']")
    List<WebElement> productdetails;
   
   
    @FindBy(xpath = "//select[@class='product_sort_container']")
    WebElement dropdown;

    // Constructor
    public Product_page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
       
    }

    // Method to verify logo
    public WebElement logoverify() {
        return logo;
        
        }
    // Method to add items to cart safely without StaleElementReferenceException
    public void additems() throws InterruptedException {
        
        Thread.sleep(3000);
        
        int totalProducts = cartbutton.size();
        System.out.println("Total Add to Cart Buttons: " + totalProducts);
        
        for (int i = 0; i < totalProducts; i++) {
            WebElement item = cartbutton.get(0);
            item.click();
        }
        System.out.println("Successfully added all products to the cart");
    }
    public void selectZtoA() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sortDropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        Select sel = new Select(sortDropdown);
        sel.selectByValue("za");
    }

    // Method to get product details list
    public List<WebElement> productdtls() {
        return productdetails;
    }
}

