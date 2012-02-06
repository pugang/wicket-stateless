package com.robmcguinness.stateless;

import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.StringValue;

import com.robmcguinness.stateless.utils.Parameters;

/**
 * props to <a href="http://letsgetdugg.com/2009/05/27/wicket-stateless-pagination/" >letsgetdugg</a> for example
 * 
 * @author robertmcguinness
 * 
 * @param <T>
 */
public abstract class StatelessAjaxDataView<T> extends DataView<T> {

	public static final long ITEMS_PER_PAGE = 100;
	public static final String PAGE_PARAM = "page";

	private PageParameters params;

	public StatelessAjaxDataView(String id, IDataProvider<T> dataProvider, PageParameters params) {
		this(id, dataProvider, ITEMS_PER_PAGE, params);
	}

	public StatelessAjaxDataView(String id, IDataProvider<T> dataProvider, long itemsPerPage, PageParameters params) {
		super(id, dataProvider, itemsPerPage);

		Args.notNull(params, "params");
		this.params = params;

	}

	protected int getPageNumber(final StringValue _param) {
		int page = 0;
		if (Parameters.isInteger(_param)) {
			page = _param.toInt();
		}
		return page;
	}

	@Override
	protected boolean getStatelessHint() {
		return super.getStatelessHint();
	}

}
