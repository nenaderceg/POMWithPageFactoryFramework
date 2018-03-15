package com.test.automation.uiAutomation.uiActions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage {

	WebDriver driver;
	public static final Logger log = Logger.getLogger(HomePage.class.getName());
	
	@FindBy(xpath=".//*[@id='header']/div[2]/div/div/nav/div[1]/a")	
	WebElement signIn;
	
	@FindBy(xpath="//*[@id='email']")
	WebElement loginEmailAddress;
	
	@FindBy(xpath=".//*[@id='passwd']")
	WebElement loginPassword; 
	
	@FindBy(xpath=".//*[@id='SubmitLogin']")
	WebElement submitButton;

	@FindBy(xpath=".//*[@id='center_column']/div[1]/ol/li")
	WebElement authenticationFailed;
	
	@FindBy(id="email_create")
	WebElement registerEmailAddress;
	
	@FindBy(id="SubmitCreate")
	WebElement createAnAccount;
	
	@FindBy(xpath=".//*[@id='create_account_error']/ol/li")
	WebElement registrationFailed;
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void registerToApplication(String email) {
		signIn.click();
		log.info("Clicked on sign in and object is:-" + signIn.toString());
		registerEmailAddress.clear();
		registerEmailAddress.sendKeys(email);
		log.info("Entered the email address:-" + email + " and object is:-" + loginEmailAddress.toString());
		createAnAccount.click();
		log.info("Clicked on create an account button and the object is:-" + createAnAccount.toString());
		
	}
	
	public void loginToApplication(String email, String pass) {
		signIn.click();
		log.info("Clicked on sign in and object is:-" + signIn.toString());
		loginEmailAddress.sendKeys(email);
		log.info("Entered the email address:-" + email + " and object is:-" + loginEmailAddress.toString());
		loginPassword.sendKeys(pass);
		log.info("Entered a password:-" + pass + " and object is:-" + loginPassword.toString());
		submitButton.click();
		log.info("Clicked on submit button and the object is:-" + submitButton.toString());
	}
	
	public String getInvalidLoginText() {
		log.info("Error message is:-" + authenticationFailed.getText());
		return authenticationFailed.getText();
	}
	
	public String getInvalidRegistrationText() {
		log.info("Error message is:-" + registrationFailed.getText());
		return registrationFailed.getText();
	}
}
