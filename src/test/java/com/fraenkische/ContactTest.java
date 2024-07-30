package com.fraenkische;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ContactTest {
    WebDriver driver;
    String url = "https://www.fraenkische.com/en/";
    @BeforeTest
    public void setUp(){
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void getContactDetails(){
        WebElement contactButton = driver.findElement(By.linkText("Contact"));
        contactButton.click();

        WebElement title = driver.findElement(By.className("ce_headline"));
        String actualTitle = title.getText();
        String expectedTitle = "Find the right contact person";
        Assert.assertTrue(actualTitle.contains(expectedTitle));

    }
    @AfterTest(alwaysRun = true)
    public void tearDown(){
        //Inchide pagina
        System.out.println("Inchide pagina");
        //driver.close();
    }
}
