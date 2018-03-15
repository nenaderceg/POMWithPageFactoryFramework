package com.test.automation.uiAutomation.homepage;

import org.testng.annotations.Test;
import com.test.automation.uiAutomation.testBase.TestBase;
import com.test.automation.uiAutomation.uiActions.HomePage;

import org.testng.annotations.BeforeClass;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class TC002_VerifyRegistration extends TestBase{
	
	private static Logger log = Logger.getLogger(TC002_VerifyRegistration.class.getName());
	HomePage homepage;
	@BeforeClass
	public void setUp() {
		init("firefox");
	}

	@Test
	public void testSignUp() {
		log.info("=========== Starting TC001_VerifyLoginWithInvalidCredentials ========");
		homepage = new HomePage(driver);
		homepage.registerToApplication("test@gmail.com");
		Assert.assertEquals(homepage.getInvalidRegistrationText(), " An account using this email address has already been registered. Please enter a valid password or request a new one.");
		log.info("=========== Finished TC001_VerifyLoginWithInvalidCredentials ========");
		/*
		driver.switchTo().frame(driver.findElement(By.id("PreviewFrame")));
		driver.findElement(By.id("customer_register_link")).click();
		driver.findElement(By.id("FirstName")).clear();
		driver.findElement(By.id("FirstName")).sendKeys("test");
		driver.findElement(By.id("LastName")).clear();
		driver.findElement(By.id("LastName")).sendKeys("testlast");
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("automation@gmail.com");
		driver.findElement(By.id("CreatePassword")).clear();
		driver.findElement(By.id("CreatePassword")).sendKeys("password");
		driver.findElement(By.cssSelector("input.btn")).click();
		*/
		
	}

	@AfterClass
	public void endTest() {
		driver.close();
	}

}
