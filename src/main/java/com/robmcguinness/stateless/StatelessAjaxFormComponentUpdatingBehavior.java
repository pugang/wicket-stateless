/**
 *
 */
package com.robmcguinness.stateless;

import static com.robmcguinness.stateless.StatelessEncoder.mergeParameters;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jfk
 * 
 */
public abstract class StatelessAjaxFormComponentUpdatingBehavior extends AjaxFormComponentUpdatingBehavior {

	private PageParameters params;

	/**
	 * @param event
	 */
	public StatelessAjaxFormComponentUpdatingBehavior(final String event) {
		super(event);
	}

	/**
	 * 
	 * @see AbstractAjaxBehavior#getCallbackUrl()
	 */
	@Override
	public CharSequence getCallbackUrl() {

		final Url url = Url.parse(super.getCallbackUrl().toString());

		return mergeParameters(url, getPageParameters()).toString();
	}

	protected PageParameters getPageParameters() {
		return params;
	}

	public void setParams(PageParameters params) {
		this.params = params;
	}

	/**
	 * @return always {@literal true}
	 */
	@Override
	public boolean getStatelessHint(final Component component) {
		return true;
	}
}
