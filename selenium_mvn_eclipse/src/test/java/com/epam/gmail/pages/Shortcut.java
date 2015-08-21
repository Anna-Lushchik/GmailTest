package com.epam.gmail.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Shortcut extends AbstractPage {

	private static final Logger logger = Logger.getLogger(MailPage.class);
	private final String BASE_URL = "https://mail.google.com/mail/#inbox";

	@FindBy(xpath = "//div[@class='p6']")
	private WebElement triangleTheLeftOfTheShortcut;

	@FindBy(xpath = "//div[@class='Kj-JD-Jz']/input")
	private WebElement fieldShortcutName;

	@FindBy(className = "J-at1-auR")
	private WebElement buttonConfirmCreateShortcut;

	@FindBy(xpath = "//input[@class='ajJ']")
	private WebElement placeAnShortcutCheckbox;

	@FindBy(xpath = "//option[@value='My shortcut']")
	private WebElement choosePlaceAnShortcutDropdown;
	
	@FindBy(xpath = "//span[@class='J-Ph-hFsbo']")
	private WebElement buttonColorShortcut;

	@FindBy(id = "JA-Kn-Jr-Kw-Jn8")
	private WebElement colorShortcut;

	@FindBy(xpath = "(//input[@class='ajH'])[2]")
	private WebElement chooseShortcutsRadiobutton;

	@FindBy(className = "J-at1-auR")
	private WebElement buttonConfirmColorShortcut;

	@FindBy(xpath="//div[text()='Добавить вложенный ярлык']")
	private WebElement buttonAddNestedShortcut;

	@FindBy(xpath="//div[text()='Удалить ярлык']")
	private WebElement buttonDeleteShortcut;

	@FindBy(xpath = "//div[@class='Kj-JD-Jl']/button[1]")
	private WebElement buttonConfirmDeleteShortcut;

	@FindBy(xpath = "//a[@title='My shortcut']")
	private WebElement shortcutParentLink;

	@FindBy(xpath = "//a[@title='My inserted shortcut']")
	private WebElement shortcutInsertedLink;

	@FindBy(xpath = "(//div[@class='TN aY7xie aze'])[1]")
	private WebElement buttonOpenNestedShortcut;

	@FindBy(xpath = "//div[@class='TN aY7xie aik']")
	private WebElement myInsertedShortcut;

	@FindBy(xpath = "//div[@class='pM']")
	private WebElement pathToColorShortcut;

	@FindBy(xpath = "(//span[@class='ajP'])[1]")
	private WebElement parentShortcut;

	@FindBy(xpath = "(//span[@class='ajP'])[2]")
	private WebElement nestedShortcut;

	private String attributeStyle = "style";

	public Shortcut(WebDriver driver) {
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public void openPage() {
		driver.navigate().to(BASE_URL);
	}

	public void clickTriangleShortcut() {
		Actions action = new Actions(driver);
		action.moveToElement(shortcutParentLink).build().perform();
		triangleTheLeftOfTheShortcut.click();
	}

	public void createNestedShortcut(String nameShortcut) {
		fieldShortcutName.sendKeys(nameShortcut);
		if (!placeAnShortcutCheckbox.isSelected()) {
			placeAnShortcutCheckbox.click();
		}
		choosePlaceAnShortcutDropdown.click();
		buttonConfirmCreateShortcut.click();
		logger.info("Nested shortcut was created");
	}

	public void clickColorShortcut() {
		buttonColorShortcut.click();
	}

	public boolean checkShortcutAtTheLeftSide(String nameShortcutParent,
			String nameShortcutNested) {
		buttonOpenNestedShortcut.click();
		return myInsertedShortcut.getText().contains(nameShortcutNested);
	}

	public String chooseColoursShortcut() {
		colorShortcut.click();
		return colorShortcut.getAttribute(attributeStyle);
	}

	public void chooseShortcutsRadiobuttonAndConfirmColor() {
		chooseShortcutsRadiobutton.click();
		buttonConfirmColorShortcut.click();
		logger.info("Shortcuts color was changed");
	}

	public boolean checkColorShortcut(String colorShortcut) {
		Actions action = new Actions(driver);
		action.moveToElement(shortcutParentLink).build().perform();
		return pathToColorShortcut.getAttribute(attributeStyle).contains(
				colorShortcut);
	}

	public void clickAddNestedShortcut() {
		buttonAddNestedShortcut.click();
	}

	public void clickDeleteShortcut() {
		buttonDeleteShortcut.click();
	}

	public void checkPresenceBothShortcuts(String nameShortcutParent,
			String nameShortcutNested) {
		boolean checkPresence = false;
		if (parentShortcut.getText().contains(nameShortcutParent)
				&& nestedShortcut.getText().contains(nameShortcutNested)) {
			checkPresence = true;
		}
		logger.info("Names of both shortcuts (parent and inserted) are present at the dialog - "
				+ checkPresence);
	}

	public void buttonConfirmDeleteShortcut() {
		buttonConfirmDeleteShortcut.click();
		logger.info("Shortcuts was deleted");
	}

	public boolean checkDeleteShortcuts() {
		boolean deleted = false;
		boolean deleted1 = driver.findElements(By.xpath("//a[@title='My shortcut']"))
				.size() > 0;
		boolean deleted2 = driver.findElements(
				By.xpath("//a[@title='My inserted shortcut']")).size() > 0;
		if (deleted1 && deleted2) {
			deleted = true;
		}
		return deleted;
	}
}
