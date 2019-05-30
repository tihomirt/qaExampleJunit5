package com.qa.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qa.testbase.BrowsersEnum;

abstract class BasePage extends LoadableComponent<BasePage> {

  protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);

  protected WebDriver driver;
  protected BrowsersEnum browser;

  public BasePage(final WebDriver driver, BrowsersEnum browser) {
    this.driver = driver;
    this.browser = browser;

    logger.info("Currently on page: " + this.getClass().getSimpleName());

    PageFactory.initElements(driver, this);
    //trigger the load() -> isLoaded() -> load() process
    this.get();
  }

  @Override
  protected void load() {

  }

  @Override
  protected void isLoaded() throws Error {

  }
}