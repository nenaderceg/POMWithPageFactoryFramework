package com.test.automation.uiAutomation.testBase;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {
	
	private static final Logger log = Logger.getLogger(TestBase.class.getName());

	private static String driverPath = System.getProperty("user.dir");
	private String url = "file:///Users/nenaderceg/Downloads/PageObjectWithPageFactoryFramework-master/project/demoSite.htm";
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
	
	public void getUrl(String url) {
		log.info("Navigating to " + url);
		driver.get(url);
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
}
