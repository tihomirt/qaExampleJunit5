package com.qa.testbase;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.qa.extensions.CloseDriverExtension;
import com.qa.extensions.LogExtension;
import com.qa.extensions.ScreenshotExtension;
import com.qa.testbase.driverfactory.DriverFactory;
import com.qa.testbase.driverfactory.DriverInterface;

@ExtendWith(LogExtension.class)
@ExtendWith(CloseDriverExtension.class)
@ExtendWith(ScreenshotExtension.class)
@RunWith(JUnitPlatform.class)
public class TestBase {

  protected static final Logger logger = LoggerFactory.getLogger(TestBase.class);

  private static TestModesEnum mode;
  private static BrowsersEnum browser;

  protected WebDriver driver;

  //load the config parameters in static block
  static {
    MDC.put("methodName", "TestBase-static");

    mode = getTestModeProperty(System.getProperty("mode"));
    browser = getBrowserTypeProperty(System.getProperty("browser"));

    logger.info("Mode set to: " + mode);
    logger.info("Browser set to: " + browser);

    MDC.remove("methodName");
  }

  public TestBase() throws Exception {
    DriverFactory driverFactory = new DriverFactory();
    DriverInterface driverInterface = driverFactory.createDriver(mode, browser);
    this.driver = driverInterface.getDriver();
  }

  public WebDriver getDriver() {
    return driver;
  }
  
  public BrowsersEnum getBrowser() {
    return browser;
  }

  private static TestModesEnum getTestModeProperty(String testModeProperty)
      throws IllegalArgumentException {
    if (testModeProperty == null || testModeProperty.isEmpty()) {
      logger.info("Test mode was null. Setting LOCAL mode.");
      return TestModesEnum.LOCAL;
    }

    TestModesEnum mode = TestModesEnum.valueOf(testModeProperty.toUpperCase());
    logger.info("Test mode identified: {},", mode);
    return mode;
  }

  private static BrowsersEnum getBrowserTypeProperty(String browserProperty)
      throws IllegalArgumentException {
    if (browserProperty == null || browserProperty.isEmpty()) {
      logger.info("Browser was null. Setting chrome as browser.");
      return BrowsersEnum.FIREFOX;
    }

    BrowsersEnum browser = BrowsersEnum.valueOf(browserProperty.toUpperCase());
    logger.info("Browser identified: {},", mode);
    return browser;
  }

}
