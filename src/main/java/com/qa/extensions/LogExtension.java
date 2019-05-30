package com.qa.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.MDC;

public class LogExtension implements BeforeEachCallback,
    AfterEachCallback, BeforeAllCallback, AfterAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    final String className = context.getTestClass()
        .orElseThrow(() -> new Exception("Test class is not in test context.")).getCanonicalName();
    MDC.put("methodName", className);
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    final String className = context.getTestClass()
        .orElseThrow(() -> new Exception("Test class is not in test context.")).getCanonicalName();
    final String methodName = context.getTestMethod()
        .orElseThrow(() -> new Exception("Test method is not in test context.")).getName();

    MDC.remove("methodName");
    MDC.put("methodName", className + "-" + methodName);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    //TODO check this
    MDC.remove("methodName");
    MDC.put("methodName", context.getTestClass().get().getCanonicalName());
  }

  @Override
  public void afterAll(ExtensionContext context) {
    MDC.remove("methodName");
  }


}
