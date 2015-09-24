package com.epam.gmail.pages.settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.gmail.pages.AbstractPage;

public class SettingsPage extends AbstractPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/#inbox";

	@FindBy(xpath = "//div[@class='aos T-I-J3 J-J5-Ji']")
	private WebElement buttonSettings;

	@FindBy(id = "ms")
	private WebElement buttonSettingsInSettings;

	@FindBy(id = "pbwc")
	private WebElement buttonThemeInSettings;

	@FindBy(xpath = "//button[@guidedhelpid='save_changes_button']")
	private WebElement buttonSaveChanges;

	@FindBy(xpath = "//div[@class='J-M asi aYO jQjAxd']/div")
	private WebElement settingsDropdownList;

	@FindBy(xpath = "//h2[@class='dt']")
	private WebElement pathSettingsTitle;
	
	@FindBy(xpath = "//div[@class='Tm aeJ']")
	private WebElement settingsWindow;

	public SettingsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void clickSettingsButton() {
		buttonSettings.click();
		logger.info("Drop down list appears");
	}

	public void clickSettingsButtonFromDropdownMenu() {
		buttonSettingsInSettings.click();
	}

	public void clickThemesButtonFromDropdownMenu() {
		buttonThemeInSettings.click();
	}

	public void clickButtonSaveChanges() {
		buttonSaveChanges.click();
		logger.info("Save changes in settings");
	}

	public boolean hasDropDownListSettings() {
		return settingsDropdownList.isDisplayed();
	}

	public boolean settingsWasOpened() {
		return settingsWindow.isDisplayed();
	}

	public boolean buttonSaveChangesAvailable() {
		return buttonSaveChanges.isEnabled();
	}
}
