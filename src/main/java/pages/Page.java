package pages;

import browser.Browser;

public class Page {
	protected Browser browser;

	protected Page() {
		this.browser = Browser.getInstance();
	}
}
