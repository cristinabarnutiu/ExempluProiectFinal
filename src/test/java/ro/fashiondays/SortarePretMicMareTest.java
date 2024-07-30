package ro.fashiondays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SortarePretMicMareTest {

    WebDriver driver;

    @Parameters({"browserParam"})
    @BeforeTest(alwaysRun = true)
    public void setUp(@Optional("edge") String browser) {
        //open page
        String url = "https://www.fashiondays.ro";
        //driver = new ChromeDriver();

        switch (browser) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                driver = new EdgeDriver();
        }

        driver.get(url);
        driver.manage().window().maximize();
    }
    @Test
    public void sortarePretMicMareTest(){
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("/html//a[@id='accept-cookie-policy']")));

        WebElement allowCookies =driver.findElement(By.xpath("/html//a[@id='accept-cookie-policy']"));
        allowCookies.click();

        //Cautare dupa nume
        WebElement butonCautare = driver.findElement(By.xpath("//a[@id='mobile-search']/span[@class='text-placeholder']"));
        butonCautare.click();
        WebElement activareCautare = driver.findElement(By.xpath("/html//input[@id='search-input']"));
        activareCautare.sendKeys("genti");

        WebElement apasaButon = driver.findElement(By.xpath("//button[@id='search-submit']/span[@class='icon-fdux_search']"));
        apasaButon.click();

        //sortare dupa pret mic
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"filterDrop\"]")));
        WebElement sortD= driver.findElement(By.xpath("//button[@class=\"filterDrop\"]"));
        sortD.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@value='lowest_price']")));
        WebElement sortLowestPrice= driver.findElement(By.xpath("//label[@value='lowest_price']"));
        sortLowestPrice.click();


        //asteapta 3 secunde pt sortarea produselor
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //verificam ca se incarca pagina cu parametru lowest_price
        Assert.assertTrue(driver.getCurrentUrl().contains("lowest_price"));

        //primul produs din lista
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='products-listing-list']/li[1]/a//div[@class='sale-wrapper']/span")));
        WebElement firstProduct = driver.findElement(By.xpath("//ul[@id='products-listing-list']/li[1]/a//div[@class='sale-wrapper']/span"));
        //pretul primului produs - String
        String firstProductPrice = firstProduct.getText().replace(" lei","");
        //preturl primului produs - int
        int price1 = Integer.valueOf(firstProductPrice);


        //al doilea produs din lista
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='products-listing-list']/li[2]/a//div[@class='sale-wrapper']/span")));
        WebElement secondProduct = driver.findElement(By.xpath("//ul[@id='products-listing-list']/li[2]/a//div[@class='sale-wrapper']/span"));
        //pretul celui de-al doilea produs - String
        String secondProductPrice = secondProduct.getText().replace(" lei", "");
        //pretul celui de-al doilea produs - int
        int price2 = Integer.valueOf(secondProductPrice);

        Assert.assertTrue(price1<price2);



        //Select sortS =new Select(sortD);
        //sortS.selectByValue("lowest_price");

      /*  List<WebElement> pretEl =driver.findElement(By.id(""));//nu gasesc de unde sa copiex lista pentru xparh, id, css pentru cel mai mic pret
        List<Double> pretProduse = new ArrayList<>();
        for(WebElement pret:pretEl){
            pretProduse.add(Double.parseDouble(pret.getText().substring(1)));
        }
        List<Double>asteptPret = new ArrayList<>(pretProduse);
        Collections.sort(asteptPret);
        Assert.assertEquals(asteptPret, pretProduse);
        System.out.println("Test passed - cautare dupa pret reusita");*/


    }
    @AfterTest(alwaysRun = true)
    public void tearDown(){
        driver.close();
    }

    public void wait(int milliseconds) {
        try {
            driver.wait(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
