package com.robmcguinness;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

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
		
		if(isDevelopment()) {
			getMarkupSettings().setStripWicketTags(true);
			getDebugSettings().setDevelopmentUtilitiesEnabled(true);
			getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());
		}
		
	}
	
	protected boolean isDevelopment() {
		return RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType());
	}
	
}