package com.robmcguinness.stateless;

/**
 *
 */

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Just like {@link AjaxFallbackLink}, but stateless.
 * 
 * @author jfk
 * 
 */
public abstract class StatelessAjaxFallbackLink<T> extends StatelessLink<T> implements IAjaxLink {

	public StatelessAjaxFallbackLink(final String id) {
		this(id, null, null);
	}

	public StatelessAjaxFallbackLink(final String id, final IModel<T> model) {
		this(id, model, null);
	}

	public StatelessAjaxFallbackLink(final String id, final IModel<T> model, final PageParameters params) {
		super(id, model, params);

		add(new StatelessAjaxEventBehavior("onclick") {

			@Override
			protected PageParameters getPageParameters() {
				return StatelessAjaxFallbackLink.this.getPageParameters();
			}

			@Override
			protected void onComponentTag(final ComponentTag tag) {
				// only render handler if link is enabled
				if (isLinkEnabled()) {
					super.onComponentTag(tag);
				}
			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
			}

			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				onClick(target);
				target.add(StatelessAjaxFallbackLink.this);
			}
		});
	}

	public StatelessAjaxFallbackLink(final String id, final PageParameters params) {
		this(id, null, params);
	}

	/**
	 * @see Link#onClick()
	 */
	@Override
	public final void onClick() {
		onClick(null);
	}

	/**
	 * Callback for the onClick event. If ajax failed and this event was generated
	 * via a normal link the target argument will be null
	 * 
	 * @param target
	 *          ajax target if this linked was invoked using ajax, null otherwise
	 */
	@Override
	public abstract void onClick(final AjaxRequestTarget target);
}
