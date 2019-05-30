package com.qa.testbase.driverfactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.testbase.TestModesEnum;
import com.qa.utils.WaitConstants;

public class FirefoxFactory extends GenericFactory implements DriverInterface {

  public FirefoxFactory(final TestModesEnum mode) {
    this.mode = mode;
    this.driverLocation = "drivers/geckodriver.exe";
  }

  @Override
  protected WebDriver initializeLocalDriver() {
    logger.info("initializing local firefox driver...");

    final URL resourceUrl = getClass().getClassLoader().getResource(driverLocation);
    if (resourceUrl == null) {
      throw new InvalidArgumentException("geckodriver could not be found on location: "
          + driverLocation);
    }

    final File file = new File(resourceUrl.getFile());
    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
    logger.info("geckodriver location set as system property: {}", file.getAbsolutePath());
    
    final FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setCapability(CapabilityType.LOGGING_PREFS, logTypes());
    logger.info("firefox option capabilities set");

    FirefoxDriver driver = new FirefoxDriver(firefoxOptions);
    maximize(driver);
    driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_TIME, TimeUnit.MILLISECONDS);
    
    return driver;
  }

  @Override
  protected WebDriver initializeGridDriver() throws MalformedURLException {
    logger.info("initializing grid firefoxdrivre...");
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    WebDriver driver = new RemoteWebDriver(new URL(jenkinsUrl), firefoxOptions);
    maximize(driver);
    return new RemoteWebDriver(new URL(jenkinsUrl), firefoxOptions);
  }

}