package com.robmcguinness;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.caching.NoOpResourceCachingStrategy;
import org.apache.wicket.util.tester.DummyHomePage;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseWicketTester {

	private static final Logger logger = LoggerFactory.getLogger(BaseWicketTester.class);

	protected WicketTester tester;

	/**
	 * Splits the camel case method name into human readable form
	 */
	@Rule
	public MethodRule watchman = new TestWatchman() {

		@Override
		public void starting(FrameworkMethod method) {
			logger.info("{} ", splitCamelCase(method.getName()));
		}
	};

	@Before
	public void createTester() {
		this.tester = new WicketTester(new StatelessWebApplication() {
			@Override
			public Class<? extends WebPage> getHomePage() {
				return DummyHomePage.class;
			}

			@Override
			public RuntimeConfigurationType getConfigurationType() {
				return RuntimeConfigurationType.DEPLOYMENT;
			}

			@Override
			protected void init() {
				super.init();
				getResourceSettings().setCachingStrategy(NoOpResourceCachingStrategy.INSTANCE);
			}
		});
	}

	@After
	public void destroyTester() {
		if (this.tester != null) {
			this.tester.destroy();
		}
	}

	/**
	 * @see #watchman
	 * @param s
	 * @return
	 */
	static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ").toLowerCase();
	}

}
