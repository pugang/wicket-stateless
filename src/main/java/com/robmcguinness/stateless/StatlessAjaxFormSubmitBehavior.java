package com.robmcguinness.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class StatlessAjaxFormSubmitBehavior extends AjaxFormSubmitBehavior {

	/**
	 * should never be accessed directly (thus the __ cause its overkill to create a super class), instead always use #getForm()
	 */
	private Form<?> __form;

	public StatlessAjaxFormSubmitBehavior(String event) {
		this(null, event);
	}

	public StatlessAjaxFormSubmitBehavior(Form<?> form, String event) {
		super(event);

		if (form != null) {
			form.setOutputMarkupId(true);
		}
	}

	@Override
	public boolean getStatelessHint(Component component) {
		return true;
	}

	/**
	 * @see org.apache.wicket.ajax.AjaxEventBehavior#onEvent(org.apache.wicket.ajax.AjaxRequestTarget)
	 */
	@Override
	protected void onEvent(final AjaxRequestTarget target) {
		getForm().getRootForm().onFormSubmitted(new IFormSubmitter() {
			@Override
			public Form<?> getForm() {
				return StatlessAjaxFormSubmitBehavior.this.getForm();
			}

			@Override
			public boolean getDefaultFormProcessing() {
				return StatlessAjaxFormSubmitBehavior.this.getDefaultProcessing();
			}

			@Override
			public void onSubmit() {
				StatlessAjaxFormSubmitBehavior.this.onSubmit(target);
			}

			@Override
			public void onError() {
				StatlessAjaxFormSubmitBehavior.this.onError(target);
			}
		});

	}

	protected abstract PageParameters getPageParameters();

}