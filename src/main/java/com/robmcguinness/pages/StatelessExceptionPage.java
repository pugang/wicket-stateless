package com.robmcguinness.pages;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StatelessExceptionPage extends BasePage {
	public StatelessExceptionPage(PageParameters params) {
		super(params);
	}

	@Override
	protected void setHeaders(final WebResponse response) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
