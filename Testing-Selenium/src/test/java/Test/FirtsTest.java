package Test;

import base.TestBase;
import factory.TLDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

public class FirtsTest extends TestBase {
    @Test
    public void  FirtsTestES(){
        runTest();
    }

    private void runTest() {
        WebDriver webDriver = TLDriverFactory.getTLDriver();
        String url = getURL("");
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        webDriver.findElement(By.name("q")).sendKeys("hola hola");
    }
}


