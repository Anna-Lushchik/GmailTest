package com.epam.gmail.steps;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.epam.gmail.pages.*;
import com.epam.gmail.pages.settings.*;
import com.epam.gmail.utils.Utils;

public class Steps {

	private final Logger logger = Logger.getLogger(Steps.class);
	private static WebDriver driver;

	String lastMessageSubject = "(//div[@class='yW'])[1]";
	String lastMessageText = "//div[@class='y6']";

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
			String username2, String password2) {
		MailPage mailPage = new MailPage(driver);
		SendLetterPage sendLetterPage = new SendLetterPage(driver);

		Steps.loginGmail(username1, password1);

		sendLetterPage.clickWriteButton();
		sendLetterPage.writeNewMessage(username2, "theme1", "message1");
		Steps.deleteCookies();

		Steps.changeLoginNameGmail(username2, password2);

		mailPage.chooseTopItem();
		mailPage.markLastLetterAsSpam();
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username1, password1);

		sendLetterPage.clickWriteButton();
		sendLetterPage.writeNewMessage(username2, "theme2", "message2");
		Steps.deleteCookies();

		Steps.changeLoginWhenTwoOrMoreNameGmail(username2, password2);

		mailPage.clickSpam();
	}

	public void forwardLetter(String username1, String password1,
			String username2, String password2, String username3,
			String password3) throws AWTException {

		ForwardPage forwardPage = new ForwardPage(driver);
		FiltersPage filtersPage = new FiltersPage(null);
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
		sendLetterPage.writeNewMessage(username2, "theme", "message");
		sendLetterPage.writeNewMessageWithAttach(username2, "theme", "message");

		Steps.changeLoginWhenTwoOrMoreNameGmail(username2, password2);
	}

	public void attachBigFile(String username1, String password1)
			throws AWTException {
		SendLetterPage sendLetterPage = new SendLetterPage(driver);
		Utils utils = new Utils();
		utils.createNewBigFile("t.txt");

		Steps.loginGmail(username1, password1);
		sendLetterPage.writeNewMessageWithAttach(username1, "theme", "message");
	}

}
