package com.qa.utils;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitForElementToAppear implements Function<WebElement, WebElement> {

	private static final int BASIC_WAIT_TIME_IN_SECONDS = 12;
	private WebDriver driver;

	public WaitForElementToAppear(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	public WebElement apply(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, BASIC_WAIT_TIME_IN_SECONDS);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitForElementToAppearFunction(WebElement webElement) {
		return new WaitForElementToAppear(driver).apply(webElement);
	}

}