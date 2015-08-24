package com.epam.gmail.pages.settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.gmail.pages.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	private String settingsTitle = "Settings";

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
		new WebDriverWait(driver, 60).until(ExpectedConditions
				.presenceOfElementLocated((By) pathSettingsTitle));
		return pathSettingsTitle.getText().equals(settingsTitle);
	}

	public boolean buttonSaveChangesAvailable() {
		return buttonSaveChanges.isEnabled();
	}
}
