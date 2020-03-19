import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import browser.Browser;
import pages.DroppablePage;
import pages.MainPage;
import pages.ResizablePage;
import pages.SelectablePage;

public class ActionsDemoTests {
	@Test(description = "Drag-n-drop test")
	public void dragNDropDemo() {
		DroppablePage droppablePage = new MainPage().open().openDroppableLink();
		droppablePage.dragNDropSquare();
	}

	@Test(description = "Resize test")
	public void resizeDemo() {
		ResizablePage resizablePage = new MainPage().open().openResizableLink();
		resizablePage.resizeSquare();
	}

	@Test(description = "Select test")
	public void selectingDemo() {
		SelectablePage selectablePage = new MainPage().open().openSelectableLink();
		selectablePage.selectItems(5);
	}

	@AfterClass(description = "Close browser")
	public void quit() {
		Browser.quit();
	}
}
