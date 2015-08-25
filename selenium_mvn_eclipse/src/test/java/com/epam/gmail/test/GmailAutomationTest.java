package com.epam.gmail.test;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.epam.gmail.pages.*;
import com.epam.gmail.pages.settings.*;
import com.epam.gmail.steps.Steps;
import com.epam.gmail.utils.Utils;

public class GmailAutomationTest {

	private static final Logger logger = Logger.getLogger(SettingsPage.class);

	private WebDriver driver;

	private Steps steps;
	private LoginPage loginPage;
	private MailPage mailPage;
	private SendLetterPage sendLetterPage;
	private SettingsPage settingsPage;
	private ThemePage themePage;
	private GeneralPage generalPage;
	private Shortcut shortcut;
	private Utils utils;

	private final String USERNAME_1 = "bob.keller.test@gmail.com";
	private final String PASSWORD_1 = "bob.keller";
	private final String USERNAME_2 = "joe.bennett.test@gmail.com";
	private final String PASSWORD_2 = "joe.bennett";
	private final String USERNAME_3 = "rosie.wilson.test@gmail.com";
	private final String PASSWORD_3 = "rosie.wilson";

	private String parentShortcut = "My shortcut";
	private String insertedShortcut = "My inserted shortcut";
	private String starred = "starred";
	private String subject = "subject";
	private String message = "message";
	private String theme = "theme";
	private String fileWithNotImageExtension = "DSC.NEF";
	private String bigFile = "t.txt";
	private String file = "file.txt";
	protected String themeBeach = "//div[@aria-label='Beach (by: iStockPhoto)']";
	private String spam = "spam";
	private String inbox = "inbox";

	@Before
	public void setUp() throws Exception {
		PropertyConfigurator.configure("log4j.properties");
		steps = new Steps();
		loginPage = new LoginPage(driver);
		driver = steps.initBrowser();
		mailPage = new MailPage(driver);
		sendLetterPage = new SendLetterPage(driver);
		settingsPage = new SettingsPage(driver);
		themePage = new ThemePage(driver);
		generalPage = new GeneralPage(driver);
		shortcut = new Shortcut(driver);
		utils = new Utils();
	}

	@After
	public void tearDown() throws Exception {
		steps.closeDriver();
	}

	@Ignore
	@Test
	public void testSecondLetterInSpamAfterFirstLetterMarkedAsSpam() {
		steps.markLetterAsSpam(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2);
		assertTrue(mailPage.hasTestableLetterInSpam(theme, "message2"));
	}

	@Ignore
	@Test
	public void testCreateForwardAndFilter() throws AWTException {
		utils.createNewFile(file);
		steps.forwardLetter(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2,
				USERNAME_3, PASSWORD_3, file);
		assertFalse(mailPage.testableLetterMarkWithAttach(theme, message));

		mailPage.clickTrash();
		assertTrue(mailPage.testableLetterMarkWithAttach(theme, message));

		steps.changeLoginWhenTwoOrMoreNameGmail(USERNAME_3, PASSWORD_3);
		assertFalse(mailPage.testableLetterMarkWithAttach(theme, message));
	}

	@Ignore
	@Test
	public void testSendLetterWithAttachMoreThen25Mb() throws AWTException {
		steps.attachBigFile(USERNAME_1, PASSWORD_1, bigFile);
		assertTrue(mailPage.hasWarningMessageAboutSizeFile());
	}

	@Ignore
	@Test
	public void testUploadThemesWithWrongExtension() throws AWTException {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		assertTrue(settingsPage.hasDropDownListSettings());

		settingsPage.clickThemesButtonFromDropdownMenu();
		assertTrue(themePage.windowThemesAppears());

		themePage.clickButtonMyPhotos();
		assertTrue(themePage.windowSelectYourBackgroundImageAppears());

		themePage.clickButtonDownloadPhoto();
		themePage.clickButtonSelectFileOnComputer();

		utils.createNewFile(fileWithNotImageExtension);
		utils.uploadFile(fileWithNotImageExtension);
		assertTrue(themePage.hasWarningMessageAboutTypeFile());
	}

	@Ignore
	@Test
	public void testSendMailWithEmoticons() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);

		sendLetterPage.clickWriteButton();
		assertTrue(sendLetterPage.windowNewMessageAppears());

		sendLetterPage.writeWhomLetter(USERNAME_1);
		sendLetterPage.clickEmoticonIcon();
		assertTrue(sendLetterPage.windowEmoticonsAppears());

		List<String> listSmiley = sendLetterPage.chooseEmoticons();
		assertTrue(sendLetterPage.hasChoosenEmoticons(listSmiley));
		assertFalse(sendLetterPage.windowEmoticonsAppears());

		sendLetterPage.clickSendButton();
		assertFalse(sendLetterPage.windowNewMessageDisplayed());

		mailPage.openLastMessage();
		assertTrue(sendLetterPage.hasSentEmoticonsAtTheMail(listSmiley));
	}

	@Ignore
	@Test
	public void testChooseThemeWithHighResolution() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		assertTrue(settingsPage.hasDropDownListSettings());

		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(generalPage.generalSettingsPageAppears());

		themePage.chooseThemesInset();
		assertTrue(themePage.themeSettingsPageAppears());

		themePage.choosingThemeWithHighResolution(themeBeach);
		assertTrue(themePage.backgroundChangedToChoosenTheme());
	}

	@Ignore
	@Test
	public void testCreateInsertedShortcut() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		shortcut.clickTriangleShortcut();
		assertTrue(shortcut.shortcutMenuAppears());

		shortcut.clickAddNestedShortcut();
		assertTrue(shortcut.dialogNewShortcutAppears());

		shortcut.createNestedShortcut(insertedShortcut);
		assertFalse(shortcut.windowNewShortcutDisplayed());

		assertTrue(shortcut.hasShortcutAtTheLeftSide(parentShortcut,
				insertedShortcut));
	}

	@Ignore
	@Test
	public void testChangeColourOfParentAndInsertedShortcutsAtTheSameTime() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		shortcut.clickTriangleShortcut();
		assertTrue(shortcut.shortcutMenuAppears());

		shortcut.clickColorShortcut();
		assertTrue(shortcut.colourVariantsAppear());

		String shortcutColor = shortcut.chooseColoursShortcut();
		assertTrue(shortcut.dialogChangeColourShortcutsAppears());

		shortcut.chooseShortcutsRadiobuttonAndConfirmColor();
		assertFalse(shortcut.dialogChangeColourShortcutsAppears());

		boolean checkColor = shortcut
				.colorShortcutIsTheSameAsChoosen(shortcutColor);
		logger.info("Colour of both shortcuts is the same - " + checkColor);
	}

	@Ignore
	@Test
	public void testDeleteParentAndInsertedShortcutsAtTheSameTime() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		shortcut.clickTriangleShortcut();
		assertTrue(shortcut.shortcutMenuAppears());

		shortcut.clickDeleteShortcut();
		assertTrue(shortcut.dialogDeleteShortcutsAppears());

		shortcut.presenceBothShortcuts(parentShortcut, insertedShortcut);
		shortcut.buttonConfirmDeleteShortcut();

		assertFalse(shortcut.dialogDeleteShortcutsAppears());

		boolean checkDelete = shortcut.shortcutsDeleted();
		logger.info("Both shortcuts (parent and inserted) are deleted - "
				+ !checkDelete);
	}

	@Ignore
	@Test
	public void testMarkItemAsSpamAndMarkSpamItemAsNotSpam() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		mailPage.clickInbox();
		assertTrue(driver.getCurrentUrl().contains(inbox));
		assertTrue(mailPage.listOfLettersAppears());

		List<String> chosenLetter = mailPage.chooseTopItem();
		assertTrue(mailPage.testableItemIsSelected());

		mailPage.markLastLetterAsSpam();
		assertFalse(mailPage.testableItemRemoved(chosenLetter));

		mailPage.clickSpam();
		assertTrue(driver.getCurrentUrl().contains(spam));
		assertTrue(mailPage.listOfLettersAppears());

		List<String> letter = mailPage.openLastMessage();
		assertTrue(mailPage.testableItemIsOpened());

		mailPage.clickNotSpam();
		assertTrue(driver.getCurrentUrl().contains(spam));
		assertFalse(mailPage.testableItemRemoved(chosenLetter));

		mailPage.clickInbox();
		assertTrue(driver.getCurrentUrl().contains(inbox));
		assertTrue(mailPage.listOfLettersAppears());

		assertTrue(chosenLetter.contains(letter));
	}

	@Ignore
	@Test
	public void testCreateSignature() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(settingsPage.settingsWasOpened());

		generalPage.chooseGeneral();
		assertTrue(generalPage.generalSettingsPageAppears());

		generalPage.enterSignature(USERNAME_1);
		assertTrue(generalPage.fieldSignatureHasEnteredText(USERNAME_1));

		settingsPage.clickButtonSaveChanges();
		assertFalse(settingsPage.buttonSaveChangesAvailable());

		sendLetterPage.clickWriteButton();
		assertTrue(sendLetterPage.windowNewMessageAppears());

		sendLetterPage.messageHasSignature();
		assertTrue(mailPage.newMessageHasSignature(USERNAME_1));
	}

	@Ignore
	@Test
	public void testCheckStarSelection() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		List<String> starredLetter = mailPage.clickStar();
		assertTrue(mailPage.testableItemIsStarred());

		mailPage.clickStarred();
		assertTrue(driver.getCurrentUrl().contains(starred));

		boolean messagePresent = mailPage.isTheSameLetter(starredLetter.get(0),
				starredLetter.get(1));
		logger.info("Message present in the list of starrred - "
				+ messagePresent);
	}

	@Ignore
	@Test
	public void testCreateVacation() throws InterruptedException {
		steps.loginGmail(USERNAME_3, PASSWORD_3);
		settingsPage.clickSettingsButton();
		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(settingsPage.settingsWasOpened());

		generalPage.chooseVacationResponderOnRadiobutton();
		assertTrue(generalPage.vacationResponderOnSelected());

		generalPage.enterDataVacationResponder(subject, message);
		settingsPage.clickButtonSaveChanges();
		assertTrue(generalPage.enteredSubjectPresentAtTheTopOfNewPage(subject));

		mailPage.confirmVacation();
		mailPage.singOut();
		assertTrue(loginPage.mainLoginPageWasOpened());

		steps.changeLoginNameGmail(USERNAME_2, PASSWORD_2);
		sendLetterPage.clickWriteButton();
		assertTrue(sendLetterPage.windowNewMessageAppears());

		sendLetterPage.writeNewMessageWithoutAttach(USERNAME_3, "Test14",
				message);
		assertTrue(sendLetterPage.fildsInNewMessageWasFilledCorrectInformation(
				USERNAME_3, "Test14", message));
		sendLetterPage.clickSendButton();

		boolean checkVacation = mailPage
				.hasGotAutoAnswerWithVacationEnteredData("Test14", message);
		logger.info("you get auto-answer with vacation entered data - "
				+ checkVacation);
	}
}
