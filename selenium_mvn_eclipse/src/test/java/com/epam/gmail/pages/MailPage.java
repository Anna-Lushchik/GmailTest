package com.epam.gmail.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.gmail.steps.Steps;

public class MailPage extends AbstractPage {

	private static final Logger logger = Logger.getLogger(MailPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/#inbox";
	
	@FindBy(xpath = "//span[@class='gb_Ma']")
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

	@FindBy(xpath = "//div[@class='iH']/div/div/div[text()='Not spam']")
	private WebElement buttonNotSpam;

	@FindBy(xpath = "(//div[@class='T-Jo-auh'])[2]")
	private WebElement lastMessageCheckbox;

	@FindBy(xpath = "//td[@class='apU xY']")
	private WebElement lastMessageStar;

	@FindBy(xpath = "//div[@class='xS']")
	private WebElement lastMessageLink;

	private String lastMessageSubject = "(//div[@class='yW'])[1]";

	private String lastMessageText = "//div[@class='y6']";

	private String openedMessageSubject = "//h2[@class='hP']";

	private String openedMessageText = "//div[@class='a3s']";
	
	@FindBy(xpath = "(//a[@rel='noreferrer'])[1]")
	private WebElement confirmForwardingLink;	

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement confirmForwardingButton;

	@FindBy(xpath = "//div[@class='w-MH a6P']/span[1]")
	private WebElement confirmVacationLink;

	private String starredURL = "https://mail.google.com/mail/#starred";
	
	private String trashURL = "https://mail.google.com/mail/#trash";
	
	private String spamURL = "https://mail.google.com/mail/#spam";
	
	public MailPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Mail page opened");
	}

	public void clickInbox() {
		driver.navigate().to(BASE_URL);
		logger.info("In inbox folder");
	}

	public void clickStarred() {
		buttonStarred.click();
		driver.get(starredURL);
		logger.info("In starred folder");
	}

	public void clickTrash() {
		buttonElse.click();
		buttonTrash.click();
		driver.get(trashURL);
		logger.info("In trash folder");
	}

	public void clickSpam() {
		buttonElse.click();
		buttonSpam.click();
		driver.get(spamURL);
		logger.info("In spam folder");
	}

	public void markLetterAsSpam() {
		buttonToSpam.click();
		logger.info("Mark letter as spam");
	}

	public List<String> chooseTopItem() {
		List<String> letterText = new ArrayList<String>();
		letterText.add(driver.findElement(By.xpath(lastMessageSubject)).getText());
		letterText.add(driver.findElement(By.xpath(lastMessageText)).getText());
		lastMessageCheckbox.click();
		logger.info("Choose top item");
		return letterText;
	}

	public void clickNotSpam() {
		buttonNotSpam.click();
		logger.info("Mark letter as not spam");
	}

	public List<String> clickStar() {
		List<String> letterText = new ArrayList<String>();
		letterText.add(driver.findElement(By.xpath(lastMessageSubject)).getText());
		letterText.add(driver.findElement(By.xpath(lastMessageText)).getText());
		lastMessageStar.click();
		logger.info("Mark letter by star");
		return letterText;
	}

	public void confirmForward() throws InterruptedException {
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

	public  List<String> openLastMessage() {
		lastMessageLink.click();
		List<String> letterText = new ArrayList<String>();
		letterText.add(driver.findElement(By.xpath(openedMessageSubject)).getText());
		letterText.add(driver.findElement(By.xpath(openedMessageText)).getText());
		return letterText;
	}

	public void confirmVacation() {
		confirmVacationLink.click();
	}

	public boolean checkGotAutoAnswerWithVacationEnteredData(String theme,
			String message) throws InterruptedException {
		Steps steps = new Steps();
		for(int i = 0; i <= 50 || steps.isTheSameLetter(theme, message); i++) {
			Thread.sleep(300);
		}
		return steps.isTheSameLetter(theme, message);
	}

	public void singOut() {
		usernameLink.click();
		buttonSingOut.click();
		logger.info("Sing out performed");
	}
}
