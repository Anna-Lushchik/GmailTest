package com.epam.gmail.test;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.gmail.pages.*;
import com.epam.gmail.pages.settings.*;
import com.epam.gmail.steps.Steps;
import com.epam.gmail.utils.Utils;

public class GmailAutomationTest {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);

	private WebDriver driver;
	private WebElement waitElement;

	private Steps steps;
	private MailPage mailPage;
	private SendLetterPage sendLetterPage;
	private SettingsPage settingsPage;
	private LoginPage loginPage;
	private ThemePage themePage;
	private GeneralPage generalPage;
	private Shortcut shortcut;
	private Utils utils;
	private Locators locators;

	private final String USERNAME_1 = "bob.keller.test@gmail.com";
	private final String PASSWORD_1 = "bob.keller";
	private final String USERNAME_2 = "joe.bennett.test@gmail.com";
	private final String PASSWORD_2 = "joe.bennett";
	private final String USERNAME_3 = "rosie.wilson.test@gmail.com";
	private final String PASSWORD_3 = "rosie.wilson";

	private String attributeAlt = "alt";
	private String attributeHidefocus = "hidefocus";
	private String attributeGoomoji = "goomoji";
	private String attach = "Приложение";
	private String messageSizeAttach = "Размер файла превышает допустимые 25 МБ.";
	private String themeTitle = "Выбор темы";
	private String themeSelectTitle = "Выберите фоновое изображение";
	private String messageTipeUploadFile = "Формат выбранного файла (DSC_1117.NEF) не поддерживается";
	private String settingsTitle = "Настройки";
	private String generalTitle = "Общие";
	private String newMessage = "Новое сообщение";
	private String newShortcut = "Новый ярлык";
	private String parentShortcut = "My shortcut";
	private String insertedShortcut = "My inserted shortcut";
	private String deleteShortcutsTitle = "Удаление ярлыков";
	private String selected = "Помеченные";
	private String starred = "starred";
	private String subject = "subject";
	private String choosenTheme = "//ssl.gstatic.com/ui/v1/icons/mail/themes/beach2/";

	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		steps = new Steps();
		driver = steps.initBrowser();
		mailPage = new MailPage(driver);
		sendLetterPage = new SendLetterPage(driver);
		settingsPage = new SettingsPage(driver);
		loginPage = new LoginPage(driver);
		themePage = new ThemePage(driver);
		generalPage = new GeneralPage(driver);
		shortcut = new Shortcut(driver);
		utils = new Utils();
		locators = new Locators();
	}

	@After
	public void tearDown() throws Exception {
		steps.closeDriver();
	}

	@Ignore
	@Test
	public void testSpam_1() throws InterruptedException {
		steps.markLetterAsSpam(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2);
		assertTrue(steps.isTheSameLetter("theme", "message2"));
	}
	
	@Ignore
	@Test
	public void testForward_2() throws InterruptedException, AWTException {
		steps.forwardLetter(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2,
				USERNAME_3, PASSWORD_3);
		assertTrue(steps.isTheSameLetter("theme", "message2"));
		assertEquals("false",
				getElementAtribute(locators.pathToAttributeMessage, attributeAlt)
						.contains(attach));
		
		mailPage.clickTrash();
		assertEquals("false",
				getElementAtribute(locators.pathToAttributeMessage, attributeAlt)
						.contains(attach));
		steps.changeLoginWhenTwoOrMoreNameGmail(USERNAME_3, PASSWORD_3);
		assertTrue(steps.isTheSameLetter("theme", "message"));
		assertEquals("false",
				getElementAtribute(locators.pathToAttributeMessage, attributeAlt)
						.contains(attach));
	}

	@Ignore
	@Test
	public void testMainMailBoxPage_3() throws InterruptedException,
			AWTException {
		steps.attachBigFile(USERNAME_1, PASSWORD_1);
		assertEquals(messageSizeAttach,
				getElementText(locators.pathToMessageSizeAttach));
	}

	@Ignore
	@Test
	public void testThemes_4() throws InterruptedException, AWTException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		
		settingsPage.clickSettingsButton();
		assertTrue(isElementDisplayed(locators.settingsDropdownList));
		
		settingsPage.clickThemesButtonFromDropdownMenu();
		assertEquals(themeTitle, getElementText(locators.pathThemeTitle));
		
		themePage.clickButtonMyPhotos();
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.pathSelectThemeTitle)));  
		assertEquals(themeSelectTitle, getElementText(locators.pathSelectThemeTitle));
		
		themePage.clickButtonDownloadPhoto();  
		themePage.clickButtonSelectFileOnComputer();

		utils.createNewFileWithWrongExtension("DSC.NEF");
		utils.uploadFile("DSC.NEF");
		assertEquals(messageTipeUploadFile,
				getElementText(locators.pathToMessageTipeUploadFile));
	}

	@Ignore
	@Test
	public void testSendMailWithAttachement_5() throws InterruptedException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		
		sendLetterPage.clickWriteButton();
		assertEquals(newMessage, getElementText(locators.pathNewMessage));
		
		sendLetterPage.writeWhomLetter(USERNAME_1);
		sendLetterPage.clickEmoticonIcon();
		assertTrue(isElementPresent(locators.emoticonsWindow));
		
		ArrayList listSmiley = sendLetterPage.chooseEmoticons();
		assertEquals(listSmiley.get(0),
				getElementAtribute(locators.pathToMessageField + "img[" + 1
						+ "]", attributeGoomoji));
		assertEquals(listSmiley.get(1),
				getElementAtribute(locators.pathToMessageField + "img[" + 2
						+ "]", attributeGoomoji));
		
		assertFalse(isElementDisplayed(locators.emoticonsWindow));
		
		sendLetterPage.clickSendButton();
		assertFalse(isElementDisplayed(locators.pathNewMessage));
		
		mailPage.openLastMessage();
		assertEquals(listSmiley.get(0),
				getElementAtribute(locators.pathToTextMessage + "[1]", attributeGoomoji));
		assertEquals(listSmiley.get(1),
				getElementAtribute(locators.pathToTextMessage + "[2]", attributeGoomoji));
	}

	@Ignore
	@Test
	public void testTheme_6() throws InterruptedException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		assertEquals(true, isElementDisplayed(locators.settingsDropdownList));
		
		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(getElementAtribute(locators.pathGeneralTitle, attributeHidefocus)
				.contains("true"));
		
		themePage.chooseThemesInset();
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.theme)));   
		assertTrue(getElementAtribute(locators.theme, attributeHidefocus).contains(
				"true"));
		
		themePage.choosingThemeWithHighResolution(locators.themeBeach);
		assertTrue(getElementAtribute(locators.pathToChoosenTheme, "src")
				.contains(choosenTheme));
	}

	@Ignore
	@Test
	public void testCreateShortcut_8() throws InterruptedException {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		
		shortcut.clickTriangleShortcut();
		assertTrue(isElementPresent(locators.shortcutMenu));
		
		shortcut.clickAddNestedShortcut();
		assertEquals(newShortcut, getElementText(locators.createShortcutTitle));
		
		shortcut.createNestedShortcut(insertedShortcut);
		assertFalse(isElementDisplayed(locators.createShortcutWindow));
		
		boolean check = shortcut.checkShortcutAtTheLeftSide(parentShortcut,
				insertedShortcut);
		assertEquals(true, check);
	}

	@Ignore
	@Test
	public void testChangeShortcut_9() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		
		shortcut.clickTriangleShortcut();
		assertTrue(isElementPresent(locators.shortcutMenu));
		
		shortcut.clickColorShortcut();
		assertTrue(isElementPresent(locators.colorsShortcutMenu));
		
		String shortcutColor = shortcut.chooseColoursShortcut();
		assertTrue(isElementPresent(locators.changeColorsShortcutWindow));
		
		shortcut.chooseShortcutsRadiobuttonAndConfirmColor();
		assertFalse(isElementPresent(locators.changeColorsShortcutWindow));
		
		boolean checkColor = shortcut.checkColorShortcut(shortcutColor);
		logger.info("Colour of both shortcuts is the same - " + checkColor);
	}

	@Ignore
	@Test
	public void testDeleteShortcut_10() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		
		shortcut.clickTriangleShortcut();
		assertTrue(isElementPresent(locators.shortcutMenu));
		
		shortcut.clickDeleteShortcut();
		assertTrue(isElementPresent(locators.deleteShortcutsWindow));
		assertEquals(deleteShortcutsTitle,
				getElementText(locators.pathDeleteShortcutsTitle));
		
		shortcut.checkPresenceBothShortcuts(parentShortcut, insertedShortcut);
		shortcut.buttonConfirmDeleteShortcut();
		new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locators.deleteShortcutsWindow)));
		assertFalse(isElementPresent(locators.deleteShortcutsWindow));
		
		boolean checkDelete = shortcut.checkDeleteShortcuts();
		logger.info("Both shortcuts (parent and inserted) are deleted - "
				+ !checkDelete);
	}

	@Ignore
	@Test
	public void testMarkItemAsSpamAndMarkSpamItemAsNotSpam_11()
			throws InterruptedException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		
		mailPage.clickInbox();
		assertTrue(isElementPresent(locators.listOfLetters));
		assertTrue(driver.getCurrentUrl().contains("inbox"));
		
		List<String> chosenLetter = mailPage.chooseTopItem();
		assertEquals("true",
				getElementAtribute(locators.lastMessageCheckbox, "aria-checked"));
		
		mailPage.markLetterAsSpam();
		assertFalse(isElementPresent("//div[text()='" + chosenLetter.get(1) + "']"));
		
		mailPage.clickSpam();
		assertTrue(isElementPresent(locators.listOfLetters));
		assertTrue(driver.getCurrentUrl().contains("spam"));
		
		ArrayList<String> letter = mailPage.openLastMessage();
		assertTrue(isElementPresent(locators.openedLetterWindow));
		
		mailPage.clickNotSpam();
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.openedLetterWindow)));;
		assertFalse(isElementPresent("//div[text()='" + chosenLetter.get(1) + "']"));
		assertTrue(driver.getCurrentUrl().contains("spam"));
		
		mailPage.clickInbox();
		assertTrue(isElementPresent(locators.listOfLetters));
		assertTrue(driver.getCurrentUrl().contains("inbox"));
		
		assertTrue(chosenLetter.contains(letter));  
	}

	@Ignore
	@Test
	public void testCheckingSignature_12() throws InterruptedException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		
		settingsPage.clickSettingsButton();
		settingsPage.clickSettingsButtonFromDropdownMenu();
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.pathSettingsTitle)));
		assertEquals(settingsTitle, getElementText(locators.pathSettingsTitle));
		
		generalPage.chooseGeneral();
		assertTrue(getElementAtribute(locators.pathGeneralTitle, attributeHidefocus)
				.contains("true"));
		assertEquals(generalTitle, getElementText(locators.pathGeneralTitle));
		
		generalPage.enterSignature(USERNAME_1);
		assertEquals(USERNAME_1, getElementText(locators.fieldSignature));
		
		settingsPage.clickButtonSaveChanges();
		assertFalse(isElementEnabled(locators.buttonSaveChanges));
		
		sendLetterPage.clickWriteButton();
		assertEquals(newMessage, getElementText(locators.pathNewMessage));
		
		sendLetterPage.checkMessageHasSignature();
		assertEquals(USERNAME_1, getElementText(locators.signature));
	}

	@Ignore
	@Test
	public void testCheckStarSelection_13() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='Cp']/div/table/tbody/tr[1]/td[3]/span")));
		
		ArrayList<String> starredLetter = mailPage.clickStar(); 
		assertEquals(selected,
				getElementAtribute(locators.lastMessageStar, "title"));
		
		mailPage.clickStarred();
		assertTrue(driver.getCurrentUrl().contains(starred));
		
		boolean messagePresent = steps.isTheSameLetter(starredLetter.get(0), starredLetter.get(1));
		logger.info("Message present in the list of starrred - " + messagePresent);
	}

	@Ignore
	@Test
	public void testCheckVacation_14() throws InterruptedException {
		Date date = new Date();
		steps.loginGmail(USERNAME_3, PASSWORD_3);
		
		settingsPage.clickSettingsButton();
		settingsPage.clickSettingsButtonFromDropdownMenu();
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locators.vacationResponderRadioButton)));;
		assertEquals(settingsTitle, getElementText(locators.pathSettingsTitle));
		
		generalPage.chooseVacationResponderOnRadiobutton();
		assertTrue(isElementSelected(locators.vacationResponderRadioButton));
		
		generalPage.enterDataVacationResponder("subject", "message");		
		settingsPage.clickButtonSaveChanges();
		assertTrue(getElementText(locators.topPage).contains(subject));
		mailPage.confirmVacation();
		mailPage.singOut();
		assertTrue(isElementPresent(locators.GoogleTitleOnLoginPage));
		
		steps.changeLoginNameGmail(USERNAME_2, PASSWORD_2);
		
		sendLetterPage.clickWriteButton();
		assertEquals(newMessage, getElementText(locators.pathNewMessage));
		new WebDriverWait(driver,60).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='wO nr l1']/textarea")));;
		
		sendLetterPage.writeNewMessageWithoutAttach(USERNAME_3, "Test14", "message");
		assertEquals(USERNAME_3, getElementAtribute(locators.fieldWhom, "email"));
		assertEquals("Test14", getElementText(locators.fieldTheme));
		assertEquals("message", getElementText(locators.fieldMessage));
		sendLetterPage.clickSendButton();
		
		boolean checkVacation = mailPage
				.checkGotAutoAnswerWithVacationEnteredData("Test14", "message");
		logger.info("you get auto-answer with vacation entered data - "
				+ checkVacation);
	}

	
	public boolean isElementPresent(String locator) {
		return driver.findElements(By.xpath(locator)).size() > 0;
	}

	public String getElementText(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean isElementDisplayed(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementSelected(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).isSelected();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isElementEnabled(String locator) {
		try {
			return driver.findElement(By.xpath(locator)).isEnabled();
		} catch (NoSuchElementException e) {
			return false;
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
