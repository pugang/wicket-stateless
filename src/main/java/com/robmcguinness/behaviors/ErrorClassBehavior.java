package com.robmcguinness.behaviors;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 * Taken from <a href="https://www.packtpub.com/apache-wicket-cookbook/book#chapter_3">Wicket Cookbook: "Making Forms more Presentable"</a> 
 * @author robertmcguinness
 *
 */
public class ErrorClassBehavior extends Behavior {
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		 if (((FormComponent<?>) component).isValid() == false) {
			 String cl = tag.getAttribute("class");
	      if (cl == null) {
	         tag.put("class", "error");
	      } else {
	         tag.put("class", cl + "error ");
	      }
		 }
	}
}
