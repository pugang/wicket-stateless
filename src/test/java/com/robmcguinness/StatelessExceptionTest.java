package com.robmcguinness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.wicket.Page;
import org.apache.wicket.settings.IExceptionSettings;
import org.junit.Test;

import com.robmcguinness.stateless.StatelessAjaxFallbackLink;

public class StatelessExceptionTest extends BaseWicketTester {

	@Test
	public void shouldThrowApplicationInStatefulModeWhenExceptionOccurs() {
		tester.startPage(ExceptionPage.class);
		tester.getApplication().getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_EXCEPTION_PAGE);
		tester.setExposeExceptions(false);

		StatelessAjaxFallbackLink<?> link = (StatelessAjaxFallbackLink<?>) tester.getComponentFromLastRenderedPage("link");
		tester.executeAjaxEvent(link, "onclick");
		Page lastPage = tester.getLastRenderedPage();

		assertFalse(lastPage.isStateless());
	}

	@Test
	public void shouldRemainStatelessWhenExceptionOccurs() {
		tester.startPage(ExceptionPage.class);

		tester.getApplication().getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
		tester.setExposeExceptions(false);

		StatelessAjaxFallbackLink<?> link = (StatelessAjaxFallbackLink<?>) tester.getComponentFromLastRenderedPage("link");
		tester.executeAjaxEvent(link, "onclick");
		assertEquals(500, tester.getLastResponse().getStatus());

		Page lastPage = tester.getLastRenderedPage();
		assertTrue(lastPage.isStateless());

	}

}
