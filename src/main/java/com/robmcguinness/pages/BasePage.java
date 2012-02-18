package com.robmcguinness.pages;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.robmcguinness.assets.Assets;

public class BasePage extends WebPage {

	public BasePage(PageParameters params) {
		super(params);
	}

	@Override
	public void renderHead(IHeaderResponse response) {

		response.render(CssHeaderItem.forUrl("http://fonts.googleapis.com/css?family=Arvo:400,700"));
		response.render(CssHeaderItem.forReference(new CssResourceReference(Assets.class, "bootstrap.min.css")));
		response.render(CssHeaderItem.forReference(new CssResourceReference(Assets.class, "stateless.css")));

		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(Assets.class, "bootstrap.min.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(Assets.class, "stateless.js")));
		response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(Assets.class, "jquery.timeago.js")));

	}
}
