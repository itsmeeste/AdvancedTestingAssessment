package com.qa.AssessmentAdvanced.AssessmentAdvanced;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Vets 
{
	
	public static WebElement findVet(String id,WebDriver driver)
	{
		int itr = 1;
		WebElement element;
		while(true)
		{
			element = driver.findElement(By.xpath("//*[@id=\"vets\"]/tbody/tr[" + itr+"]/td[1]"));
			if(element == null)
			{
				System.out.println("Web element not found");
				break;
			}
			else
			{
				if(element.getText().equals(id))
				{
					WebElement ele = driver.findElement(By.xpath("//*[@id=\"vets\"]/tbody/tr[" + itr + "]/td[3]/button[1]"));
					return ele;					
				}
			}
			itr++;
			
		}
		return element;
	}
	
	public static boolean clickOnWebElement(WebElement element)
	{
		if(element == null)
			return false;
		element.click();
		
		return true;
	}
	
	public static String checkCareAvailable(WebDriver driver)
	{
		WebElement element = driver.findElement(By.className("ng-star-inserted"));
		if(element == null)
			return "";
		else
			return element.getText();
	}
	public static boolean updateFirstName(WebDriver driver,String input)
	{
		WebElement text = driver.findElement(By.xpath("//*[@id=\"firstName\"]"));
		if(text == null)
			return false;
		text.clear();
		text.sendKeys(input);
		
		WebElement submit = driver.findElement(By.xpath("//*[@id=\"vet_form\"]/div[5]/div/button[2]"));
		
		if(submit == null)
			return false;
		submit.click();
		return true;
		
	}
	

}
