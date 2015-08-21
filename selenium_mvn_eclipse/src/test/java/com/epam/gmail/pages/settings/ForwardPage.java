package com.epam.gmail.pages.settings;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ForwardPage extends SettingsPage {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/u/0/#settings/fwdandpop";

	@FindBy(xpath = "//a[@href='https://mail.google.com/mail/#settings/fwdandpop']")
	private WebElement buttonForwarding;

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

	@FindBy(name = "sx_em")
	private WebElement forwardCopyRadiobutton;
	
	public ForwardPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}
	
	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void setForward(String username) throws InterruptedException {
		super.clickSettingsButton();
		super.clickSettingsButtonFromDropdownMenu();
		buttonForwarding.click();
		buttonAddForwardingAddress.click();
		fieldForwardingAddress.sendKeys(username);
		buttonNextForwarding.click();
		Thread.sleep(2500);
		Set<String> handles = driver.getWindowHandles();
		driver.switchTo().window(handles.toArray(new String[handles.size()])[0]);
		confirmForwardingAddress.click();  //--
		buttonOkInConfirmForwardingAddress.click();
	}

	public void setForwardCopy(String username) {
		super.clickSettingsButton();
		super.clickSettingsButtonFromDropdownMenu();
		buttonForwarding.click();
		if (!forwardCopyRadiobutton.isSelected()) {
			forwardCopyRadiobutton.click();
		}
		driver.findElement(By.linkText(username)).click();
		super.clickButtonSaveChanges();
	}
}
