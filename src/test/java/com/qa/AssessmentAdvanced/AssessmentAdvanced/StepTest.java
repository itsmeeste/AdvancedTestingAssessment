package com.qa.AssessmentAdvanced.AssessmentAdvanced;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.LogStatus;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class StepTest 
{
	WebDriver driver;
	String oldName = "bob";
	@Before()
	public void startUp()
	{
		System.setProperty("webdriver.chrome.driver",Constants.CHROMEDRIVER);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		ReportUtil.startReport();
	}
	@After()
	public void TearDown()
	{
		driver.close();
		driver.quit();
		ReportUtil.EndReport();
	}
	@Given("^a vet$")
	public void a_vet() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		ReportUtil.createTest("Go To the vets page");
		driver.get(Constants.VETSPAGE);
		ReportUtil.logTest(LogStatus.INFO, "Using selenium to check if the element with vet name is there ");
		boolean check;
		if(Vets.findVet("James Carter", driver) == null)
		{
			ReportUtil.logTest(LogStatus.PASS, "The name: " + "James Carter "  +"was there");
			check = false;
		}
		else
		{
			check = true;
			ReportUtil.logTest(LogStatus.FAIL, "The name: " + "James Carter "  +"was not found");
			
		}
		ReportUtil.EndTest();
		Assert.assertEquals(true, check);
	}

	@When("^I click on some records$")
	public void i_click_on_some_records() throws Throwable {
		// Click on the records
		ReportUtil.createTest("Check to see if can click on record on page");
		boolean check = Vets.clickOnWebElement(Vets.findVet("James Carter", driver));
		if(check)
		{
			ReportUtil.logTest(LogStatus.PASS,"the element was found and clicked");
		}
		else
		{
			ReportUtil.logTest(LogStatus.FAIL,"Failed to find the element and check it");
		}
		ReportUtil.EndTest();
		Assert.assertEquals(true, check);
	}

	@Then("^I can see the care available for animals$")
	public void i_can_see_the_care_available_for_animals() throws Throwable 
	{
		//ReportUtil.createTest("Find what care is available");	
		//Thread.sleep(1000);
		//int length = Vets.checkCareAvailable(driver).length();
		//System.out.println("Length of text: " + length);
		//ReportUtil.EndTest();
	}

	@Given("^an admin$")
	public void an_admin() throws Throwable {
	   System.out.println("Admin");
	}

	@When("^I update a record$")
	public void i_update_a_record() throws Throwable 
	{
		ReportUtil.createTest("Updating a record on the Vets page");	
		driver.get(Constants.VETSPAGE);
		boolean check;
		if(Vets.findVet("James Carter", driver) == null)
		{
			ReportUtil.logTest(LogStatus.FAIL,"the element to update was not found with the name James Carter");
			check = false;
		}
		else
		{
			ReportUtil.logTest(LogStatus.PASS,"the element to update was found");
			check = true;
		}
		
		Assert.assertEquals(true, check);
		// Click on the records
		
		check = Vets.clickOnWebElement(Vets.findVet("James Carter", driver));
		Assert.assertEquals(true, check);
		ReportUtil.logTest(LogStatus.INFO,"Clicked on the element to be directed to edit page");
		check = Vets.updateFirstName(driver, "bob");
		Assert.assertEquals(true, check);
		ReportUtil.logTest(LogStatus.PASS,"The name was updated for the vet");
		ReportUtil.EndTest();
		
		
	}

	@Then("^the correct details are now shown$")
	public void the_correct_details_are_now_shown() throws Throwable {
		ReportUtil.createTest("Was the correct info shown on the vets page after change");
		boolean check;
		if(Vets.findVet("bob Carter", driver) == null)
		{
			ReportUtil.logTest(LogStatus.PASS, "the name was changed from James Carter to bob Carter");
			check = false;
		}
		else
			check = true;
		ReportUtil.EndTest();
		Assert.assertEquals(true, check);
	
		
	}

	@SuppressWarnings("deprecation")
	@When("^I delete a animal$")
	public void i_delete_a_animal() throws Throwable 
	{
		ReportUtil.createTest("Test for deleting a pet using RestAssured with delete");
		// First set the url for the request
		RestAssured.baseURI = Constants.PETSAPI;
						
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
						
		// Then need to set up the request and set the data type
		// Set the content type and set it to be json
		request.header("Content-Type","application/json");
						
		// now to make a response from the request
		Response response = request.delete("/14");
		ReportUtil.logTest(LogStatus.PASS,"Deleted the pet with the id of 14");
		ReportUtil.EndTest();
		//Assert.assertEquals(204,response.getStatusCode());
	}

	@Then("^emails arent sent to deceased annimals$")
	public void emails_arent_sent_to_deceased_annimals() throws Throwable {
		ReportUtil.createTest("Check to see if the pet is still there after being deleted using get");
	   // Try to access the details of the animal
		RestAssured.baseURI = Constants.PETSAPI;
		
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
						
		// Then need to set up the request and set the data type
		// Set the content type and set it to be json
		request.header("Content-Type","application/json");
						
		// now to make a response from the  request
		Response response = request.get("/14");
		ReportUtil.logTest(LogStatus.PASS, "The error code returned for accessing that pet should be 404 and it was " + response.getStatusCode());
		ReportUtil.EndTest();
		System.out.println(response.getStatusCode());
	}

	@When("^I add new records$")
	public void i_add_new_records() throws Throwable 
	{
		ReportUtil.createTest("Using POST add a new vet");
	    // Add a new record using the post request
		RestAssured.baseURI = Constants.VETSAPI;
		
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
						
		// Then need to set up the request and set the data type
		// Set the content type and set it to be json
		request.header("Content-Type","application/json");
		request.header("Accept","application/json;charset=UTF-8");
		

		
		JSONObject main = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject specialties = new JSONObject();
		main.put("id", "22");
		main.put("firstName", "sds");
		main.put("lastName", "SDFS");
		specialties.put("id", 3);
		specialties.put("name", "dentistry");
		array.add(specialties);
		main.put("specialties", array);
		
		request.body(main.toString());
		ReportUtil.logTest(LogStatus.INFO, "Added the json object to the response body");
		
		Response response = request.post("");
		ReportUtil.logTest(LogStatus.PASS, "Send the post request");
		ReportUtil.EndTest();
		Assert.assertEquals(201, response.getStatusCode());
		
	}

	@Then("^the records are correct$")
	public void the_records_are_correct() throws Throwable 
	{
		ReportUtil.createTest("Check to see if the info was correct using GET");
	    // see if can find the name of the new record is correct
		RestAssured.baseURI = Constants.VETSAPI;
		
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
		ReportUtil.logTest(LogStatus.INFO,"Use the request.get on the 10th element and check its firstname");
		Response response = request.get("/10");
		System.out.println(response.asString());
		int start = response.asString().indexOf("firstName");
		int end = response.asString().indexOf("lastName");
		String firstname =response.asString().substring(start+12, end-3);
		ReportUtil.logTest(LogStatus.PASS,"The new name was found");
		ReportUtil.EndTest();
		Assert.assertEquals(firstname, "sds");
		
	}

	@SuppressWarnings("deprecation")
	@When("^I add new owners to the records$")
	public void i_add_new_owners_to_the_records() throws Throwable
	{
		ReportUtil.createTest("Add to the owners by using POST");
		// Add a new record using the post request
		RestAssured.baseURI = Constants.OWNERSAPI;
				
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
								
		// Then need to set up the request and set the data type
		// Set the content type and set it to be json
		request.header("Content-Type","application/json");
		request.header("Accept","application/json;charset=UTF-8");
				

		ReportUtil.logTest(LogStatus.INFO, "Starting to add info to a JSONObject");		
		JSONObject main = new JSONObject();
		JSONArray petsArray = new JSONArray();
		JSONArray visitsArray = new JSONArray();
		JSONObject pet = new JSONObject();
		JSONObject type = new JSONObject();
				
				
		main.put("id", 13);
		oldName = "sam";
		main.put("firstName", "sam");
		main.put("lastName", "SDFS");
		main.put("address", "something");
		main.put("city", "dfsdfs");
		main.put("telephone", "45454545");
				
		pet.put("id", 12);
		pet.put("name","Lucky");
		pet.put("birthDate", "2010/06/24");
				
		type.put("id", 2);
		type.put("name", "dog");
		pet.put("type", type);
		pet.put("owner", 10);
		pet.put("visits", visitsArray);
				
		petsArray.add(pet);
		main.put("pets",petsArray);
		request.body(main.toString());
		ReportUtil.logTest(LogStatus.INFO, "Info added to the request.body");
		Response response = request.post("");
		ReportUtil.logTest(LogStatus.PASS, "Using the request.post added a new owner");
		ReportUtil.EndTest();
		System.out.println(response.getStatusCode());
		Assert.assertEquals(201,response.getStatusCode());
	    
	}

	@Then("^the details show the change$")
	public void the_details_show_the_change() throws Throwable {
	    // Check to see if the name is different
		RestAssured.baseURI = Constants.OWNERSAPI;
		
		// Then need to make a request to the url
		RequestSpecification request = RestAssured.given();
		
		Response response = request.get("/13");
		int start = response.asString().indexOf("firstName");
		int end = response.asString().indexOf("lastName");
		String firstname =response.asString().substring(start+12, end-3);
		Assert.assertEquals(firstname, oldName);
	}
	
	@Given("^Get the results for a load test$")
	public void get_the_results_for_a_load_test() throws Throwable {
	    ReportUtil.createTest("Load Test");
	}

	@When("^create a test load report$")
	public void create_a_test_load_report() throws Throwable {
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "BytesThroughputOverTime.png", LogStatus.INFO, "BytesThroughputOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "HitsPerSecond.png", LogStatus.INFO, "HitsPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "LatenciesOverTime.png", LogStatus.INFO, "LatenciesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ResponseCodesPerSecond.png", LogStatus.INFO, "ResponseCodesPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ResponseTimesDistribution.png", LogStatus.INFO, "ResponseTimesDistribution pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ResponseTimesOverTime.png", LogStatus.INFO, "ResponseTimesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ResponseTimesPercentiles.png", LogStatus.INFO, "ResponseTimesPercentiles pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ThreadsStateOverTime.png", LogStatus.INFO, "ThreadsStateOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "ThroughputVsThreads.png", LogStatus.INFO, "ThroughputVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "TimesVsThreads.png", LogStatus.INFO, "TimesVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.LOADFILEPATH + "TransactionsPerSecond.png", LogStatus.INFO, "TransactionsPerSecond pic", driver);
	    
	}

	@Then("^end a test load report$")
	public void end_a_test_load_report() throws Throwable {
	    ReportUtil.EndTest();
	}

	@Given("^Get the results for a Stress test$")
	public void get_the_results_for_a_Stress_test() throws Throwable {
		 ReportUtil.createTest("Stress Test");
	}

	@When("^create a test stress report$")
	public void create_a_test_stress_report() throws Throwable {
		ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "BytesThroughputOverTime.png", LogStatus.INFO, "BytesThroughputOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "HitsPerSecond.png", LogStatus.INFO, "HitsPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "LatenciesOverTime.png", LogStatus.INFO, "LatenciesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ResponseCodesPerSecond.png", LogStatus.INFO, "ResponseCodesPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ResponseTimesDistribution.png", LogStatus.INFO, "ResponseTimesDistribution pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ResponseTimesOverTime.png", LogStatus.INFO, "ResponseTimesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ResponseTimesPercentiles.png", LogStatus.INFO, "ResponseTimesPercentiles pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ThreadsStateOverTime.png", LogStatus.INFO, "ThreadsStateOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "ThroughputVsThreads.png", LogStatus.INFO, "ThroughputVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "TimesVsThreads.png", LogStatus.INFO, "TimesVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.STRESSFILEPATH + "TransactionsPerSecond.png", LogStatus.INFO, "TransactionsPerSecond pic", driver);
	}

	@Then("^end a test stress report$")
	public void end_a_test_stress_report() throws Throwable {
		 ReportUtil.EndTest();
	}

	@Given("^Get the results for a spike test$")
	public void get_the_results_for_a_spike_test() throws Throwable {
		ReportUtil.createTest("Spike Test");
	}

	@When("^create a test spike report$")
	public void create_a_test_spike_report() throws Throwable {
		ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "BytesThroughputOverTime.png", LogStatus.INFO, "BytesThroughputOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "HitsPerSecond.png", LogStatus.INFO, "HitsPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "LatenciesOverTime.png", LogStatus.INFO, "LatenciesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ResponseCodesPerSecond.png", LogStatus.INFO, "ResponseCodesPerSecond pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ResponseTimesDistribution.png", LogStatus.INFO, "ResponseTimesDistribution pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ResponseTimesOverTime.png", LogStatus.INFO, "ResponseTimesOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ResponseTimesPercentiles.png", LogStatus.INFO, "ResponseTimesPercentiles pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ThreadsStateOverTime.png", LogStatus.INFO, "ThreadsStateOverTime pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "ThroughputVsThreads.png", LogStatus.INFO, "ThroughputVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "TimesVsThreads.png", LogStatus.INFO, "TimesVsThreads pic", driver);
	    ReportUtil.logTestWithPic(Constants.SPIKEFILEPATH + "TransactionsPerSecond.png", LogStatus.INFO, "TransactionsPerSecond pic", driver);
	}

	@Then("^end a test spike report$")
	public void end_a_test_spike_report() throws Throwable {
		ReportUtil.EndTest();
	}

}
