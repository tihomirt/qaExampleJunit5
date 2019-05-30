package com.qa.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qa.testbase.TestBase;

public class CloseDriverExtension implements AfterEachCallback {

  private static final Logger logger = LoggerFactory.getLogger(CloseDriverExtension.class);

  //the WebDriver is closed in an AfterEachCallback to enable additional operations if needed
  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    final TestBase testBase = (TestBase) context.getTestInstance()
        .orElseThrow(() -> new Exception("No test instance in test context."));

    testBase.getDriver().quit();
    logger.info("Driver closed");
  }

}
