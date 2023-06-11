package hooks;

import org.openqa.selenium.WebDriver;

import Utilities.BrowserFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
	
	WebDriver driver;
	
	@Before
	public void setup() {
		// Pass the browser name as an argument to initializeBrowser method
		BrowserFactory.initializeBrowser("chrome");
		driver = BrowserFactory.getDriver();
	}
	
	@After
	public void teardown() {
		driver.close();
	}

}
