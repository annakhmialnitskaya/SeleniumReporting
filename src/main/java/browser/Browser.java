package browser;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import reporting.MyLogger;

public class Browser {

	private static final int PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS = 15;
	private static final int COMMAND_DEFAULT_TIMEOUT_SECONDS = 10;
	private static final int WAIT_ELEMENT_TIMEOUT = 10;
	private static final String SCREENSHOTS_NAME_TPL = "screenshots/scr";
	private WebDriver driver;
	private static Browser instance = null;

	private Browser(WebDriver driver) {
		this.driver = driver;
	}

	public static Browser getInstance() {
		if (instance != null) {
			return instance;
		}
		return instance = init();
	}

	private static Browser init() {
		System.setProperty("webdriver.chrome.driver", "C:/programs/selenium/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(COMMAND_DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return new Browser(driver);
	}

	public void open(String url) {
		MyLogger.info("Going to URL: " + url);
		driver.get(url);
	}

	public void waitForElementPresent(By locator) {
		(new WebDriverWait(driver, Duration.ofSeconds(WAIT_ELEMENT_TIMEOUT)))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public void waitForElementEnabled(By locator) {
		(new WebDriverWait(driver, Duration.ofSeconds(WAIT_ELEMENT_TIMEOUT)))
				.until(ExpectedConditions.elementToBeClickable(locator));
	}

	private void waitForElementVisible(By locator) {
		(new WebDriverWait(driver, Duration.ofSeconds(WAIT_ELEMENT_TIMEOUT)))
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	private void highlightElement(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid green'",
				driver.findElement(locator));
	}

	private void unHighlightElement(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", driver.findElement(locator));
	}

	public void click(By locator) {
		waitForElementVisible(locator);
		MyLogger.info("Clicking element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")");
		highlightElement(locator);
		takeScreenshot();
		unHighlightElement(locator);
		driver.findElement(locator).click();
	}

	public void dragAndDrop(By locator, By targetLocator) {
		waitForElementVisible(locator);
		waitForElementVisible(targetLocator);
		WebElement element = driver.findElement(locator);
		WebElement target = driver.findElement(targetLocator);
		takeScreenshot();
		(new Actions(driver)).dragAndDrop(element, target).perform();

		MyLogger.info("Dragging element '" + driver.findElement(locator).getText() + "' (Located: " + locator + ")"
				+ "to '" + driver.findElement(targetLocator).getText() + "' (Located: " + targetLocator + ")");
	}

	public void selectItems(By firstLocator, By lastLocator) {
		new Actions(driver).clickAndHold(driver.findElement(firstLocator))
				.moveToElement(driver.findElement(lastLocator)).release().build().perform();
		takeScreenshot();
	}

	public void resize(By sizeHandleLocator, int xOffset, int yOffset) {
		WebElement handle = driver.findElement(sizeHandleLocator);
		new Actions(driver).clickAndHold(handle).moveByOffset(xOffset, yOffset).release(handle).build().perform();
		takeScreenshot();
	}

	private void takeScreenshot() {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String screenshotName = SCREENSHOTS_NAME_TPL + System.nanoTime();
			File copy = new File(screenshotName + ".png");
			FileUtils.copyFile(screenshot, copy);
			MyLogger.info("Saved screenshot: " + screenshotName);
		} catch (IOException e) {
			MyLogger.error("Failed to make screenshot");
		}
	}

	public static void quit() {
		if (instance != null) {
			try {
				instance.driver.quit();
			} finally {
				instance = null;
			}
		}
	}

	public void switchToFrame() {
		driver.switchTo().frame(0);
	}
}
