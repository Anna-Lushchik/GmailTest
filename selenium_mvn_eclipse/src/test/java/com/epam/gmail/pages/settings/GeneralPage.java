package com.epam.gmail.pages.settings;

import org.apache.log4j.Logger;
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

	@FindBy(xpath = "//div[@class='w-MH a6P']")
	private WebElement topPage;

	private String attributeHidefocus = "hidefocus";
	private String generalTitle = "General";

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
		return buttonGeneral.getAttribute(attributeHidefocus).contains("true")
				&& buttonGeneral.getText().equals(generalTitle);
	}

	public boolean fieldSignatureHasEnteredText(String signature) {
		return fieldEnterSignature.getText().equals(signature);
	}

	public boolean vacationResponderOnSelected() {
		return vacationResponderOnRadiobutton.isSelected();
	}

	public boolean enteredSubjectPresentAtTheTopOfNewPage(String subject) {
		return topPage.getText().contains(subject);
	}
}
