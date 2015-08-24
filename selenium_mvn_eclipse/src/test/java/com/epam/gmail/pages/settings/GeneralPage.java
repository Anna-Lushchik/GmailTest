package com.epam.gmail.pages.settings;

import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GeneralPage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/general";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/general']")
	private WebElement buttonGeneral;

	@FindBy(xpath = "//div[@class='rU']/button[1]")
	private WebElement buttonSaveChanges;

	@FindBy(xpath = "//div[@aria-label='Signature']")
	private WebElement fieldEnterSignature;

	@FindBy(xpath = "(//input[@name='bx_ve'])[2]")
	private WebElement vacationResponderOnRadiobutton;

	@FindBy(xpath = "//input[@class='Da']")
	private WebElement fieldSubjectVacationResponder;

	@FindBy(xpath = "(//div[@class='Am Al editable Xp0HJf-LW-avf'])[2]")
	private WebElement fieldMessageVacationResponder;

	private String attributeHidefocus = "hidefocus";
	private String generalTitle = "General";

	String pathGeneralTitle = "//a[@href='https://mail.google.com/mail/#settings/general']";
	String fieldSignature = "//div[@aria-label='Signature']";
	String vacationResponderRadioButton = "(//input[@name='bx_ve'])[2]";
	String topPage = "//div[@class='w-MH a6P']";

	public GeneralPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void chooseGeneral() {
		buttonGeneral.click();
	}

	public void enterSignature(String signature) {
		fieldEnterSignature.clear();
		fieldEnterSignature.sendKeys(signature);
	}

	public void chooseVacationResponderOnRadiobutton() {
		vacationResponderOnRadiobutton.click();
	}

	public void enterDataVacationResponder(String subject, String message) {
		fieldSubjectVacationResponder.clear();
		fieldSubjectVacationResponder.sendKeys(subject);
		fieldMessageVacationResponder.clear();
		fieldMessageVacationResponder.sendKeys(message);
	}

	public boolean generalSettingsPageAppears() {
		return getElementAtribute(pathGeneralTitle, attributeHidefocus)
				.contains("true")
				&& getElementText(pathGeneralTitle).equals(generalTitle);
	}

	public boolean fieldSignatureHasEnteredText(String signature) {
		return getElementText(fieldSignature).equals(signature);
	}

	public boolean vacationResponderOnSelected() {
		return isElementSelected(vacationResponderRadioButton);
	}

	public boolean enteredSubjectPresentAtTheTopOfNewPage(String subject) {
		return getElementText(topPage).contains(subject);
	}

	public String getElementAtribute(String locator, String atribute) {
		try {
			return driver.findElement(By.xpath(locator)).getAttribute(atribute);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean isElementSelected(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).isSelected();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
