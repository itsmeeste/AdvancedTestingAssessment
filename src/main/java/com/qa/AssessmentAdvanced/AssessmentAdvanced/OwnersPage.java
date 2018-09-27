package com.qa.AssessmentAdvanced.AssessmentAdvanced;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OwnersPage 
{
	// Do all of the find by
	public static boolean findRecordsOnPage(WebDriver driver)
	{
		int itr = 1;
		WebElement element;
		while(true)
		{
			element = driver.findElement(By.xpath("/html/body/app-root/app-owner-list/div/div/div/table/tbody/tr["+ itr +"]/td[1]/a"));
			if(element == null)
			{
				System.out.println("Web element not found");
				break;
			}
			else
			{
				System.out.println(element.getText());
			}
			
		}
		return true;
		
	}
	
	public static WebElement getWebElementByID(String id,WebDriver driver)
	{
		int itr = 1;
		WebElement element;
		while(true)
		{
			element = driver.findElement(By.xpath("/html/body/app-root/app-owner-list/div/div/div/table/tbody/tr["+ itr +"]/td[1]/a"));
			if(element == null)
			{
				System.out.println("Web element not found");
				break;
			}
			else
			{
				System.out.println(element.getText());
			}
			
		}
		return element;
	}
}

