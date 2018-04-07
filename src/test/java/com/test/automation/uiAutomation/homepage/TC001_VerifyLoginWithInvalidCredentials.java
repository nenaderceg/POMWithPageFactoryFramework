package com.test.automation.uiAutomation.homepage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;

public class TC001_VerifyLoginWithInvalidCredentials extends TestBase {
	
	private static final Logger log = Logger.getLogger(TC001_VerifyLoginWithInvalidCredentials.class.getName());
	HomePage homepage;
	
	@Test(dataProvider="loginData")
	public void verifyLoginWithInvalidCredentials(String email, String pass, String runMode) {
		if(runMode.equalsIgnoreCase("n")) {
			throw new SkipException("User marked this record no run. Skiping test");
		}
		log.info("=========== Starting verifyLoginWithInvalidCredentials ========");
		homepage = new HomePage(driver);
		extentTest.log(LogStatus.INFO, "Attempting login with  email : " + email + ", and password : " + pass);
		homepage.loginToApplication(email, pass);
		//getScreenShot("verifyLoginWithInvalidCredentials_" + email);
		Assert.assertEquals(homepage.getInvalidLoginText(), "Authentication failed.");
		log.info("=========== Finished TC001_VerifyLoginWithInvalidCredentials ========");
	}
		
//	@Test
//	public void verifyFailTest() {
//		log.info("====================== Starting verifyFailTest ======================");
//		homepage = new HomePage(driver);
//		homepage.loginToApplication("jediGovan@yahoo.com", "pusi kurac");
//		//getScreenShot("verifyLoginWithInvalidCredentials_" + email);
//		Assert.assertEquals(homepage.getInvalidLoginText(), "failed.");
//		log.info("====================== Finished verifyFailTest ======================");
//	}
	
	@DataProvider(name= "loginData")
	public Object[][] loginData() {
		return getData("TestData1.xlsx", "LoginTestData");
	}
}
