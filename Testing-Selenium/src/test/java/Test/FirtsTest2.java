package Test;

import base.TestBase;
import factory.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class FirtsTest2 extends TestBase {

    @Test
    public void  FirtsTestES2(){
        runTest();
        test=extent.createTest("FirtsTest2","PASSED TEST CASE");
    }

    private void runTest() {
        log.info("Primer test");
        WebDriver  webDriver = TLDriverFactory.getTLDriver();
        String url = getURL("");
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        webDriver.findElement(By.name("q")).sendKeys("hola hola");

        //this.web=webDriver;
    }
}


