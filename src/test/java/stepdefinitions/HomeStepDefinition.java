package stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import Utilities.BrowserFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomeStepDefinition {

	private WebDriver driver;
	List<Map<String, String>> endpointTexAndUrlsfromCSV;

	@Given("I open the application URL")
	public void i_open_the_application_url() {

		driver = BrowserFactory.getDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://reqres.in/");
	}

	@When("I check the home page")
	public void i_check_the_home_page() {

		String title = driver.getTitle();
        System.out.println("Page Title: " + title);

	}

	@Then("I verify the request types and endpoints")
	public void i_verify_the_request_types_and_endpoints() {

		Map<String, String> endpointTexAndUrl = new LinkedHashMap<>();
		List<Map<String, String>> endpointTexAndUrls = new ArrayList<>();
		WebElement endpointsElement = driver.findElement(By.xpath("//div[@class='endpoints']"));
		assertEquals("Request types and endpoints are not displayed correctly.", true, endpointsElement.isDisplayed());

		WebElement ulElement = endpointsElement.findElement(By.tagName("ul"));
		List<WebElement> liElements = ulElement.findElements(By.tagName("li"));

		for (WebElement liElement : liElements) {
			String endpointText = liElement.findElement(By.tagName("a")).getText();
			String endpointUrl = liElement.findElement(By.tagName("a")).getAttribute("href");

			endpointTexAndUrl.put("RequestType", endpointText);
			endpointTexAndUrl.put("Endpoint", endpointUrl);

		}
		endpointTexAndUrls.add(endpointTexAndUrl);

		for (Map<String, String> user : endpointTexAndUrls) {
			for (Map.Entry<String, String> entry : user.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				System.out.println("Print all values from Web page ::: " + " Key: " + key + ", Value: " + value);
			}
		}
	}

	@Then("I select a specific option")
	public void i_select_a_specific_option() throws InterruptedException {

		driver.findElement(By.xpath("//div[@class='endpoints']/ul/li/a[text()= ' Single user not found ']")).click();
		Thread.sleep(3000);

	}

	@Then("I verify the request {string} , response code {string} and response body {string} details")
	public void i_verify_the_request_and_response_details(String request, String expectedResponseCode,
			String expctedresponseBody) {
		String endpointText = driver
				.findElement(By.xpath(
						"//div[@class='output']//div[@class='request']//a[@class='link try-link']/span[@class='url']"))
				.getText();
		String responseCode = driver.findElement(By.xpath("//div[@class='output']//div[@class='response']//span"))
				.getText();

		assertEquals(request, endpointText);
		assertEquals(expectedResponseCode, responseCode);
	}

	public List<Map<String, String>> getUserDetailsFromFile(String filePath) {
		// Read the CSV file and extract user details
		List<Map<String, String>> userDetails = new ArrayList<>();

		try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
			String[] headers = reader.readNext(); // Get column headers
			String[] row;

			while ((row = reader.readNext()) != null) {
				Map<String, String> user = new LinkedHashMap<>();

				for (int i = 0; i < headers.length; i++) {
					user.put(headers[i], row[i]);
				}

				userDetails.add(user);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userDetails;
	}

}
