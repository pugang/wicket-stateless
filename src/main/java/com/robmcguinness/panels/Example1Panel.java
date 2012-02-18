package com.robmcguinness.panels;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.stateless.StatelessAjaxFallbackLink;
import com.robmcguinness.stateless.StatelessLabel;
import com.robmcguinness.stateless.StatelessLink;
import com.robmcguinness.stateless.utils.Javascript;
import com.robmcguinness.stateless.utils.Parameters;

/**
 * Panel that demonstrates {@link StatelessAjaxFallbackLink}
 * 
 * @author robertmcguinness
 * 
 */
public class Example1Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example1Panel.class);

	private static final String COUNTER = "counter"; // id and param
	private int counter;

	private final CounterLabel counterLabel;

	public Example1Panel(String id) {
		super(id);

		PageParameters params = init();

		counterLabel = new CounterLabel(COUNTER, new CounterModel());
		counterLabel.setOutputMarkupId(true);
		add(counterLabel);

		StatelessLink<Void> plusLink = new StatelessAjaxFallbackLink<Void>("plusLink", null, params) {

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (target != null) {
					logger.debug("plusLink pageParamters {}", getPageParameters());
					updateCounter(getPageParameters(), true);
				}
			}

			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof CounterPayload) {
					CounterPayload _payload = (CounterPayload) event.getPayload();
					setPageParameters(_payload.getParams());
					updateAjaxCounterComponent(this);
				}
			}
		};
		add(plusLink);

		StatelessLink<Void> minusLink = new StatelessAjaxFallbackLink<Void>("minusLink", null, params) {

			@Override
			public void onClick(final AjaxRequestTarget target) {
				if (target != null) {
					logger.debug("minusLink pageParamters {}", getPageParameters());
					updateCounter(getPageParameters(), false);
				}
			}

			@Override
			public void onEvent(IEvent<?> event) {
				// if counter has been updated and source of event is not this component
				if (event.getPayload() instanceof CounterPayload) {
					CounterPayload _payload = (CounterPayload) event.getPayload();
					setPageParameters(_payload.getParams());
					updateAjaxCounterComponent(this);
				}
			}
		};
		add(minusLink);

	}

	/**
	 * Initializes counter value from request parameters (if applicable) and creates a subset of these parameters for use with the components on this
	 * page.
	 */
	private PageParameters init() {
		counter = 0;
		StringValue _counter = Parameters.getParameter(COUNTER);
		if (Parameters.isInteger(_counter)) {
			counter = _counter.toInt();
		}

		return new Parameters<Example1Panel>().encodePageParameters(this, COUNTER);
	}

	private class CounterLabel extends StatelessLabel {

		public CounterLabel(String id, IModel<?> model) {
			super(id, model);
		}

		@Override
		public void onEvent(IEvent<?> event) {
			if (event.getPayload() instanceof CounterPayload) {

				AjaxRequestTarget target = this.getRequestCycle().find(AjaxRequestTarget.class);
				if (target != null) {
					updateAjaxCounterComponent(this);
					target.appendJavaScript(Javascript.highlight(this));
				}
			}
		}

	}

	/**
	 * Update counter.
	 * 
	 * @param pageParameters
	 *          the page parameters
	 * @param counter
	 *          the counter
	 * @param add
	 *          the add
	 */
	protected final void updateCounter(final PageParameters pageParameters, boolean add) {
		counter = add ? counter + 1 : counter - 1;
		pageParameters.set(COUNTER, counter);
		send(getPage(), Broadcast.BREADTH, new CounterPayload(pageParameters));
	}

	private void updateAjaxCounterComponent(Component component) {
		AjaxRequestTarget target = component.getRequestCycle().find(AjaxRequestTarget.class);
		if (target != null) {
			target.add(component);
		}
	}

	private class CounterModel extends AbstractReadOnlyModel<Integer> {

		@Override
		public Integer getObject() {
			return counter;
		}

	}

	public static class CounterPayload {
		private final PageParameters params;

		public CounterPayload(PageParameters params) {
			this.params = params;
		}

		public PageParameters getParams() {
			return params;
		}

	}

}
