package com.robmcguinness.stateless;

import org.apache.wicket.ajax.AjaxRequestAttributes;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;

public abstract class StatelessAjaxButton extends Button {

	private final Form<?> form;
	private PageParameters params;

	public StatelessAjaxButton(String id, PageParameters params) {
		this(id, null, null, params);
	}

	public StatelessAjaxButton(String id, IModel<String> model, PageParameters params) {
		this(id, model, null, params);

	}

	public StatelessAjaxButton(String id, Form<?> form) {
		this(id, null, form, null);
	}

	public StatelessAjaxButton(String id, Form<?> form, PageParameters params) {
		this(id, null, form, params);
	}

	public StatelessAjaxButton(String id, IModel<String> model, Form<?> form, PageParameters params) {
		super(id, model);
		this.form = form;
		this.params = params;

		add(new StatelessAjaxFormSubmitBehavior(form, "click") {

			@Override
			protected PageParameters getPageParameters() {
				return StatelessAjaxButton.this.getPageParameters();
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target) {
				StatelessAjaxButton.this.onSubmit(target, StatelessAjaxButton.this.getForm());

			}

			@Override
			protected void onError(AjaxRequestTarget target) {
				StatelessAjaxButton.this.onError(target, StatelessAjaxButton.this.getForm());

			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				StatelessAjaxButton.this.updateAjaxAttributes(attributes);
			}

			@Override
			protected CharSequence getEventHandler() {
				final String script = StatelessAjaxButton.this.getOnClickScript();

				AppendingStringBuffer handler = new AppendingStringBuffer();

				if (!Strings.isEmpty(script)) {
					handler.append(script).append(";");
				}

				handler.append(super.getEventHandler());
				handler.append("; return false;");
				return handler;
			}

		});
	}

	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
	}

	@Override
	protected boolean getStatelessHint() {
		return true;
	}

	public PageParameters getPageParameters() {
		return params;
	}

	/**
	 * Listener method invoked on form submit with no errors
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onSubmit(AjaxRequestTarget target, Form<?> form);

	/**
	 * Listener method invoked on form submit with errors
	 * 
	 * @param target
	 * @param form
	 */
	protected abstract void onError(AjaxRequestTarget target, Form<?> form);

	/**
	 * Returns the form if it was set in constructor, otherwise returns the form nearest in parent hierarchy.
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponent#getForm()
	 */
	@Override
	public Form<?> getForm() {
		if (form != null) {
			return form;
		} else {
			return super.getForm();
		}
	}

}
