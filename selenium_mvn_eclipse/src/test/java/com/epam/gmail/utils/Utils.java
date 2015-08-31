package com.epam.gmail.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.RandomAccessFile;

public class Utils {

	private String ACCESS_MODE = "rw";
	private int FILE_SIZE = 480 * 240 * 240;
	
	public void createNewBigFile(String file) {
		try {
			RandomAccessFile f = new RandomAccessFile(file, ACCESS_MODE);
			f.setLength(FILE_SIZE);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void createNewFile(String file) {
		try {
			File f = new File(file);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void uploadFile(String file) {
		File f = new File(file);
		String absolutePath = null;
		if (f.exists()) {
			absolutePath = f.getAbsolutePath();
		}
		Robot robot;
		try {
			robot = new Robot();
			StringSelection ss = new StringSelection(absolutePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(3000);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
