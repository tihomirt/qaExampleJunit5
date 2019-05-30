package com.example.qa.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.qa.pageobjects.AdoptOpenJdkPage;
import com.qa.pageobjects.DuckDuckGoLandingPage;
import com.qa.pageobjects.DuckDuckGoResultsPage;
import com.qa.testbase.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTest extends TestBase {

	private DuckDuckGoLandingPage duckDuckGoPage;
	
	public FirstTest() throws Exception {
		super();
	}

	@BeforeEach
	public void before() {
		duckDuckGoPage = new DuckDuckGoLandingPage(driver, getBrowser());		
	}

	/**
	 * Test autocomplete
	 * 1. Load google.com
	 * 2. Search for adoptOpendJdk
	 * 3. Click on the first result
	 * 4. Test the url of the page
	 * 5. Print the latest jdk 8 version
	 */
	@Test
	public void searchForAdoptOpenJDK() {
		final String searchQuery = "adoptOpenJdk";
		final String resultUrl = "https://adoptopenjdk.net/";
		
		DuckDuckGoResultsPage resultsPage = duckDuckGoPage.fillSearchFieldAndClickEnter(searchQuery);
		AdoptOpenJdkPage openJdkPage = resultsPage.clickFirstLink();
		
		assertEquals(resultUrl, openJdkPage.getUrl());
		
		System.out.println(openJdkPage.getVersion());
	}
}
