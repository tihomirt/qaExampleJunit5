package com.qa.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.qa.testbase.BrowsersEnum;
import com.qa.utils.WaitForElementToAppear;

public class AdoptOpenJdkPage extends BasePage {
	
	@FindBy (id = "dl-version-text")
	private WebElement version;
	
	public AdoptOpenJdkPage(WebDriver driver, BrowsersEnum browser) {
		super(driver, browser);
	}

	@Override
	protected void isLoaded() {
		new WaitForElementToAppear(driver).apply(version);
	}

	public String getVersion() {
		return version.getText();
	}
	
	public String getUrl() {
		return driver.getCurrentUrl();
	}
}
