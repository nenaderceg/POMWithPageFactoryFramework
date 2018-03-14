package com.test.automation.uiAutomation.homepage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TC001_VerifyLoginWithInvalidCredentials extends TestBase {
	
	public static final Logger log = Logger.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getName());
	HomePage homepage;
	
	@BeforeTest
	public void setup() {
		// For Mac OS
		//System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/drivers/geckodriver");
		// For Window
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
		init("Firefox");
	}
	
	@Test
	public void verifyLoginWithInvalidCredentials() {
		log.info("=========== Starting TC001_VerifyLoginWithInvalidCredentials ========");
		homepage = new HomePage(driver);
		homepage.loginToApplication("test@gmail.com", "password");
		Assert.assertEquals(homepage.getInvalidLoginText(), "Authentication failed.");
		log.info("=========== Finished TC001_VerifyLoginWithInvalidCredentials ========");
	}
	
	@AfterClass
	public void endTest() {
		if(driver != null) {
			driver.close();
		}
	}
}
