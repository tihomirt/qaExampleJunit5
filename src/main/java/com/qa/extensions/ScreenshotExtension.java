package com.qa.extensions;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qa.testbase.TestBase;

public class ScreenshotExtension implements AfterTestExecutionCallback, AfterEachCallback {

  private static final Logger logger = LoggerFactory.getLogger(ScreenshotExtension.class);

  @Override
  public void afterTestExecution(ExtensionContext context) throws Exception {
    final TestBase testBase = (TestBase) context.getTestInstance()
        .orElseThrow(() -> new Exception("No test instance in test context."));

    //the test passed
    if (!context.getExecutionException().isPresent()) {
      return;
    }

    logger.debug("test fail! taking screenshot...");
    final File screenshot = ((TakesScreenshot) testBase.getDriver())
        .getScreenshotAs(OutputType.FILE);

    final String className = context.getTestClass()
        .orElseThrow(() -> new Exception("Test class is not in test context.")).getCanonicalName();
    final String methodName = context.getTestMethod()
        .orElseThrow(() -> new Exception("Test method is not in test context.")).getName();

    final String screenshotPath = "." + File.separator + "screenshots" + File.separator + className
        + "-" + methodName + ".png";
    logger.debug("saving screenshot for with path: {}", screenshotPath);

    FileUtils.copyFile(screenshot, new File(screenshotPath));
    logger.info("screenshot created: {}", screenshotPath);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    //TODO investigate
  }

}
