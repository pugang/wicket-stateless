package com.robmcguinness;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebResponse;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;
import org.apache.wicket.settings.IExceptionSettings;

import com.robmcguinness.pages.HomePage;
import com.robmcguinness.pages.NotFoundPage;
import com.robmcguinness.pages.StatelessExceptionPage;
import com.robmcguinness.stateless.utils.Validation;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.StartTest.stateless.Start#main(String[])
 * 
 */
public class StatelessWebApplication extends WebApplication {
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {

		initDebugSettings();
		initExceptionSettings();
		initResources();
		initMountPage();

	}

	protected void initMountPage() {
		mountPage("/500", StatelessExceptionPage.class);
		mountPage("/404", NotFoundPage.class);
	}

	protected void initDebugSettings() {
		if (isDevelopment()) {
			getMarkupSettings().setStripWicketTags(true);
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());
		}
	}

	protected void initExceptionSettings() {
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
		getApplicationSettings().setInternalErrorPage(StatelessExceptionPage.class);
	}

	protected void initResources() {
		getResourceSettings().getStringResourceLoaders().add(new ClassStringResourceLoader(Validation.class));

		setHeaderResponseDecorator(new IHeaderResponseDecorator() {

			@Override
			public IHeaderResponse decorate(IHeaderResponse response) {
				return new JavaScriptFilteredIntoFooterHeaderResponse(response, "footerBucket");
			}
		});
	}

	/**
	 * Don't append <code>jsessionid</code> since application should be in stateless mode and not require a session
	 */
	@Override
	protected WebResponse newWebResponse(final WebRequest webRequest, HttpServletResponse httpServletResponse) {
		return new ServletWebResponse((ServletWebRequest) webRequest, httpServletResponse) {

			@Override
			public String encodeURL(CharSequence url) {
				return url.toString();
			}

			@Override
			public String encodeRedirectURL(CharSequence url) {
				return encodeURL(url);
			}
		};
	}

	protected boolean isDevelopment() {
		return RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType());
	}

}