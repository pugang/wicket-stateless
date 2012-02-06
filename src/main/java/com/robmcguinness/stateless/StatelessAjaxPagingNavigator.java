package com.robmcguinness.stateless;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StatelessAjaxPagingNavigator extends AjaxPagingNavigator {

	private final PageParameters params;

	public StatelessAjaxPagingNavigator(String id, IPageable pageable, PageParameters params) {
		this(id, pageable, null, params);
	}

	public StatelessAjaxPagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider, PageParameters params) {
		super(id, pageable, labelProvider);

		this.params = params;
	}

}
