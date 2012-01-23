package com.robmcguinness.stateless.utils;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.lang.Args;

public class HTML {
	
	private HTML() {}
	
	public static AttributeAppender maxLength(Integer maxLength) {
		Args.notNull(maxLength, "maxLength");
		return new AttributeAppender("maxlength", new Model<Integer>(maxLength));
	}
}
