package com.epam.gmail.pages.settings;

import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThemePage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/oldthemes";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/oldthemes']")
	private WebElement buttonThemes;

	@FindBy(xpath = "//div[@class='rU']/button[1]")
	private WebElement buttonSaveChanges;

	@FindBy(xpath = "//a[@class='e NvzLyc']")
	private WebElement setThemes;

	@FindBy(xpath = "//div[@class='Kj-JD-Jl a8Y']/div[text()='My Photos']")
	private WebElement buttonMyPhotos;

	@FindBy(xpath = "//div[text()='Upload a photo']")
	private WebElement buttonDownloadPhoto;

	@FindBy(className = "a-b-c d-u d-u-Q")
	private WebElement buttonSelectFileOnComputer;

	@FindBy(className = "J-J5-Ji T-I T-I-atl")
	private WebElement buttonSaveTheme;

	private String themeTitle = "Pick your theme";
	private String themeSelectTitle = "Select your background image";
	private String messageTipeUploadFile = "Selected file [DSC.NEF] is not supported for upload.";
	private String attributeHidefocus = "hidefocus";
	private String choosenTheme = "//ssl.gstatic.com/ui/v1/icons/mail/themes/beach2/";
	
	String pathThemeTitle = "//span[@class='Kj-JD-K7-K0']";
	String pathSelectThemeTitle = "//div[@class='Xf-cg-fc Xf-Ke-lf']";
	String pathToMessageTipeUploadFile = "//div[@style='margin-top: 27px;']";
	String theme = "//a[@href='https://mail.google.com/mail/#settings/oldthemes']";
	String pathToChoosenTheme = "//div[@class='a4t']/img";

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

	public boolean windowThemesAppears() {
		return getElementText(pathThemeTitle).equals(themeTitle);
	}

	public boolean windowSelectYourBackgroundImageAppears() {
		new WebDriverWait(driver, 60).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath(pathSelectThemeTitle)));
		return getElementText(pathSelectThemeTitle).equals(themeSelectTitle);
	}

	public boolean hasWarningMessageAboutTypeFile() {
		return getElementText(pathToMessageTipeUploadFile).equals(
				messageTipeUploadFile);
	}

	public boolean themeSettingsPageAppears() {
		new WebDriverWait(driver, 60).until(ExpectedConditions
				.presenceOfElementLocated(By.xpath(theme)));
		return getElementAtribute(theme, attributeHidefocus).contains("true");
	}

	public boolean backgroundChangedToChoosenTheme() {
		return getElementAtribute(pathToChoosenTheme, "src").contains(
				choosenTheme);
	}

	public String getElementText(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public String getElementAtribute(String locator, String atribute) {
		try {
			return driver.findElement(By.xpath(locator)).getAttribute(atribute);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
