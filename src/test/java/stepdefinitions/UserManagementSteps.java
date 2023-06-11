package stepdefinitions;


import java.util.Map;
import org.junit.Assert;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserManagementSteps {

	private String baseUrl = "https://reqres.in";
	private Response userResponse;
	private Response createdUserResponse;

	@Before
	public void setup() {
		RestAssured.baseURI = baseUrl;
	}

	@Given("the API is available")
	public void apiIsAvailable() {
		// No additional action required as it's already set in the setup method
	}

	@When("I send a GET request to {string}")
	public void sendGetRequest(String endpoint) {
		RequestSpecification request = RestAssured.given();
		userResponse = request.get(endpoint);
	}

	@Then("the GET call response status code should be {int}")
	public void verifyStatusCode(int expectedStatusCode) {
		Assert.assertEquals(expectedStatusCode, userResponse.getStatusCode());
	}

	@Then("the GET call response body should contain the following details:")
	public void verifyResponseBody(DataTable dataTable) {
		Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : expectedDetails.entrySet()) {
			String field = entry.getKey();
			String expectedValue = entry.getValue();
			String actualValue = userResponse.path(field);

			Assert.assertEquals(expectedValue, actualValue);
		}
	}

	@When("I send a POST request to {string} and the request body contains the following details:")
	public void sendPostRequest(String endpoint, DataTable dataTable) {
		Map<String, String> userDetails = dataTable.asMap(String.class, String.class);

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(userDetails);

		createdUserResponse = request.post(endpoint);
	}
	
	@Then("the POST call response status code should be {int}")
	public void verifyPostStatusCode(int expectedStatusCode) {
		Assert.assertEquals(expectedStatusCode, createdUserResponse.getStatusCode());
	}


	@Then("the POST call response body should contain the following details:")
	public void verifyPostResponseBody(DataTable dataTable) {
		Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : expectedDetails.entrySet()) {
			String field = entry.getKey();
			String expectedValue = entry.getValue();
			String actualValue = createdUserResponse.path(field);

			Assert.assertEquals(expectedValue, actualValue);
		}
	}
}
