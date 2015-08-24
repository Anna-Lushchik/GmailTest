package com.epam.gmail.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPage {

	private static final Logger logger = Logger.getLogger(LoginPage.class);
	private final String BASE_URL = "http://gmail.com";

	@FindBy(id = "Email")
	private WebElement inputLogin;

	@FindBy(id = "next")
	private WebElement buttonNext;

	@FindBy(id = "Passwd")
	private WebElement inputPassword;

	@FindBy(id = "signIn")
	private WebElement buttonSignIn;

	@FindBy(id = "gmail-sign-in")
	private WebElement buttonSignInFromAdvertising;

	@FindBy(xpath = "//p[@class='switch-account']/a")
	private WebElement switchAccount;

	@FindBy(id = "account-chooser-add-account")
	private WebElement addAccount;

	private String advertising1 = "https://www.gmail.com/intl/en/mail/help/about.html";
	private String advertising2 = "https://www.gmail.com/intl/en/mail/help/about.html#inbox";

	String GoogleTitleOnLoginPage = "//div[@class='banner']";

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Login page opened");
	}

	public void login(String username, String password) {
		inputLogin.sendKeys(username);
		buttonNext.click();
		inputPassword.clear();
		inputPassword.sendKeys(password);
		buttonSignIn.click();
		logger.info("Login performed");
	}

	public void changeLogin(String username, String password) {
		if (driver.getCurrentUrl().equals(advertising1)
				|| driver.getCurrentUrl().equals(advertising2)) {
			buttonSignInFromAdvertising.click();
		}
		switchAccount.click();
		addAccount.click();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		logger.info("Change login performed");
	}

	public void changeLoginWhenTwoOrMoreName(String username, String password) {
		if (driver.getCurrentUrl().equals(advertising1)
				|| driver.getCurrentUrl().equals(advertising2)) {
			buttonSignInFromAdvertising.click();
		}
		addAccount.click();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		logger.info("Change login performed");
	}

	public boolean mainLoginPageWasOpened() {
		return isElementPresent(GoogleTitleOnLoginPage);
	}

	public boolean isElementPresent(String locator) {
		return driver.findElements(By.xpath(locator)).size() > 0;
	}

}
