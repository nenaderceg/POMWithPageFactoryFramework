package com.test.automation.uiAutomation.testBase;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.automation.uiAutomation.excelReader.ExcelReader;

public class TestBase {
	
	private static final Logger log = Logger.getLogger(TestBase.class.getName());

	private static String driverPath = System.getProperty("user.dir");
	//private String url = "file:////Users/nenaderceg/Desktop/Code/Tutorials/PageObjectWithPageFactoryFramework-master/project/demoSite.htm";
	private String url = "http://automationpractice.com/index.php";
	public WebDriver driver;
	
	public void init(String browserType) {
		selectBrowser(browserType);
		getUrl(url);
		String log4jConfigPath = "log4j.properties";
		//BasicConfigurator.configure();
		PropertyConfigurator.configureAndWatch(log4jConfigPath);
	}
	
	public void selectBrowser(String browserType) {
	
		switch (browserType.toLowerCase()) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "/drivers/chromedriver");			
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", driverPath + "/drivers/geckodriver");
			log.info("Creating object of " + browserType);
			driver = new FirefoxDriver();
			break;

		//case "IEA":
		//	System.setProperty("webdriver.ie.driver", driverPath + "/drivers/IEDriverServer");
		//	driver = new InternetExplorerDriver();

		default:
			System.setProperty("webdriver.chrome.driver", driverPath + "/drivers/chromedriver");
			//System.out.println("browser : " + browserType + " is invalid, Launching Chrome as browser of choice..");
			driver = new ChromeDriver();
			break;
		}
	}
	
	private void getUrl(String url) {
		log.info("Navigating to " + url);
		driver.get(url);
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public Object[][] getData(String excelName, String sheetName) {
		
		String path = System.getProperty("user.dir") + "/src/main/java/com/test/automation/uiAutomation/data/" + excelName;
		Object[][] data = new ExcelReader(path).getDataFromSheet(sheetName);
		return data;
	}
	
	public void waitForElement(int timeOutInSeconds, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
}
