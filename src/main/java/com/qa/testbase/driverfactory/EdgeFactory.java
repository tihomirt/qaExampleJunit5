package com.qa.testbase.driverfactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qa.testbase.TestModesEnum;
import com.qa.utils.WaitConstants;

public class EdgeFactory extends GenericFactory implements DriverInterface {
  
  private static final String defaultBuildNumber = "17134";

  public EdgeFactory(final TestModesEnum mode) {
    this.mode = mode;
    this.driverLocation = "drivers/MicrosoftWebDriver-%s.exe";
  }

  @Override
  protected WebDriver initializeLocalDriver() {
    logger.info("initializing local edgedriver...");
    final String location = String.format(driverLocation, getWindowsBuildNumber());


    final ClassLoader classLoader = getClass().getClassLoader();
    final URL resourceUrl = classLoader.getResource(location);
    if (resourceUrl == null) {
      throw new InvalidArgumentException(
          "MicrosoftWebDriver could not be found on location: " + location);
    }
    final File file = new File(resourceUrl.getFile());
    System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, file.getAbsolutePath());
    logger.info("edgedriver location set as system property: {}", file.getAbsolutePath());

    final EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setCapability("InPrivate", true);
    edgeOptions.setCapability(CapabilityType.LOGGING_PREFS, logTypes());
    logger.info("edge option capabilities set");

    final EdgeDriver driver = new EdgeDriver(edgeOptions);
    maximize(driver);
    driver.manage().timeouts().implicitlyWait(WaitConstants.IMPLICIT_WAIT_TIME, TimeUnit.MILLISECONDS);
    
    return driver;
  }

  @Override
  protected WebDriver initializeGridDriver() throws MalformedURLException {
    logger.info("initializing drig edgedriver...");
    final EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setCapability("InPrivate", true);
    WebDriver driver = new RemoteWebDriver(new URL(jenkinsUrl), edgeOptions);
    maximize(driver);
    return driver;
  }
  
  private String getWindowsBuildNumber() {
    String buildNumber = defaultBuildNumber;

    final ProcessBuilder builder = new ProcessBuilder("reg", "query",
        "HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion");

    Process reg;
    try {
      reg = builder.start();
    } catch (IOException e1) {
      logger.info("Failed to get build number from registry, using default value: {}", buildNumber);
      return buildNumber;
    }

    try (BufferedReader output = new BufferedReader(new InputStreamReader(reg.getInputStream()))) {
      Optional<String> key =
          output.lines().filter(l -> l.contains("CurrentBuildNumber")).findFirst();
      buildNumber = key.isPresent() ? key.get().replaceAll("\\D+", "") : buildNumber;
      reg.destroy();
    } catch (IOException e) {
      logger.info("Failed to get build number from registry, using default value: {}", buildNumber);
    }

    logger.info("creating edge driver for version: {}", buildNumber);
    return buildNumber;
  }
}