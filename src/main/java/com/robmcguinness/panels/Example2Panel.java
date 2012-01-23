package com.robmcguinness.panels;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestAttributes;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.parse.metapattern.MetaPattern;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robmcguinness.behaviors.ErrorClassBehavior;
import com.robmcguinness.stateless.StatelessAjaxButton;
import com.robmcguinness.stateless.StatelessAjaxFormComponentUpdatingBehavior;
import com.robmcguinness.stateless.StatelessLabel;
import com.robmcguinness.stateless.utils.HTML;
import com.robmcguinness.stateless.utils.Javascript;
import com.robmcguinness.stateless.utils.Parameters;

/**
 * Various stateless form ajax behaviors.
 * 
 * @author robertmcguinness
 * 
 */
public class Example2Panel extends Panel {

	private static final Logger logger = LoggerFactory.getLogger(Example2Panel.class);
	private transient User user;
	private StatelessLabel yourNameLabel;

	public Example2Panel(String id) {
		super(id);

		user = new User();
		// hydrate user object from page parameters
		user.setFirstName(Parameters.getParam("firstName"));
		user.setLastName(Parameters.getParam("lastName"));

		final TextField<String> firstName = new TextField<String>("firstName", new PropertyModel<String>(this, "user.firstName"));
		firstName.setMarkupId(firstName.getId());
		firstName.add(HTML.maxLength(20));
		firstName.setRequired(true);
		firstName.add(new PatternValidator(MetaPattern.STRING));

		final TextField<String> lastName = new TextField<String>("lastName", new PropertyModel<String>(this, "user.lastName"));
		lastName.setMarkupId(lastName.getId());
		lastName.add(HTML.maxLength(20));
		firstName.add(new PatternValidator(MetaPattern.STRING));
		lastName.setRequired(true);

		final Form<String> inputForm = new StatelessForm<String>("inputForm");
		inputForm.setMarkupId(inputForm.getId());

		inputForm.add(new StatelessAjaxButton("inputFormButton", inputForm) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				PageParameters params = target.getPageParameters();
				logger.debug("onSubmit pageParamters {}", params);
				Example2Panel.this.user.setFirstName(Parameters.getParam(params, "firstName"));
				Example2Panel.this.user.setLastName(Parameters.getParam(params, "lastName"));
				yourNameLabel.modelChanged();
				target.add(yourNameLabel);
				target.appendJavaScript(Javascript.highlight(yourNameLabel));
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				PageParameters params = target.getPageParameters();
				target.addChildren(form, FormComponent.class);
				target.add(form.get("nameFeedback"));
				logger.debug("error {}", params);

			}

			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				attributes.getDynamicExtraParameters().add("return Wicket.Form.serializeElement('" + firstName.getMarkupId() + "')");
				attributes.getDynamicExtraParameters().add("return Wicket.Form.serializeElement('" + lastName.getMarkupId() + "')");

			}

		});

		yourNameLabel = new StatelessLabel("yourName", new PropertyModel<String>(this, "user.salutation"));
		yourNameLabel.setOutputMarkupPlaceholderTag(true);
		inputForm.add(yourNameLabel);

		final DropDownChoice<String> preference = new DropDownChoice<String>("preference", new Model<String>("Tebowing"), Arrays.asList(new String[] { "Tebowing", "Gronking", "Other" }));
		preference.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected PageParameters getPageParameters() {
				return new PageParameters();
			}

			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				logger.debug("pageParamters {}", getPageParameters());
			}
		});
		preference.setMarkupId(preference.getId());
		inputForm.add(firstName);
		inputForm.add(preference);
		inputForm.add(lastName);
		add(inputForm);

		inputForm.add(new FeedbackPanel("nameFeedback").setOutputMarkupId(true));

		// https://www.packtpub.com/apache-wicket-cookbook/book#chapter_3"
		inputForm.visitChildren(FormComponent.class, new IVisitor<FormComponent<?>, Void>() {

			@Override
			public void component(FormComponent<?> component, IVisit<Void> visit) {
				component.add(new ErrorClassBehavior());
			}

		});

	}

	private class User {
		private String firstName;
		private String lastName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getSalutation() {
			if (Strings.isEmpty(firstName) || Strings.isEmpty(lastName))
				return null;

			return "Hello " + firstName + " " + lastName + "!";

		}

	}

}
