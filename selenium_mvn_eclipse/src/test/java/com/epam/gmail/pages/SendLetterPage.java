package com.epam.gmail.pages;

import org.apache.log4j.Logger;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.gmail.utils.Utils;

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

	private String signature = "//div[@dir='J-J5-Ji']";

	@FindBy(xpath = "//div[@class='T-I J-J5-Ji aoO T-I-atl L3']")
	private WebElement buttonSend;

	private String attributeString = "string";
	
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

	
	public void writeNewMessageWithoutAttach(String whom, String subject, String message) {
		fieldWhom.sendKeys(whom);
		fieldTheme.sendKeys(subject);
		fieldMessage.sendKeys(message);
		logger.info("Fields whom, subject, message was filled");
	}

	public void writeNewMessageWithAttach(String whom, String subject,
			String message) throws AWTException{
		Utils utils = new Utils();
		SendLetterPage sendLetterPage = new SendLetterPage(driver);
		buttonWrite.click();
		sendLetterPage.clickWriteButton();
		fieldWhom.sendKeys(whom);
		fieldTheme.sendKeys(subject);
		fieldMessage.sendKeys(message);
		attachment.click();
		utils.uploadFile("t.txt");
		buttonSend.click();
		logger.info("Sent a letter with attach");
	}

	public void clickEmoticonIcon() {
		smiley.click();
		emotionalSmiley.click();
	}

	public List<String> chooseEmoticons() {
		List<String> listSmiley = new ArrayList<String>();
		smiley1.click();
		listSmiley.add(smiley1.getAttribute(attributeString));
		smiley2.click();
		listSmiley.add(smiley2.getAttribute(attributeString));
		buttonSmileyClose.click();
		logger.info("Emoticons was chosen");
		return listSmiley;
	}

	public void clickSendButton() {
		buttonSend.click();
		logger.info("Sent a letter");
	}

	public boolean checkMessageHasSignature() {
		return driver.findElements(By.xpath(signature)).size() > 0;
	}
}
