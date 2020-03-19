package pages;

import org.openqa.selenium.By;

import reporting.MyLogger;

public class SelectablePage extends Page {

	public static final By FIRST_ITEM_LOCATOR = By.xpath("//li[1]");
	public static final String LAST_ITEM_LOCATOR_PATTERN = "//li[%d]";

	public SelectablePage selectItems(int itemsToSelect) {
		browser.switchToFrame();
		By lastItemLocator = By.xpath(String.format(LAST_ITEM_LOCATOR_PATTERN, itemsToSelect));
		browser.selectItems(FIRST_ITEM_LOCATOR, lastItemLocator);
		MyLogger.info("Successfully selected " + itemsToSelect + " elements!");
		return this;
	}
}
