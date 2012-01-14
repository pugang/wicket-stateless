package com.robmcguinness;

import java.util.Arrays;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.resource.header.CssHeaderItem;
import org.apache.wicket.util.string.StringValue;

import com.robmcguinness.models.SessionModel;
import com.robmcguinness.stateless.StatelessAjaxFallbackLink;
import com.robmcguinness.stateless.StatelessAjaxFormComponentUpdatingBehavior;
import com.robmcguinness.stateless.StatelessLink;

// TODO: Auto-generated Javadoc
/**
 * For testing only.
 */
@StatelessComponent
public class HomePage extends WebPage {

	/** The Constant COUNTER_PARAM. */
	private static final String COUNTER_PARAM = "counter";

	private Label sessionLabel;
	private StatelessLink<Void> plusLink;
	private StatelessLink<Void> minusLink;

	/**
	 * Instantiates a new home page.
	 * 
	 * @param parameters the parameters
	 */
	public HomePage(final PageParameters parameters) {

		add(sessionLabel = new Label("sessionLabel", new SessionModel()));
		sessionLabel.setMarkupId("sessionLabel");

		final Label counterLabel = new Label("counterLabel", new AbstractReadOnlyModel<Integer>() {

			@Override
			public Integer getObject() {
				final String _counter = getParameter(parameters, COUNTER_PARAM);
				final int counter = _counter != null ? Integer.parseInt(_counter) : 0;
				return counter;
			}

		});

		counterLabel.setMarkupId(counterLabel.getId()); // Required to make stateless Ajax work
		counterLabel.setOutputMarkupId(true);

		plusLink = new StatelessAjaxFallbackLink<Void>("plusLink", null, parameters) {

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (target != null) {
					Integer counter = (Integer) counterLabel.getDefaultModelObject();
					updateCounter(getPageParameters(), counter, true);
					target.add(counterLabel);
					target.add(this);
				}
			}
		};

		minusLink = new StatelessAjaxFallbackLink<Void>("minusLink", null, parameters) {

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (target != null) {
					Integer counter = (Integer) counterLabel.getDefaultModelObject();
					updateCounter(getPageParameters(), counter, false);
					target.add(counterLabel);
					target.add(this);
				}
			}
		};

		add(plusLink);
		add(minusLink);
		add(counterLabel);

		final String _a = getParameter(parameters, "a");
		final String _b = getParameter(parameters, "b");
		final Form<String> form = new StatelessForm<String>("inputForm") {

			@Override
			protected void onSubmit() {
				System.out.format("clicked sumbit: a = [%s], b = [%s]%n", getParameter(parameters, "a"), getParameter(parameters, "b"));
			}

		};
		final TextField<String> a = new TextField<String>("a", new Model<String>(_a));
		final TextField<String> b = new TextField<String>("b", new Model<String>(_b));
		final DropDownChoice<String> c = new DropDownChoice<String>("c", new Model<String>("2"), Arrays.asList(new String[] { "1", "2", "3" }));

		c.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected PageParameters getPageParameters() {
				return new PageParameters();
			}

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				final String value = c.getModelObject();
				System.out.println("value[" + value + "]");
			}
		});
		c.setMarkupId("c");
		form.add(a);
		form.add(b);
		add(form);

		add(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#renderHead(org.apache.wicket.markup.html.IHeaderResponse)
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forReference(new CssResourceReference(HomePage.class, "bootstrap.min.css")));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.wicket.Component#onEvent(org.apache.wicket.event.IEvent)
	 */
	@Override
	public void onEvent(IEvent<?> event) {
		// update the session label on every ajax request
		if (event.getPayload() instanceof AjaxRequestTarget) {
			SessionModel model = (SessionModel) sessionLabel.getDefaultModel();
			if (model.isStateless()) {
				sessionLabel.add(new AttributeModifier("class", new Model<String>("alert-message success session")));
			} else {
				sessionLabel.add(new AttributeModifier("class", new Model<String>("alert-message error session")));
			}
			((AjaxRequestTarget) event.getPayload()).add(sessionLabel);
		}
	}

	/**
	 * Gets the parameter.
	 * 
	 * @param parameters the parameters
	 * @param key the key
	 * @return the parameter
	 */
	private String getParameter(final PageParameters parameters, final String key) {
		final StringValue value = parameters.get(key);

		if (value.isNull() || value.isEmpty()) {
			return null;
		}

		return value.toString();
	}

	/**
	 * Update counter.
	 * 
	 * @param pageParameters the page parameters
	 * @param counter the counter
	 * @param add the add
	 */
	protected final void updateCounter(final PageParameters pageParameters, int counter, boolean add) {
		pageParameters.set(COUNTER_PARAM, Integer.toString(add ? counter + 1 : counter - 1));

		if (add)
			minusLink.setPageParameters(pageParameters);
		else
			plusLink.setPageParameters(pageParameters);

	}
}
