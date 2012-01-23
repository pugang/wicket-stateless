package com.robmcguinness.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;

public class FieldDecorator extends Behavior {
	
	@Override
	public void bind(Component component) {
	  component.setOutputMarkupId(true);
	}
	
	@Override
	public void beforeRender(Component component) {
	  super.beforeRender(component);
	}

}
