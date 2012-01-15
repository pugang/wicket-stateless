package com.robmcguinness;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.jolira.stateless.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		getMarkupSettings().setStripWicketTags(true);
		getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());
	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEVELOPMENT;
	}

}