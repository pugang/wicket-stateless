package com.robmcguinness.panels;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestAttributes;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.stateless.StatelessAjaxButton;
import com.robmcguinness.stateless.StatelessAjaxFormComponentUpdatingBehavior;
import com.robmcguinness.stateless.utils.Parameters;

/**
 * Various stateless form ajax behaviors.
 * 
 * @author robertmcguinness
 * 
 */
public class Example2Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example2Panel.class);

	public Example2Panel(String id) {
		super(id);

		String _firstName = Parameters.getParam("firstName");
		String _lastName = Parameters.getParam("lastName");

		final TextField<String> firstName = new TextField<String>("firstName", new Model<String>(_firstName));
		firstName.setMarkupId(firstName.getId());
		final TextField<String> lastName = new TextField<String>("lastName", new Model<String>(_lastName));
		lastName.setMarkupId(lastName.getId());

		final Form<String> form = new StatelessForm<String>("inputForm");
		form.setMarkupId(form.getId());

		form.add(new StatelessAjaxButton("inputFormButton", form) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				PageParameters params = target.getPageParameters();
				logger.debug("target pageParamters {}", params);
				target.add(getParent().get("yourName"));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				PageParameters params = target.getPageParameters();
				logger.debug("pageParamters {}", params);

			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				attributes.getDynamicExtraParameters().add("return Wicket.Form.serializeElement('" + firstName.getMarkupId() + "')");
				attributes.getDynamicExtraParameters().add("return Wicket.Form.serializeElement('" + lastName.getMarkupId() + "')");

			}

		});

		Label yourNameLabel = null;
		form.add(yourNameLabel = new Label("yourName", new AbstractReadOnlyModel<String>() {

			@Override
			public String getObject() {
				AjaxRequestTarget target = AjaxRequestTarget.get();
				if (target != null) {

					logger.debug("pageParamters {}", getPage().getPageParameters());
					String fName = Parameters.getParam("firstName");
					String lName = Parameters.getParam("lastName");
					if (fName != null && lName != null)
						return fName + lName;
				}

				return null;
			}

		}) {
			@Override
			public boolean isVisible() {
				return getDefaultModelObject() != null;
			}
		});
		yourNameLabel.setMarkupId(yourNameLabel.getId());
		yourNameLabel.setOutputMarkupPlaceholderTag(true);

		final DropDownChoice<String> preference = new DropDownChoice<String>("preference", new Model<String>("Tebowing"), Arrays.asList(new String[] { "Tebowing", "Gronking", "Other" }));
		preference.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected PageParameters getPageParameters() {
				return new PageParameters();
			}

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				final String value = preference.getModelObject();
				logger.debug("pageParamters {}", getPageParameters());
			}
		});
		preference.setMarkupId(preference.getId());
		form.add(firstName);
		form.add(preference);
		form.add(lastName);
		add(form);

	}

}
