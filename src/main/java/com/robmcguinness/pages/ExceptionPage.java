package com.robmcguinness.pages;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ExceptionPage extends BaseTemplatePage {
	public ExceptionPage(PageParameters params) {
		super(params);
	}

	@Override
	protected void setHeaders(final WebResponse response) {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	@Override
	public boolean isVersioned() {
		return false;
	}

	@Override
	public boolean isErrorPage() {
		return true;
	}
}
