package Test;

import base.TestBase;
import factory.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import utils.ExtentTestManager;

public class FirtsTest extends TestBase {
    @Test
    public void  FirtsTestES(){
        runTest();
        test=extent.createTest("FirtsTestES","PASSED TEST CASE");
    }
    @Test
    public void  FirtsTestES23(){
        runTest2();
        test=extent.createTest("FirtsTestES23","PASSED TEST CASE");
    }


    private void runTest() {

        WebDriver  webDriver = TLDriverFactory.getTLDriver();
        String url = getURL("");
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        webDriver.findElement(By.name("q")).sendKeys("hola hola");

        //this.web=webDriver;
    }
    private void runTest2() {

        WebDriver  webDriver = TLDriverFactory.getTLDriver();
        String url = getURL("");
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("wq")));
        webDriver.findElement(By.name("q")).sendKeys("hola hola");

        //this.web=webDriver;
    }
}


