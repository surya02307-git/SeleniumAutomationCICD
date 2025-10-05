package Selenium_Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjects.CartPage;
import PageObjects.CheckoutPage;
import PageObjects.ConfirmationPage;
import PageObjects.OrderPage;
import PageObjects.ProductCatalogue;
import TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {

	// Variables
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void SubmitOrder(String email, String password, String productName)
			throws IOException, InterruptedException {

		// LandingPage landingPage = launchApplication();
		ProductCatalogue catalogue = landingPage.loginApplication(email, password);

		// Add to Cart
		// ProductCatalogue catalogue = new ProductCatalogue(driver);
		List<WebElement> products = catalogue.getProductList();
		catalogue.addProductToCart(productName);
		CartPage cartPage = catalogue.goToCartPage();

		// Cart Page
		// CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitTheOrder();

		String confirmMessage = confirmationPage.verifyConformMessage();
		// Cart Page
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order.")); // True

		System.out.println("Finished");

	}

	@Test(dependsOnMethods = { "SubmitOrder" })
	public void OrderHistoryTest() {

		// ZARA COAT 3
		ProductCatalogue catalogue = landingPage.loginApplication("surya02307@gmail.com", "SuryaRahul@23");
		OrderPage ordersPage = catalogue.goOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

	}

	

	@DataProvider
	public Object[][] getData() throws IOException {

//		HashMap<Object, Object> map = new HashMap<>();
//		map.put("email","surya02307@gmail.com");
//		map.put("password","SuryaRahul@23");
//		map.put("productName","ZARA COAT 3");
//
		return new Object[][] { { "surya02307@gmail.com", "SuryaRahul@23", "ZARA COAT 3" },
				{ "ayrusksm23@gmail.com", "Iamking@000", "ADIDAS ORIGINAL" } };

//		List<HashMap<String, String>> data = getjsonDataToMap(
//				System.getProperty("user.dir") + "//src//test//java//Data//PurchaseOrder.json");

//		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

}
