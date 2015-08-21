package com.epam.gmail.pages.settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ThemePage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/oldthemes";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/oldthemes']")
	private WebElement buttonThemes;

	@FindBy(xpath = "//div[@class='rU']/button[1]")
	private WebElement buttonSaveChanges;

	@FindBy(xpath = "//a[@class='e NvzLyc']")
	private WebElement setThemes;

	@FindBy(xpath = "//div[@class='Kj-JD-Jl a8Y']/div[text()='Мои фото']")
	private WebElement buttonMyPhotos;

	@FindBy(xpath = "//div[text()='Загрузка фото']")
	private WebElement buttonDownloadPhoto;

	@FindBy(className = "a-b-c d-u d-u-Q")
	private WebElement buttonSelectFileOnComputer;

	@FindBy(className = "J-J5-Ji T-I T-I-atl")
	private WebElement buttonSaveTheme;

	
	public ThemePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}
	
	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Inbox page opened");
	}

	public void changeThemes() {
		buttonMyPhotos.click();
		buttonDownloadPhoto.click();
		buttonSelectFileOnComputer.click();
	}

	public void clickButtonMyPhotos() {
		buttonMyPhotos.click();
	}

	public void clickButtonDownloadPhoto() {
		buttonDownloadPhoto.click();
	}

	public void clickButtonSelectFileOnComputer() {
		buttonSelectFileOnComputer.click();
	}

	public void chooseThemesInset() {
		buttonThemes.click();
	}

	public void choosingThemeWithHighResolution(String themeBeach) {
		setThemes.click();
		driver.findElement(By.xpath(themeBeach)).click();
	}
}
