package ro.electriccastle;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddToCartTest {
    WebDriver driver;
    String url = "https://electriccastle.ro/";
    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void addToCartTest(){

        // https://www.selenium.dev/documentation/webdriver/waits/
        //1.click Allow Cookies

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("CybotCookiebotDialogBodyButtonAccept")));

        WebElement allowCookies = driver.findElement(By.id("CybotCookiebotDialogBodyButtonAccept"));
        allowCookies.click();

        //2.click buy tickets
        //Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.std-button.std-button--light.cta")));
        WebElement buyTickets = driver.findElement(By.cssSelector("a.std-button.std-button--light.cta"));
        buyTickets.click();

        //3. add to cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("section#GENERAL > div > article:nth-of-type(1)  .tag_add_to_cart.tickets__item__select")));
        WebElement addToCart = driver.findElement(By.cssSelector("section#GENERAL > div > article:nth-of-type(1)  .tag_add_to_cart.tickets__item__select"));
        addToCart.click();

        //4. verify success message
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(2) > div.modal.fade.in > div > div > div.modal-header.text-center > h4")));
        WebElement addedToCart = driver.findElement(By.cssSelector("body > div:nth-child(2) > div.modal.fade.in > div > div > div.modal-header.text-center > h4"));
        Assert.assertTrue(addedToCart.isDisplayed());
        System.out.println(addedToCart.getText());
        Assert.assertTrue(addedToCart.getText().toUpperCase().contains("YOU ADDED TO YOUR CART"));



    }


    @AfterTest(alwaysRun = true)
    public void tearDown(){
        //Inchide pagina
        System.out.println("Inchide pagina");
        driver.close();
    }
}
