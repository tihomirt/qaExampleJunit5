package com.qa.testbase.driverfactory;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qa.testbase.TestModesEnum;

public abstract class GenericFactory {
  protected static final Logger logger = LoggerFactory.getLogger(GenericFactory.class);

  protected static final String jenkinsUrl = "http://localhost:5566/wd/hub";

  protected String driverLocation;
  protected TestModesEnum mode;

  protected abstract WebDriver initializeLocalDriver();

  protected abstract WebDriver initializeGridDriver() throws MalformedURLException;

  public WebDriver getDriver() throws MalformedURLException {
    switch (mode) {
      case LOCAL:
        return initializeLocalDriver();
      case QA:
        return initializeGridDriver();
      default:
        throw new IllegalArgumentException("Unsupported test mode: " + mode);
    }
  }

  //different OSs have different maximize implementation (especially MacOS)
  protected void maximize(WebDriver driver) {
    driver.manage().window().maximize();
    logger.info("browser maximized");
  }

  protected LoggingPreferences logTypes() {
    final List<String> logTypes = Collections.singletonList(LogType.BROWSER);

    final LoggingPreferences logs = new LoggingPreferences();
    logTypes.forEach(logType -> logs.enable(logType, Level.ALL));

    return logs;
  }

}