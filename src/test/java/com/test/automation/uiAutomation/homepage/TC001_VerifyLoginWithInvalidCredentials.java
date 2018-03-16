package com.test.automation.uiAutomation.homepage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;

public class TC001_VerifyLoginWithInvalidCredentials extends TestBase {
	
	private static final Logger log = Logger.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getName());
	HomePage homepage;
	
	@BeforeTest
	public void setup() {
		// For Mac OS
		//System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/drivers/geckodriver");
		// For Window
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
		init("Firefox");
	}
	
	@Test(dataProvider="loginData")
	public void verifyLoginWithInvalidCredentials(String email, String pass, String runMode) {
		if(runMode.equalsIgnoreCase("n")) {
			throw new SkipException("User marked this record no run. Skiping test");
		}
		log.info("=========== Starting TC001_VerifyLoginWithInvalidCredentials ========");
		homepage = new HomePage(driver);
		homepage.loginToApplication(email, pass);
		Assert.assertEquals(homepage.getInvalidLoginText(), "Authentication failed.");
		log.info("=========== Finished TC001_VerifyLoginWithInvalidCredentials ========");
	}
	
	@AfterTest
	public void quitBrowsers() {
		
		if(driver != null) {
			driver.close();
			log.info("Closing browser ======");
		}
	}
	
	@DataProvider(name= "loginData")
	public Object[][] loginData() {
		return getData("TestData1.xlsx", "LoginTestData");
	}
}
