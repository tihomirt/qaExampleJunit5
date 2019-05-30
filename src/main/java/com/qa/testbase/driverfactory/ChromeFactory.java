package com.qa.testbase.driverfactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.testbase.TestModesEnum;
import com.qa.utils.WaitConstants;

public class ChromeFactory extends GenericFactory implements DriverInterface {

  public ChromeFactory(final TestModesEnum mode) {
    this.mode = mode;
    this.driverLocation = "drivers/chromedriver.exe";
  }

  @Override
  protected WebDriver initializeLocalDriver() {
    logger.info("initializing local chromedriver...");

    final URL resourceUrl = getClass().getClassLoader().getResource(driverLocation);
    if (resourceUrl == null) {
      throw new InvalidArgumentException("ChromeDriver could not be found on location: "
          + driverLocation);
    }

    final File file = new File(resourceUrl.getFile());
    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
    logger.info("chromedriver location set as system property: {}", file.getAbsolutePath());

    final ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logTypes());
    logger.info("chrome option capabilities set");

    ChromeDriver driver = new ChromeDriver(chromeOptions);
    maximize(driver);
    driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_TIME, TimeUnit.MILLISECONDS);
    return driver;
  }

  protected WebDriver initializeGridDriver() throws MalformedURLException {
    logger.info("initializing grid chromedriver...");
    ChromeOptions chromeOptions = new ChromeOptions();
    WebDriver driver = new RemoteWebDriver(new URL(jenkinsUrl), chromeOptions);
    maximize(driver);
    return driver;
  }
}