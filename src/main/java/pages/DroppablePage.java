package pages;

import org.openqa.selenium.By;

import reporting.MyLogger;

public class DroppablePage extends Page {
	private static final By SQUARE_LOCATOR = By.cssSelector("#draggable");
	private static final By TARGET_LOCATOR = By.cssSelector("#droppable");

	public DroppablePage dragNDropSquare() {
		browser.switchToFrame();
		browser.dragAndDrop(SQUARE_LOCATOR, TARGET_LOCATOR);
		MyLogger.info("Successfully dragged a square!");
		return this;
	}
}
