package com.robmcguinness.stateless.utils;

import org.apache.wicket.Component;

public class Javascript {
	
	private Javascript() {}
	
	/**
	 * Adds a highlighting effect to a component when replaced in an ajax callback by triggering a 
	 * custom event 'ajax.highlight' in the javascript layer
	 * @return
	 */
	public static String highlight(Component component) {
		String handler = 
				"$('body').triggerHandler({" +
						"type: 'ajax.highlight'," +
						"target: '"+component.getId()+"'"+ 
				"});";
		return handler;
	}
}
