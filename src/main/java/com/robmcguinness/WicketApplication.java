package com.robmcguinness;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.filter.JavaScriptFilteredIntoFooterHeaderResponse;
import org.apache.wicket.markup.html.IHeaderResponseDecorator;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.loader.ClassStringResourceLoader;

import com.robmcguinness.stateless.utils.Validation;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.StartTest.stateless.Start#main(String[])
 * 
 */
public class WicketApplication extends WebApplication {
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {

		if (isDevelopment()) {
			getMarkupSettings().setStripWicketTags(true);
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());
		}

		getResourceSettings().getStringResourceLoaders().add(new ClassStringResourceLoader(Validation.class));

		setHeaderResponseDecorator(new IHeaderResponseDecorator() {

			@Override
			public IHeaderResponse decorate(IHeaderResponse response) {
				return new JavaScriptFilteredIntoFooterHeaderResponse(response, "footerBucket");
			}
		});

	}

	protected boolean isDevelopment() {
		return RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType());
	}

}