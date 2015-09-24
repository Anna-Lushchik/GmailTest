package com.epam.gmail.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.epam.gmail.business.Email;
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

	private String PARENT_SHORTCUT = "My shortcut";
	private String INSERTED_SHORTCUT = "My inserted shortcut";
	private String STARRED = "starred";
	private String SUBJECT = "subject";
	private String THEME = "theme";
	private String MESSAGE = "message";
	private String THEME1 = "theme1";
	private String MESSAGE1 = "message1";
	private String THEME2 = "theme2";
	private String MESSAGE2 = "message2";
	private String FILE_WITH_NOT_IMAGE_EXTENSION = "DSC.NEF";
	private String BIG_FILE = "t.txt";
	private String FILE = "file.txt";
	private String SPAM = "spam";
	private String INBOX = "inbox";

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

	//@Ignore
	@Test
	public void testSecondLetterInSpamAfterFirstLetterMarkedAsSpam() {
		steps.markLetterAsSpam(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2,
				THEME1, MESSAGE1, THEME2, MESSAGE2);
		assertTrue(mailPage.hasTestableLetterInSpam(THEME2, MESSAGE2));
	}

	//@Ignore
	@Test
	public void testCreateForwardAndFilter() {
		utils.createNewFile(FILE);
		steps.forwardLetter(USERNAME_1, PASSWORD_1, USERNAME_2, PASSWORD_2,
				USERNAME_3, PASSWORD_3, FILE, THEME, MESSAGE);
		assertFalse(mailPage.testableLetterMarkWithAttach(THEME, MESSAGE));

		mailPage.goToTrash();
		assertTrue(mailPage.testableLetterMarkWithAttach(THEME, MESSAGE));

		steps.changeLoginWhenTwoOrMoreNameGmail(USERNAME_3, PASSWORD_3);
		assertFalse(mailPage.testableLetterMarkWithAttach(THEME, MESSAGE));
	}

	//@Ignore
	@Test
	public void testSendLetterWithAttachMoreThen25Mb() {
		steps.attachBigFile(USERNAME_1, PASSWORD_1, BIG_FILE, THEME, MESSAGE);
		assertTrue(mailPage.hasWarningMessageAboutSizeFile());
	}

	//@Ignore
	@Test
	public void testUploadThemesWithWrongExtension() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		assertTrue(settingsPage.hasDropDownListSettings());

		settingsPage.clickThemesButtonFromDropdownMenu();
		assertTrue(themePage.windowThemesAppears());

		themePage.clickButtonMyPhotos();
		assertTrue(themePage.windowSelectYourBackgroundImageAppears());

		themePage.clickButtonDownloadPhoto();
		themePage.clickButtonSelectFileOnComputer();

		utils.createNewFile(FILE_WITH_NOT_IMAGE_EXTENSION);
		utils.uploadFile(FILE_WITH_NOT_IMAGE_EXTENSION);
		assertTrue(themePage.hasWarningMessageAboutTypeFile());
	}

	//@Ignore
	@Test
	public void testSendMailWithEmoticons() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);

		sendLetterPage.clickWriteButton();
		assertTrue(sendLetterPage.windowNewMessageAppears());

		sendLetterPage.writeWhomLetter(USERNAME_1);
		sendLetterPage.clickEmoticonIcon();
		assertTrue(sendLetterPage.windowEmoticonsAppears());

		Email choosenEmoticons = sendLetterPage.chooseEmoticons();
		assertTrue(sendLetterPage.hasChoosenEmoticons(choosenEmoticons
				.getSmiley()));
		assertFalse(sendLetterPage.windowEmoticonsAppears());

		sendLetterPage.clickSendButton();
		assertFalse(sendLetterPage.windowNewMessageDisplayed());

		mailPage.openLastMessage();
		assertTrue(sendLetterPage.hasSentEmoticonsAtTheMail(choosenEmoticons
				.getSmiley()));
	}

	//@Ignore
	@Test
	public void testChooseThemeWithHighResolution() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		settingsPage.clickSettingsButton();
		assertTrue(settingsPage.hasDropDownListSettings());

		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(generalPage.generalSettingsPageAppears());

		themePage.chooseThemesInset();
		assertTrue(themePage.themeSettingsPageAppears());

		themePage.choosingThemeWithHighResolution();
		assertTrue(themePage.backgroundChangedToChoosenTheme());
	}

	//@Ignore
	@Test
	public void testCreateInsertedShortcut() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		shortcut.clickTriangleShortcut();
		assertTrue(shortcut.shortcutMenuAppears());

		shortcut.clickAddNestedShortcut();
		assertTrue(shortcut.dialogNewShortcutAppears());

		shortcut.createNestedShortcut(INSERTED_SHORTCUT);
		assertFalse(shortcut.windowNewShortcutDisplayed());

		assertTrue(shortcut.hasShortcutAtTheLeftSide(PARENT_SHORTCUT,
				INSERTED_SHORTCUT));
	}

	//@Ignore
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

	//@Ignore
	@Test
	public void testDeleteParentAndInsertedShortcutsAtTheSameTime() {
		steps.loginGmail(USERNAME_3, PASSWORD_3);
		shortcut.clickTriangleShortcut();
		assertTrue(shortcut.shortcutMenuAppears());

		shortcut.clickDeleteShortcut();
		assertTrue(shortcut.dialogDeleteShortcutsAppears());

		shortcut.presenceBothShortcuts(PARENT_SHORTCUT, INSERTED_SHORTCUT);
		shortcut.buttonConfirmDeleteShortcut();

		assertFalse(shortcut.dialogDeleteShortcutsAppears());

		boolean checkDelete = shortcut.shortcutsDeleted();
		logger.info("Both shortcuts (parent and inserted) are deleted - "
				+ !checkDelete);
	}

	//@Ignore
	@Test
	public void testMarkItemAsSpamAndMarkSpamItemAsNotSpam() {
		steps.loginGmail(USERNAME_1, PASSWORD_1);
		mailPage.goToInbox();
		assertTrue(driver.getCurrentUrl().contains(INBOX));
		assertTrue(mailPage.listOfLettersAppears());

		Email markedEmail = mailPage.chooseTopItem();
		assertTrue(mailPage.testableItemIsSelected());

		mailPage.markLastLetterAsSpam();
		assertFalse(mailPage.testableItemRemoved(markedEmail.getBody()));

		mailPage.goToSpam();
		assertTrue(driver.getCurrentUrl().contains(SPAM));
		assertTrue(mailPage.listOfLettersAppears());

		Email openedEmail = mailPage.openLastMessage();
		assertTrue(mailPage.testableItemIsOpened());

		mailPage.clickNotSpam();
		assertTrue(driver.getCurrentUrl().contains(SPAM));
		assertFalse(mailPage.testableItemRemoved(markedEmail.getBody()));

		mailPage.goToInbox();
		assertTrue(driver.getCurrentUrl().contains(INBOX));
		assertTrue(mailPage.listOfLettersAppears());

		assertTrue(markedEmail.getBody().contains(openedEmail.getBody()));
	}

	//@Ignore
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

	//@Ignore
	@Test
	public void testCheckStarSelection() {
		steps.loginGmail(USERNAME_2, PASSWORD_2);
		Email starredEmail = mailPage.clickStar();
		assertTrue(mailPage.testableItemIsStarred());

		mailPage.goToStarred();
		assertTrue(driver.getCurrentUrl().contains(STARRED));

		boolean messagePresent = mailPage.isTheSameLetter(
				starredEmail.getSubject(), starredEmail.getBody());
		assertTrue(messagePresent);
		logger.info("Message present in the list of starrred - "
				+ messagePresent);
	}

	//@Ignore
	@Test
	public void testCreateVacation() {
		steps.loginGmail(USERNAME_3, PASSWORD_3);
		settingsPage.clickSettingsButton();
		settingsPage.clickSettingsButtonFromDropdownMenu();
		assertTrue(settingsPage.settingsWasOpened());

		generalPage.chooseVacationResponderOnRadiobutton();
		assertTrue(generalPage.vacationResponderOnSelected());

		generalPage.enterDataVacationResponder(SUBJECT, MESSAGE);
		settingsPage.clickButtonSaveChanges();
		assertTrue(generalPage.enteredSubjectPresentAtTheTopOfNewPage(SUBJECT));

		mailPage.confirmVacation();
		mailPage.singOut();

		steps.changeLoginNameGmail(USERNAME_2, PASSWORD_2);
		sendLetterPage.clickWriteButton();
		assertTrue(sendLetterPage.windowNewMessageAppears());

		sendLetterPage.writeNewMessageWithoutAttach(USERNAME_3, THEME, MESSAGE);
		assertTrue(sendLetterPage.fildsInNewMessageWasFilledCorrectInformation(
				USERNAME_3, THEME, MESSAGE));
		sendLetterPage.clickSendButton();

		boolean checkVacation = mailPage
				.hasGotAutoAnswerWithVacationEnteredData(THEME, MESSAGE);
		logger.info("you get auto-answer with vacation entered data - "
				+ checkVacation);
	}
}
