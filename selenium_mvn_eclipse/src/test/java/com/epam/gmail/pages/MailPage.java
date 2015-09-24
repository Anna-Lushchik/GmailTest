package com.epam.gmail.pages;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.gmail.business.Email;

public class MailPage extends AbstractPage {

	private static final Logger logger = Logger.getLogger(MailPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/#inbox";

	@FindBy(xpath = "//div[@class='gb_Xb gb_Ua gb_ae gb_R']/a")
	private WebElement usernameLink;

	@FindBy(id = "gb_71")
	private WebElement buttonSingOut;

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#inbox']")
	private WebElement buttonInbox;

	@FindBy(xpath = "//span[@class='ait']")
	private WebElement buttonElse;

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#spam']")
	private WebElement buttonSpam;

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#starred']")
	private WebElement buttonStarred;

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#trash']")
	private WebElement buttonTrash;

	@FindBy(xpath = "//div[@class='asl T-I-J3 J-J5-Ji']")
	private WebElement buttonToSpam;

	@FindBy(xpath = "//div[text()='Not spam']")
	private WebElement buttonNotSpam;

	@FindBy(xpath = "(//td[@class='oZ-x3 xY'])[1]/div")
	private WebElement lastMessageCheckbox;

	@FindBy(xpath = "//td[@class='apU xY']")
	private WebElement lastMessageStar;

	@FindBy(xpath = "//div[@class='xS']")
	private WebElement lastMessageLink;

	@FindBy(xpath = "(//div[@class='yW'])[1]")
	private WebElement lastMessageSubject;

	@FindBy(xpath = "//div[@class='y6']")
	private WebElement lastMessageText;

	@FindBy(xpath = "//h2[@class='hP']")
	private WebElement openedMessageSubject;

	@FindBy(xpath = "//div[@class='a3s']")
	private WebElement openedMessageText;

	@FindBy(xpath = "(//a[@rel='noreferrer'])[1]")
	private WebElement confirmForwardingLink;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement confirmForwardingButton;

	@FindBy(xpath = "//div[@class='w-MH a6P']/span[1]")
	private WebElement confirmVacationLink;

	@FindBy(xpath = "//img[@class='yE']")
	private WebElement pathToAttributeMessage;

	@FindBy(xpath = "//span[@class='Kj-JD-K7-K0']")
	private WebElement pathToMessageSizeAttach;

	private String listOfLetters = "//div[@class='Cp']";

	private String openedLetterWindow = "//div[@class='nH if']";

	@FindBy(xpath = "//div[@class='gmail_signature']")
	private WebElement signature;

	@FindBy(xpath = "(//td[@class='apU xY'])[1]/span")
	private WebElement lastLetterStar;

	private String starredURL = "https://mail.google.com/mail/#starred";
	private String trashURL = "https://mail.google.com/mail/#trash";
	private String spamURL = "https://mail.google.com/mail/#spam";

	private String ATTRIBUTE_ALT = "alt";
	private String ATTRIBUTE_TITLE = "title";
	private String ATTACH = "Attachment";
	private String MESSAGE_SIZE_ATTACH = "The file that you are trying to send exceeds the 25 MB attachment limit.";
	private String STARRED = "Starred";
	private String SELECTED = "aria-checked";

	public MailPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Mail page opened");
	}

	public void goToInbox() {
		driver.navigate().to(BASE_URL);
		logger.info("In inbox folder");
	}

	public void goToStarred() {
		buttonStarred.click();
		driver.navigate().to(starredURL);
		logger.info("In starred folder");
	}

	public void goToTrash() {
		buttonElse.click();
		buttonTrash.click();
		driver.navigate().to(trashURL);
		logger.info("In trash folder");
	}

	public void goToSpam() {
		buttonElse.click();
		buttonSpam.click();
		driver.navigate().to(spamURL);
		logger.info("In spam folder");
	}

	public void markLastLetterAsSpam() {
		buttonToSpam.click();
		logger.info("Mark letter as spam");
	}

	public Email chooseTopItem() {
		lastMessageCheckbox.click();
		Email markedEmail = new Email();
		markedEmail.setSubject(lastMessageSubject.getText());
		markedEmail.setBody(lastMessageText.getText());
		logger.info("Choose top item");
		
		return markedEmail;
	}

	public void clickNotSpam() {
		buttonNotSpam.click();
		logger.info("Mark letter as not spam");
	}

	public Email clickStar() {
		lastMessageStar.click();
		Email starredEmail = new Email();
		starredEmail.setSubject(lastMessageSubject.getText());
		starredEmail.setBody(lastMessageText.getText());
		logger.info("Mark letter by star");

		return starredEmail;
	}

	public void confirmForward() {
		lastMessageLink.click();
		String originalWindow = driver.getWindowHandle();
		final Set<String> oldWindowsSet = driver.getWindowHandles();
		confirmForwardingLink.click();
		String newWindowHandle = (new WebDriverWait(driver, 10))
				.until(new ExpectedCondition<String>() {
					public String apply(WebDriver driver) {
						Set<String> newWindowsSet = driver.getWindowHandles();
						newWindowsSet.removeAll(oldWindowsSet);
						return newWindowsSet.size() > 0 ? newWindowsSet
								.iterator().next() : null;
					}
				});
		driver.switchTo().window(newWindowHandle);
		confirmForwardingButton.click();
		driver.switchTo().window(originalWindow);
	}

	public Email openLastMessage() {
		driver.navigate().refresh();
		lastMessageLink.click();
		Email openedEmail = new Email(); 
		openedEmail.setSubject(openedMessageSubject.getText());
		openedEmail.setBody(openedMessageText.getText());
		logger.info("Open last message");
		
		return openedEmail;
	}

	public void confirmVacation() {
		confirmVacationLink.click();
	}

	public boolean hasGotAutoAnswerWithVacationEnteredData(String theme,
			String message) {
		for (int i = 0; i <= 50 || isTheSameLetter(theme, message); i++) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return isTheSameLetter(theme, message);
	}

	public void singOut() {
		usernameLink.click();
		buttonSingOut.click();
		logger.info("Sing out performed");
	}

	public boolean hasTestableLetterInSpam(String theme, String message) {
		return isTheSameLetter(theme, message);
	}

	public boolean testableLetterMarkWithAttach(String theme, String message) {
		return pathToAttributeMessage.getAttribute(ATTRIBUTE_ALT).contains(ATTACH)
				&& isTheSameLetter(theme, message);
	}

	public boolean hasWarningMessageAboutSizeFile() {
		return pathToMessageSizeAttach.getText().equals(MESSAGE_SIZE_ATTACH);
	}

	public boolean listOfLettersAppears() {
		return isElementPresent(listOfLetters);
	}

	public boolean testableItemIsSelected() {
		return lastMessageCheckbox.getAttribute(SELECTED).equals("true");
	}

	public boolean testableItemIsStarred() {
		return lastLetterStar.getAttribute(ATTRIBUTE_TITLE).equals(STARRED);
	}

	public boolean testableItemRemoved(String testableItem) {
		return isElementPresent("//div[text()='" + testableItem + "']");
	}

	public boolean testableItemIsOpened() {
		return isElementPresent(openedLetterWindow);
	}

	public boolean newMessageHasSignature(String signatureText) {
		return signature.getText().equals(signatureText);
	}

	public boolean isTheSameLetter(String subject, String message) {
		return lastMessageSubject.getText().contains(subject)
				&& lastMessageText.getText().contains(message);
	}

	public boolean isElementPresent(String locator) {
		return driver.findElements(By.xpath(locator)).size() > 0;
	}
}
