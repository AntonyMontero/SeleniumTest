package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
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
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public class TestBase {
    protected FluentWait wait;
    protected Settings settingsModel;
    protected static SettingsManager settingsManager = new SettingsManager();
    protected static String baseURL;
    protected ExtentHtmlReporter htmlReporter;
    protected ExtentReports extent;
    protected ExtentTest test;
    //public WebDriver web;

    public static final Logger log = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    @Parameters(value = {"browser", "platform"})
    public void setupReport(@Optional String browser, @Optional String platform) {
        log.info("-----------Open Report----------------");
        String workingDir = System.getProperty("user.dir");
        //htmlReporter = new ExtentHtmlReporter(workingDir + "\\ExtentReports\\ExtentReportResults.html", true);
        htmlReporter = new ExtentHtmlReporter("C:\\Users\\User\\Documents\\Reportes\\Report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Platform", platform);
        extent.setSystemInfo("Browser", browser);
        extent.setSystemInfo("Environment", "Prueba local");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Extent Report Selenium");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);

    }

    @BeforeMethod
    @Parameters(value = {"browser", "platform"})
    public void setupTest(@Optional String browser, @Optional String platform) {
        initializeCustomSetting();
        baseURL = settingsModel.getDefaultTestUrl();
        TLDriverFactory.setTLDriver(browser, platform);
        initializeFluentWait(TLDriverFactory.getTLDriver());
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.ERROR, MarkupHelper.createLabel(result.getName() + "FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
        }else if (result.getStatus()==ITestResult.SUCCESS){
            test.log(Status.PASS,MarkupHelper.createLabel(result.getName()+"PASSED ",ExtentColor.GREEN));
        }else{
            test.log(Status.SKIP,MarkupHelper.createLabel(result.getName()+"SKIPPED ",ExtentColor.BLUE));
            test.skip(result.getThrowable());
        }
    }

    @AfterMethod
    public synchronized void tearDown() {
        log.info("------------------Finish---------------");
        TLDriverFactory.getTLDriver().quit();
    }
    @AfterSuite
    public synchronized void CloseReport() throws InterruptedException {
        log.info("------------------Closed Report---------------");
        Thread.sleep(3000);
        extent.flush();
       // TLDriverFactory.getTLDriver().quit();
    }

    public void waitForPageLoad() {
        ExpectedCondition<Boolean> expection = new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver webDriver) {
                return ((JavascriptExecutor) webDriver).executeAsyncScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(settingsModel.getDefaultSleepTime());
            wait.until(expection);
        } catch (Throwable error) {
            Assert.fail("TimeOut waiting for page load request to complete");
        }
    }

    protected String getURL(String dest) {
        return baseURL + dest;
    }

    private void initializeFluentWait(WebDriver webDriver) {
        Duration timeOut = Duration.ofSeconds(settingsModel.getDefaultTimeout());
        Duration pollingRate = Duration.ofSeconds(settingsModel.getDefaultPollingRate());

        wait = new FluentWait(webDriver).withTimeout(timeOut).pollingEvery(pollingRate).ignoring(NoSuchElementException.class);
    }

    private void initializeCustomSetting() {
        settingsModel = settingsManager.getCustomSettings("customSettings.json");
    }


    /*public WebDriver getDriver() {

        return this.web;
    }*/
}
