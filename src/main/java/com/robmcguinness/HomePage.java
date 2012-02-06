package com.robmcguinness;

import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.robmcguinness.assets.Assets;
import com.robmcguinness.panels.Example1Panel;
import com.robmcguinness.panels.Example2Panel;
import com.robmcguinness.panels.Example3Panel;
import com.robmcguinness.panels.Example4Panel;
import com.robmcguinness.panels.HeaderPanel;
import com.robmcguinness.stateless.StatelessLink;

@StatelessComponent
public class HomePage extends WebPage {

	/**
	 * Instantiates a new home page.
	 * 
	 * @param parameters
	 *          the parameters
	 */
	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new HeaderPanel("topBar").setRenderBodyOnly(true));
		add(new Example1Panel("example1").setRenderBodyOnly(true));
		add(new Example2Panel("example2").setRenderBodyOnly(true));
		add(new Example3Panel("example3").setRenderBodyOnly(true));
		add(new Example4Panel("example4", parameters).setRenderBodyOnly(true));

		add(new StatelessLink<Void>("createSessionLink") {

			@Override
			public void onClick() {
				getSession().bind();
			}
		});

		add(new HeaderResponseContainer("footerBucket", "footerBucket"));

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
