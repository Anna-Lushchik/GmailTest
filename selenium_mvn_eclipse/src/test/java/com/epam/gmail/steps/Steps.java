package com.epam.gmail.steps;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.epam.gmail.pages.*;
import com.epam.gmail.pages.settings.*;
import com.epam.gmail.utils.Utils;

public class Steps {

	private final Logger logger = Logger.getLogger(Steps.class);
	private static WebDriver driver;

	public Steps() {
	}

	public WebDriver initBrowser() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logger.info("Browser started");
		return driver;
	}

	public void closeDriver() {
		driver.quit();
	}

	public static void deleteCookies() {
		driver.manage().deleteAllCookies();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.navigate().refresh();
	}

	public static void loginGmail(String username, String password) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.openPage();
		loginPage.login(username, password);
	}

	public static void changeLoginNameGmail(String username, String password) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.changeLogin(username, password);
	}

	public static void changeLoginWhenTwoOrMoreNameGmail(String username,
			String password) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.changeLoginWhenTwoOrMoreName(username, password);
	}

	public void markLetterAsSpam(String username1, String password1,
			String username2, String password2, String theme1, String message1,
			String theme2, String message2) {
		MailPage mailPage = new MailPage(driver);
		SendLetterPage sendLetterPage = new SendLetterPage(driver);

		Steps.loginGmail(username1, password1);

		sendLetterPage.clickWriteButton();
		sendLetterPage.writeNewMessage(username2, theme1, message1);
		Steps.deleteCookies();

		Steps.changeLoginNameGmail(username2, password2);

		mailPage.chooseTopItem();
		mailPage.markLastLetterAsSpam();
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username1, password1);

		sendLetterPage.clickWriteButton();
		sendLetterPage.writeNewMessage(username2, theme2, message2);
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username2, password2);

		mailPage.goToSpam();
	}

	public void forwardLetter(String username1, String password1,
			String username2, String password2, String username3,
			String password3, String file, String theme, String message) {

		ForwardPage forwardPage = new ForwardPage(driver);
		FiltersPage filtersPage = new FiltersPage(driver);
		MailPage mailPage = new MailPage(driver);
		SendLetterPage sendLetterPage = new SendLetterPage(driver);

		Steps.loginGmail(username2, password2);
		forwardPage.setForward(username3);
		Steps.deleteCookies();

		Steps.changeLoginNameGmail(username3, password3);
		mailPage.confirmForward();
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username2, password2);
		forwardPage.setForwardCopy(username1);
		filtersPage.setFilters(username1);
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username1, password1);
		sendLetterPage.clickWriteButton();
		sendLetterPage.writeNewMessage(username2, theme, message);
		sendLetterPage.writeNewMessageWithAttach(username2, theme, message,
				file);

		Steps.changeLoginWhenTwoOrMoreNameGmail(username2, password2);
	}

	public void attachBigFile(String username1, String password1, String file,
			String theme, String message) {
		SendLetterPage sendLetterPage = new SendLetterPage(driver);
		Utils utils = new Utils();
		utils.createNewBigFile(file);

		Steps.loginGmail(username1, password1);
		sendLetterPage.writeNewMessageWithAttach(username1, theme, message,
				file);
	}
}
