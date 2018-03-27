package com.test.automation.uiAutomation.customListener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.test.automation.uiAutomation.testBase.TestBase;

public class Listener implements ITestListener{

	TestBase testBase;

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onStart(ITestContext arg0) {	
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//((TestBase)result.getInstance()).getScreenShot("/failure_screenshots/");	
		ExtentTest test = testBase.getExtentTest();
		test.log(LogStatus.ERROR, result.getName() + " test is failed:- " + result.getThrowable());
		test.log(LogStatus.ERROR, test.addScreenCapture(testBase.getScreenShot("blank")));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = testBase.getExtentTest();
		test.log(LogStatus.SKIP, result.getName() + " test is skipped and skip reason is:- " + result.getThrowable());		
	}

	@Override
	public void onTestStart(ITestResult result) {
		testBase = (TestBase)result.getInstance();		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		//testBase.getScreenShot("/success_screenshots/");	
		ExtentTest test = testBase.getExtentTest();
		test.log(LogStatus.PASS, test.addScreenCapture(testBase.getScreenShot("blank")));
		test.log(LogStatus.PASS, result.getName() + " test has passed");
	}
}
