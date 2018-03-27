package com.test.automation.uiAutomation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.uiAutomation.excelReader.ExcelReader;

public class TestBase {

	private static final Logger log = Logger.getLogger(TestBase.class.getName());

	private static final String projectPath = System.getProperty("user.dir");
	private static final String driverPath = projectPath + "/drivers/";

	protected WebDriver driver;
	private static ExtentReports extentReport;
	protected static ExtentTest extentTest;
	private Properties objectRepo;
	private String url;

	public void init(String browserType) throws IOException {		
		loadObjectRepo();
		driver = setBrowser(browserType);
		
		if(browserType.toLowerCase().equals("chrome"));
		chromeBringToFront();
		
		url = objectRepo.getProperty("url");
		navigateTo(url);
		PropertyConfigurator.configureAndWatch("log4j.properties");
		extentReport = new ExtentReports(projectPath + "/src/main/java/com/test/automation/uiAutomation/report/test_" + getTimeStamp() + ".html", false);
	}

	public static WebDriver setBrowser(String browserType) {
		switch (browserType.toLowerCase()) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");			
			log.info("Creating object of " + browserType);
			return new ChromeDriver();

		case "firefox":
			// System.setProperty("webdriver.firefox.marionette", driverPath + "geckodriver");
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver");
			log.info("Creating object of " + browserType);
			return new FirefoxDriver();

	// case "iea":
			// System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer");
			// log("Creating object of " + browserType);
			// return new InternetExplorerDriver();

		default:
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
			log.info("Browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
			return new ChromeDriver();
		}
	}

	private void navigateTo(String url) {
		log.info("Navigating to " + url);
		driver.navigate().to(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public Object[][] getData(String excelName, String sheetName) {
		String path = projectPath + "/src/main/java/com/test/automation/uiAutomation/data/" + excelName;
		Object[][] data = new ExcelReader(path).getDataFromSheet(sheetName);
		return data;
	}

	public void waitForElement(int timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(), timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public String getScreenShot(String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String reportDirectory = projectPath + "/src/main/java/com/test/automation/uiAutomation";
			File destFile = new File(reportDirectory + fileName + getTimeStamp() + ".png");
			FileUtils.copyFile(scrFile, destFile);
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/>");
			return 	destFile.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
	
	public ExtentTest getExtentTest() {
		return extentTest;
	}

	public void log(String data) {
		log.info(data);
		Reporter.log(data);
		extentTest.log(LogStatus.INFO, data);
	}

	@BeforeMethod()
	public void beforeMethod(Method result) {
		extentTest = extentReport.startTest(result.getName());
		extentTest.log(LogStatus.INFO, result.getName() + " test Started");
	}

	@AfterMethod()
	public void afterMethod(ITestResult result) {
		//getresult(result);
	}

	@Parameters("browser")
	@BeforeTest
	public void setup(String browser) throws IOException {	
		init(browser);
	}

	@AfterClass
	public void tearDown() {
		log("======== Closing browser ======");
		driver.quit();
		log.info("browser closed");
		extentReport.endTest(extentTest);
		extentReport.flush();
	}

	private void chromeBringToFront() {
		String currentWindowHandle = this.driver.getWindowHandle();
		((JavascriptExecutor)this.driver).executeScript("alert('Test')"); 
		this.driver.switchTo().alert().accept();
		this.driver.switchTo().window(currentWindowHandle);	
	}

	public String getTimeStamp() {
		return new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(Calendar.getInstance().getTime());
	}

	public void loadObjectRepo() throws IOException {		
		objectRepo = new Properties();
		File file = new File(projectPath + "/src/main/java/com/test/automation/uiAutomation/config/config.properties");
		FileInputStream f = new FileInputStream(file);
		objectRepo.load(f);
	}

/*	private void getresult(ITestResult result) {		
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, test.addScreenCapture(getScreenShot("blank")));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:-" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.FAILURE) {
			 test.log(LogStatus.ERROR, result.getName() + " test is failed" + result.getThrowable());
			String screen = getScreenShot("blank");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + " test is started");
		}
	}*/
}
