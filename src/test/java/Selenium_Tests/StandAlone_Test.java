package Selenium_Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import PageObjects.LandingPage;

public class StandAlone_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// new comments are added 
		// Variables
		String productName = "ZARA COAT 3";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		// Implicitly wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get("https://rahulshettyacademy.com/client");

		// Explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// Login
		LandingPage lp = new LandingPage(driver);
		
		driver.findElement(By.id("userEmail")).sendKeys("surya02307@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("SuryaRahul@23");
		driver.findElement(By.id("login")).click();

		// Add to Cart
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		// Using Java Streams to Filter - Better coding
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);

		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// ng-animating
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();

		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

		// Java Streams - Any match method
		Boolean match = cartProducts.stream().anyMatch(cartProd -> cartProd.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//Cart Page
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.xpath("//div[@class='user__name mt-5']/div[1]/div")), "india").build().perform();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results ")));
		
		driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order.")); //True
		
		System.out.println("Finished");
		
		driver.close();
		

	}

}
