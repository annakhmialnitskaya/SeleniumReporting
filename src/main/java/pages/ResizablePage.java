package pages;

import org.openqa.selenium.By;

import reporting.MyLogger;

public class ResizablePage extends Page {
	private static final By SIZE_HANDLE_LOCATOR = By.cssSelector(".ui-resizable-se");

	public ResizablePage resizeSquare() {
		browser.switchToFrame();
		browser.resize(SIZE_HANDLE_LOCATOR, 150, 100);
		MyLogger.info("Successfully resized a square!");
		return this;
	}
}
