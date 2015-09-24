package com.epam.gmail.pages.settings;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ForwardPage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/fwdandpop";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/fwdandpop']")
	private WebElement buttonForwarding;

	@FindBy(xpath = "//div[@class='rU']/button[1]")
	private WebElement saveChanges;

	@FindBy(xpath = "//input[@act='add']")
	private WebElement buttonAddForwardingAddress;

	@FindBy(xpath = "//div[@class='PN']/input")
	private WebElement fieldForwardingAddress;

	@FindBy(name = "next")
	private WebElement buttonNextForwarding;

	@FindBy(xpath = "//input[@type='submit']")
	private WebElement confirmForwardingAddress;

	@FindBy(className = "J-at1-auR")
	private WebElement buttonOkInConfirmForwardingAddress;

	@FindBy(xpath = "(//input[@name='sx_em'])[2]")
	private WebElement forwardCopyRadiobutton;

	public ForwardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
		logger.info("Forward page opened");
	}

	public void setForward(String username) {
		super.clickSettingsButton();
		super.clickSettingsButtonFromDropdownMenu();
		buttonForwarding.click();
		buttonAddForwardingAddress.click();
		fieldForwardingAddress.sendKeys(username);
		String originalWindow = driver.getWindowHandle();
		final Set<String> oldWindowsSet = driver.getWindowHandles();
		buttonNextForwarding.click();
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
		confirmForwardingAddress.click();
		driver.switchTo().window(originalWindow);
		buttonOkInConfirmForwardingAddress.click();
	}

	public void setForwardCopy(String username) {
		super.clickSettingsButton();
		super.clickSettingsButtonFromDropdownMenu();
		buttonForwarding.click();
		forwardCopyRadiobutton.click();
		saveChanges.click();
	}
}
