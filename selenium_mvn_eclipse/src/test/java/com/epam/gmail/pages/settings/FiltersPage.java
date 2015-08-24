package com.epam.gmail.pages.settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FiltersPage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/filters";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/filters']")
	private WebElement buttonFilters;

	@FindBy(xpath = "//div[@class='rU']/button[1]")
	private WebElement buttonSaveChanges;

	@FindBy(xpath = "//td[@class='rG']/span[1]")
	private WebElement buttonCreateNewFilter;

	@FindBy(className = "ZH nr aQa")
	private WebElement fieldFromFilter;

	@FindBy(xpath = "//span[@class='w-Pv ZG']/input")
	private WebElement hasAttachmentCheckboxFilter;

	@FindBy(className = "acM acN")
	private WebElement createFilterLink;

	@FindBy(xpath = "(//input[@type='checkbox'])[6]")
	private WebElement deleteItCheckboxFilter;

	@FindBy(xpath = "(//input[@type='checkbox'])[8]")
	private WebElement alwaysMarkItAsImportantCheckboxFilter;

	@FindBy(className = "T-I J-J5-Ji Zx acL T-I-atl L3")
	private WebElement buttonCreateFilter;

	public FiltersPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void setFilters(String username) {
		super.clickSettingsButton();
		super.clickSettingsButtonFromDropdownMenu();
		buttonFilters.click();
		buttonCreateNewFilter.click();
		fieldFromFilter.sendKeys(username);
		hasAttachmentCheckboxFilter.click();
		createFilterLink.click();
		deleteItCheckboxFilter.click();
		alwaysMarkItAsImportantCheckboxFilter.click();
		buttonCreateFilter.click();
	}
}
