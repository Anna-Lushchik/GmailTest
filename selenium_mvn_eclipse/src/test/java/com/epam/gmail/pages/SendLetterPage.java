package com.epam.gmail.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.gmail.business.Email;
import com.epam.gmail.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SendLetterPage extends AbstractPage {

	private static final Logger logger = Logger.getLogger(SendLetterPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/#inbox";

	@FindBy(xpath = "//div[@class='z0']/div")
	private WebElement buttonWrite;

	@FindBy(xpath = "//div[@class='wO nr l1']/textarea")
	private WebElement fieldWhom;

	@FindBy(xpath = "//input[@class='aoT']")
	private WebElement fieldTheme;

	@FindBy(xpath = "//div[@class='Am Al editable LW-avf']")
	private WebElement fieldMessage;

	@FindBy(xpath = "//div[@class='a1 aaA aMZ']")
	private WebElement attachment;

	@FindBy(xpath = "//div[@class='QT aaA aMZ']")
	private WebElement smiley;

	@FindBy(xpath = "//button[@class='a8v a8t a8t']")
	private WebElement emotionalSmiley;

	@FindBy(xpath = "//div[@class='a8I']/div/button[5]")
	private WebElement smiley1;

	@FindBy(xpath = "//div[@class='a8I']/div/button[10]")
	private WebElement smiley2;

	@FindBy(className = "a8o")
	private WebElement buttonSmileyClose;

	@FindBy(xpath = "//div[@class='T-I J-J5-Ji aoO T-I-atl L3']")
	private WebElement buttonSend;

	@FindBy(xpath = "//div[@class='aYF']")
	private WebElement pathNewMessage;

	@FindBy(xpath = "(//div[@class='wVboN'])[1]")
	private WebElement emoticonsWindow;

	private String pathToTextMessage = "(//img[@class='CToWUd'])";

	@FindBy(xpath = "//div[@class='vT']")
	private WebElement whomField;

	@FindBy(xpath = "//div[@class='Hp']/h2/div[2]")
	private WebElement themeField;

	private String signature = "//div[@dir='J-J5-Ji']";

	private String pathToMessageField = "//div[@class='Am Al editable LW-avf']/";

	private String ATTRIBUTE_STRING = "string";
	private String ATTRIBUTE_GOOMOGI = "goomoji";
	private String NEW_MESSAGE = "New Message";

	public SendLetterPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void clickWriteButton() {
		buttonWrite.click();
		logger.info("Dialog 'New Message' appears");
	}

	public void writeWhomLetter(String whom) {
		fieldWhom.sendKeys(whom);
	}

	public void writeNewMessage(String whom, String subject, String message) {
		fieldWhom.sendKeys(whom);
		fieldTheme.sendKeys(subject);
		fieldMessage.sendKeys(message);
		buttonSend.click();
		logger.info("Sent a letter with filled fields whom, subject, message");
	}

	public void writeNewMessageWithoutAttach(String whom, String subject,
			String message) {
		fieldWhom.sendKeys(whom);
		fieldTheme.sendKeys(subject);
		fieldMessage.sendKeys(message);
		logger.info("Fields whom, subject, message was filled");
	}

	public void writeNewMessageWithAttach(String whom, String subject,
			String message, String file) {
		Utils utils = new Utils();
		SendLetterPage sendLetterPage = new SendLetterPage(driver);
		buttonWrite.click();
		sendLetterPage.clickWriteButton();
		fieldWhom.sendKeys(whom);
		fieldTheme.sendKeys(subject);
		fieldMessage.sendKeys(message);
		attachment.click();
		utils.uploadFile(file);
		buttonSend.click();
		logger.info("Sent a letter with attach");
	}

	public void clickEmoticonIcon() {
		smiley.click();
		emotionalSmiley.click();
	}

	public Email chooseEmoticons() {
		Email choosenEmoticons = new Email();
		List<String> listSmiley = new ArrayList<String>();
		smiley1.click();
		listSmiley.add(smiley1.getAttribute(ATTRIBUTE_STRING));
		smiley2.click();
		listSmiley.add(smiley2.getAttribute(ATTRIBUTE_STRING));
		choosenEmoticons.setSmiley(listSmiley);
		buttonSmileyClose.click();
		logger.info("Emoticons was chosen");

		return choosenEmoticons;
	}

	public void clickSendButton() {
		buttonSend.click();
		logger.info("Sent a letter");
	}

	public boolean messageHasSignature() {
		return isElementPresent(signature);
	}

	public boolean windowNewMessageAppears() {
		return pathNewMessage.getText().equals(NEW_MESSAGE);
	}

	public boolean windowEmoticonsAppears() {
		return emoticonsWindow.isDisplayed();
	}

	public boolean hasChoosenEmoticons(List<String> choosenEmoticons) {
		return getElementAtribute(pathToMessageField + "img[" + 1 + "]",
				ATTRIBUTE_GOOMOGI).equals(choosenEmoticons.get(0))
				&& getElementAtribute(pathToMessageField + "img[" + 2 + "]",
						ATTRIBUTE_GOOMOGI).equals(choosenEmoticons.get(1));
	}

	public boolean windowNewMessageDisplayed() {
		return pathNewMessage.isDisplayed();
	}

	public boolean hasSentEmoticonsAtTheMail(List<String> choosenEmoticons) {
		return getElementAtribute(pathToTextMessage + "[1]", ATTRIBUTE_GOOMOGI)
				.equals(choosenEmoticons.get(0))
				&& getElementAtribute(pathToTextMessage + "[2]",
						ATTRIBUTE_GOOMOGI).equals(choosenEmoticons.get(1));
	}

	public boolean fildsInNewMessageWasFilledCorrectInformation(
			String username, String subject, String message) {
		return themeField.getText().equals(subject)
				&& fieldMessage.getText().equals(message);
	}

	public String getElementAtribute(String locator, String atribute) {
		try {
			return driver.findElement(By.xpath(locator)).getAttribute(atribute);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean isElementPresent(String locator) {
		return driver.findElements(By.xpath(locator)).size() > 0;
	}
}
