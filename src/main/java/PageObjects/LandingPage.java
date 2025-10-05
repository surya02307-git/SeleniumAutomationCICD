package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.AbstractComponents;

public class LandingPage extends AbstractComponents{

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		// Initialization driver
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this); // initialize the driver for Locators, drivers
	}

	// WebElement userEmail = driver.findElement(By.id("userEmail"));

	// Page Factory
	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;

	// Login Action method

	public void goTo() {

		driver.get("https://rahulshettyacademy.com/client");
	}

	public ProductCatalogue loginApplication(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submit.click();
		ProductCatalogue catalogue = new ProductCatalogue(driver);
		return catalogue;

	}
	
	public String getErrroMsg() {
		
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
		
	}

}
