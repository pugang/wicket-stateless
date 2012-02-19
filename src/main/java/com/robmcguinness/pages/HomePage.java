package com.robmcguinness.pages;

import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.robmcguinness.panels.Example1Panel;
import com.robmcguinness.panels.Example2Panel;
import com.robmcguinness.panels.Example3Panel;
import com.robmcguinness.panels.Example4Panel;
import com.robmcguinness.stateless.StatelessLink;

@StatelessComponent
public class HomePage extends BaseTemplatePage {

	boolean clicked = false;

	/**
	 * Instantiates a new home page.
	 * 
	 * @param parameters
	 *          the parameters
	 */
	public HomePage(final PageParameters parameters) {
		super(parameters);

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

		add(new StatelessLink<Void>("throwExceptionLink") {

			@Override
			public void onClick() {
				if (!clicked) {
					clicked = true;
					throw new AbortWithHttpErrorCodeException(500, "throw an excpetion and remain stateless");
				}
			}
		});

		add(new StatelessLink<Void>("notFoundLink") {

			@Override
			public void onClick() {
				if (!clicked) {
					clicked = true;
					throw new AbortWithHttpErrorCodeException(404, "page is not found");
				}
			}
		});

	}

}
