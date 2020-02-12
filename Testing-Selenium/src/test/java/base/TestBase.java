package base;

import factory.TLDriverFactory;
import manager.SettingsManager;
import model.Settings;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class TestBase {
    protected FluentWait wait;
    protected Settings settingsModel;
    protected static SettingsManager settingsManager = new SettingsManager();
    protected static String baseURL;

    public static final Logger log = LoggerFactory.getLogger(TestBase.class);


    @BeforeMethod
    @Parameters(value={"browser","platform"})
    public void setupTest(@Optional String browser, @Optional String platform){
        initializeCustomSetting();
        baseURL= settingsModel.getDefaultTestUrl();
        TLDriverFactory.setTLDriver(browser,platform);
        initializeFluentWait(TLDriverFactory.getTLDriver());
    }
    @AfterMethod
    public synchronized void tearDown(){
        log.info("------------------Finish---------------");
        TLDriverFactory.getTLDriver().quit();
    }

    public void waitForPageLoad(){
        ExpectedCondition<Boolean> expection = new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver webDriver) {
                return ((JavascriptExecutor)webDriver).executeAsyncScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(settingsModel.getDefaultSleepTime());
            wait.until(expection);
        }catch (Throwable error){
            Assert.fail("TimeOut waiting for page load request to complete");
        }
    }

    protected String getURL(String dest){
        return baseURL+dest;
    }

    private void initializeFluentWait(WebDriver webDriver) {
        Duration timeOut = Duration.ofSeconds(settingsModel.getDefaultTimeout());
        Duration pollingRate = Duration.ofSeconds(settingsModel.getDefaultPollingRate());

        wait = new FluentWait(webDriver).withTimeout(timeOut).pollingEvery(pollingRate).ignoring(NoSuchElementException.class);
    }

    private void initializeCustomSetting() {
        settingsModel=settingsManager.getCustomSettings("customSettings.json");
    }


}
