package com.robmcguinness.pages;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.robmcguinness.pages.HomePage;
import com.robmcguinness.utils.BaseWicketTester;

/**
 * Simple test using the WicketTester
 */
public class HomePageTest extends BaseWicketTester {

	@Test
	public void testHomePageIsStateless() {
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);

		HomePage homePage = (HomePage) tester.getLastRenderedPage();
		assertTrue(homePage.isPageStateless());
	}

}
