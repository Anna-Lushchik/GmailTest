package com.epam.gmail.pages.settings;

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

	@FindBy(xpath = "//div[text()='My Photos']")
	private WebElement buttonMyPhotos;

	@FindBy(xpath = "//div[text()='Upload a photo']")
	private WebElement buttonDownloadPhoto;

	@FindBy(className = "a-b-c d-u d-u-Q")
	private WebElement buttonSelectFileOnComputer;

	@FindBy(className = "J-J5-Ji T-I T-I-atl")
	private WebElement buttonSaveTheme;

	@FindBy(xpath = "//span[@class='Kj-JD-K7-K0']")
	private WebElement pathThemeTitle;

	@FindBy(xpath = "//div[@class='Xf-cg-fc Xf-Ke-lf']")
	private WebElement pathSelectThemeTitle;

	@FindBy(xpath = "//div[@style='margin-top: 27px;']")
	private WebElement pathToMessageTipeUploadFile;

	@FindBy(xpath = "//div[@class='a4t']/img")
	private WebElement pathToChoosenTheme;

	private String THEME_TITLE = "Pick your theme";
	private String THEME_SELECT_TITLE = "Select your background image";
	private String MESSAGE_TIPE_UPLOAD_FILE = "Selected file [DSC.NEF] is not supported for upload.";
	private String ATTRIBUTE_HIDEFOCUS = "hidefocus";
	private String ATTRIBUTE_SRC = "src";
	protected String THEME_BEACH = "//div[@aria-label='Beach (by: iStockPhoto)']";
	private String CHOOSEN_THEME = "//ssl.gstatic.com/ui/v1/icons/mail/themes/beach2/";

	public ThemePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Theme page opened");
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

	public void choosingThemeWithHighResolution() {
		setThemes.click();
		driver.findElement(By.xpath(THEME_BEACH)).click();
	}

	public boolean windowThemesAppears() {
		return pathThemeTitle.getText().equals(THEME_TITLE);
	}

	public boolean windowSelectYourBackgroundImageAppears() {
		new WebDriverWait(driver, 60).until(ExpectedConditions
				.presenceOfElementLocated((By) pathSelectThemeTitle));
		return pathSelectThemeTitle.getText().equals(THEME_SELECT_TITLE);
	}

	public boolean hasWarningMessageAboutTypeFile() {
		return pathToMessageTipeUploadFile.getText().equals(
				MESSAGE_TIPE_UPLOAD_FILE);
	}

	public boolean themeSettingsPageAppears() {
		new WebDriverWait(driver, 60).until(ExpectedConditions
				.presenceOfElementLocated((By) buttonThemes));
		return buttonThemes.getAttribute(ATTRIBUTE_HIDEFOCUS).contains("true");
	}

	public boolean backgroundChangedToChoosenTheme() {
		return pathToChoosenTheme.getAttribute(ATTRIBUTE_SRC).contains(CHOOSEN_THEME);
	}
}
