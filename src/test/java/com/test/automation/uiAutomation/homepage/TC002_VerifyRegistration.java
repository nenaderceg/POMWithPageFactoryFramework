package com.test.automation.uiAutomation.homepage;

import org.testng.annotations.Test;
import com.test.automation.uiAutomation.testBase.TestBase;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;

public class TC002_VerifyRegistration extends TestBase{
	@BeforeClass
	public void setUp() {
		init("firefox");
	}

	@Test
	public void testLogin() {
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
	}

	@AfterClass
	public void endTest() {
		driver.close();
	}

}
