package com.qa.testbase.driverfactory;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

public interface DriverInterface {
  WebDriver getDriver() throws MalformedURLException;
}