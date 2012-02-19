package com.robmcguinness.pages;

import org.apache.wicket.markup.head.filter.HeaderResponseContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.robmcguinness.panels.HeaderPanel;

public class BaseTemplatePage extends BasePage {

	public BaseTemplatePage(PageParameters params) {
		super(params);

		add(new HeaderPanel("topBar").setRenderBodyOnly(true));

		add(new HeaderResponseContainer("footerBucket", "footerBucket"));
	}

}
