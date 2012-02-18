package com.robmcguinness;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.robmcguinness.pages.HomePage;

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
