package com.qa.AssessmentAdvanced.AssessmentAdvanced;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportUtil 
{
	private static ExtentReports report;
	private static  ExtentTest test;
	private static int inc;
	public static void startReport()
	{
		if(report == null)
		{
			report = new ExtentReports(System.getProperty("user.dir") + "\\" + Constants.REPORTFILEPATH,true);			
		}
	}
	public static void createTest(String testName)
	{
		if(test == null)
		{
			test = report.startTest(testName);
		}
	}
	public static void logTest(LogStatus status,String text)
	{
		test.log(status,text);
	}
	public static void logTestWithPic(String filepath,LogStatus log,String text,WebDriver driver)
	{
		test.log(log,text,test.addScreenCapture(filepath));
	}
	public static void logTestWithScreenShot(LogStatus status,String text,WebDriver driver)
	{
		test.log(status,text,test.addScreenCapture(takeScreenShot(text,driver) + inc++ + ".png"));
	}
	public static String takeScreenShot(String output,WebDriver drive)
	{
		File scrFile = ((TakesScreenshot)drive).getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(scrFile, new File(Constants.SCREENSHOTFILEPATH + inc + ".png"));
		} catch (IOException e) {
			System.out.println("Failed to take a screen shot");
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return Constants.SCREENSHOTFILEPATH; 
	}
	public static void EndTest()
	{
		report.endTest(test);
		test = null;
		
	}
	public static void EndReport()
	{
		report.flush();
	}

}
