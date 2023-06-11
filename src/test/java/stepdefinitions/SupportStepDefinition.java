package stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import Utilities.BrowserFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SupportStepDefinition {
	
	private WebDriver driver;
	List<Map<String, String>> endpointTexAndUrlsfromCSV;
	
	@When("I click on the support button")
	public void i_click_on_the_support_button() {
		
		driver = BrowserFactory.getDriver();
		driver.findElement(By.xpath("//button/a[text()='Support ReqRes']")).click();
	}

	@Then("I verify the support page for {string} and {string}")
	public void i_verify_the_support_page_for_and(String labelText, String monthlySupport) {
		String getLabelText = driver.findElement(By.xpath("//div/label[text()='One-time payment ($)']")).getText();
		String monthlySupportText = driver.findElement(By.xpath("//label[@for='supportRecurring']")).getText();

		System.out.println("One-time payment Text Validateion: " + getLabelText);
		System.out.println("Monthly support Text Validateion: " + monthlySupportText);

		assertEquals(labelText, getLabelText);
		assertEquals(monthlySupport, monthlySupportText);

	}

	@When("I click on upgrade button and provide details {string}")
	public void i_click_on_upgrade_button_and_provide_details(String emailId) throws InterruptedException {

		driver.findElement(By.xpath("//button[text()='Upgrade']")).click();
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(emailId);

		Thread.sleep(5000);
	}

}
